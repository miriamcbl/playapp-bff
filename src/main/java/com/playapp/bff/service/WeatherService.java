package com.playapp.bff.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.stereotype.Service;

import com.playapp.bff.bean.LocationCode;
import com.playapp.bff.constants.BeachesGeographicCoordinates;
import com.playapp.bff.constants.CadizTownsGeographicCoordinates;
import com.playapp.bff.constants.LevanteWind;
import com.playapp.bff.constants.PromptConstants;
import com.playapp.bff.mapper.WeatherMapper;
import com.playapp.bff.service.supplier.AccuWeatherRestService;
import com.playapp.bff.service.supplier.bean.DailyForecast;
import com.playapp.bff.service.supplier.bean.LocationResponse;
import com.playapp.bff.service.supplier.bean.WeatherDetailsResponse;
import com.playapp.bff.util.DateUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * The Class WeatherService.
 */
@Slf4j
@Service
public class WeatherService {

	/** The accu weather rest service. */
	private AccuWeatherRestService accuWeatherRestService;

	/** The weather mapper. */
	private WeatherMapper weatherMapper;

	/** The chat service. */
	private ChatService chatService;

	/** The map service. */
	private MapsService mapService;

	/**
	 * Instantiates a new weather service.
	 *
	 * @param accuWeatherRestService the accu weather rest service
	 * @param weatherMapper          the weather mapper
	 * @param chatService            the chat service
	 * @param mapService             the map service
	 */
	public WeatherService(AccuWeatherRestService accuWeatherRestService, WeatherMapper weatherMapper,
			ChatService chatService, MapsService mapService) {
		this.accuWeatherRestService = accuWeatherRestService;
		this.weatherMapper = weatherMapper;
		this.chatService = chatService;
		this.mapService = mapService;
	}

	/**
	 * Gets the location codes by coordinates.
	 *
	 * @return the location codes by coordinates
	 */
	private List<LocationCode> getLocationCodesByCoordinates(List<BeachesGeographicCoordinates> beaches) {
		List<LocationCode> locationsKeys = new ArrayList<>();
		beaches.parallelStream().forEach(beach -> {
			LocationResponse locationResponse = accuWeatherRestService.getLocations(beach.getLatitude(),
					beach.getLongitude());
			locationsKeys.add(weatherMapper.mapToLocationCode(beach.name(), locationResponse.getKey()));
		});
		return locationsKeys;
	}

	/**
	 * Gets the all beach weathers by location code.
	 *
	 * @param locationCodes the location codes
	 * @return the all beach weathers by location code
	 */
	private List<WeatherDetailsResponse> getAllBeachWeathersByLocationCode(List<LocationCode> locationCodes,
			String dayCode, int days) {
		List<WeatherDetailsResponse> weatherDetails = new ArrayList<>();
		// Se obtiene relacion info del tiempo por cala (lista)
		locationCodes.parallelStream().forEach(locationCode -> {
			WeatherDetailsResponse weatherDetailsResponse = accuWeatherRestService.getWeatherDetailsByDays(dayCode,
					locationCode.getCode());
			DailyForecast dailyForecast = weatherDetailsResponse.getDailyForecasts().get(days);
			// Se añade solo el día que indica el usuario
			WeatherDetailsResponse weatherDetailsResponseFinal = WeatherDetailsResponse.builder()
					.beachName(locationCode.getName()).dailyForecasts(List.of(dailyForecast)).build();
			weatherDetails.add(weatherDetailsResponseFinal);
		});
		return weatherDetails;
	}

	/**
	 * Gets the cadiz information data.
	 *
	 * @param dayCodeForPrediction the day code for prediction
	 * @return the cadiz information data
	 */
	private WeatherDetailsResponse getCadizInformationData(String dayCodeForPrediction) {
		WeatherDetailsResponse weatherDetailsCadiz = null;
		LocationResponse cadizCode = accuWeatherRestService.getLocations("36.502971", "-6.276354");
		weatherDetailsCadiz = accuWeatherRestService.getWeatherDetailsByDays(dayCodeForPrediction, cadizCode.getKey());
		return weatherDetailsCadiz;
	}

	/**
	 * Verify if weatherDetails is not null and has information
	 *
	 * @param weatherDetails the weather details
	 * @return true, if successful
	 */
	private boolean existsDayAndWindInWeatherDetails(WeatherDetailsResponse weatherDetails) {
		return weatherDetails != null && CollectionUtils.isNotEmpty(weatherDetails.getDailyForecasts())
				&& weatherDetails.getDailyForecasts().get(0).getDay() != null
				&& weatherDetails.getDailyForecasts().get(0).getDay().getWind() != null;
	}

	/**
	 * Verify if its raining.
	 *
	 * @param weatherDetails the weather details
	 * @return true, if successful
	 */
	private boolean itsRaining(WeatherDetailsResponse weatherDetails) {
		return weatherDetails.getDailyForecasts().get(0).getDay().isHasPrecipitation();
	}

	private String getLevanteBeaches(List<WeatherDetailsResponse> weatherDetails) {
		String finalLevanteBeaches = null;
		// se obtiene listado de todas las playas aptas para levante
		List<BeachesGeographicCoordinates> levanteBeaches = Arrays.asList(BeachesGeographicCoordinates.values()).stream()
				.filter(beach -> Boolean.TRUE.equals(beach.getSuitableForLevante())).toList();
		// del listado de todos los weatherDetails de cada playa, nos quedamos con
		// aquellos cuyo nombre esté indicado en el resultado del filtrado anterior que
		// corresponde a las playas aptas para viento levante
		List<WeatherDetailsResponse> weatherDetailsForLevanteBeaches = weatherDetails.stream()
				.filter(weatherBeach -> levanteBeaches.stream()
						.anyMatch(levanteBeach -> levanteBeach.name().equals(weatherBeach.getBeachName())))
				.toList();
		// ordenamos las tres mejores opciones
		finalLevanteBeaches = sortAndLimitFinalBeaches(weatherDetailsForLevanteBeaches);
		return finalLevanteBeaches;
	}

	private String sortAndLimitFinalBeaches(List<WeatherDetailsResponse> weatherDetails) {
		log.info("start - Beaches comparison");
		deleteBeachesWithHighWindAndGust(weatherDetails);
		if (CollectionUtils.isEmpty(weatherDetails)) {
			return PromptConstants.GUSTS;
		}
		List<String> parseDetails = new ArrayList<>();
		weatherDetails.stream().forEach(details -> {
			String name = details.getBeachName();
			String wind = String.valueOf(details.getDailyForecasts().get(0).getDay().getWind().getSpeed().getValue());
			parseDetails.add("Playa: " + name + ", viento: " + wind + " km/h.");
		});
		log.info("Beaches parsed: " + Arrays.toString(parseDetails.toArray()));
		String message = PromptConstants.OUTPUT_SYSTEM + parseDetails;
		// Llamar al servicio de chat para obtener y devolver la respuesta
		ChatResponse comparisonResponse = chatService.getChatResponseByPrompts(message);
		log.info("end - Beaches comparison result: " + comparisonResponse.getResult().getOutput().getContent());
		return comparisonResponse.getResult().getOutput().getContent();

	}

	private boolean itHasSpecialCondition(WeatherDetailsResponse weatherDetail) {
		// Si la playa es "Tarifa, Bolonia..." el km/h del
		// windGust debe ser menor
		return Arrays.stream(BeachesGeographicCoordinates.values()).anyMatch(
				beach -> beach.name().equals(weatherDetail.getBeachName()) && beach.getSpecialConditionForWindGust());
	}

	/**
	 * Delete beaches with high wind gust.
	 *
	 * @param weatherDetails the weather details
	 * @return the string
	 */
	private void deleteBeachesWithHighWindAndGust(List<WeatherDetailsResponse> weatherDetails) {
		List<WeatherDetailsResponse> weatherDetailsWithHighWindAndGust = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(weatherDetails)) {
			weatherDetails.stream().forEach(detail -> {
				if (detail != null && CollectionUtils.isNotEmpty(detail.getDailyForecasts())) {
					if (detail.getDailyForecasts().get(0).getDay().getWind().getSpeed().getValue() >= 30.0) {
						weatherDetailsWithHighWindAndGust.add(detail);
					} else {
						addBeachesWithHighGustToDelete(detail, weatherDetailsWithHighWindAndGust);
					}

				}
			});
		}
		if (CollectionUtils.isNotEmpty(weatherDetailsWithHighWindAndGust)) {
			weatherDetails.removeAll(weatherDetailsWithHighWindAndGust);
		}

	}

	private void addBeachesWithHighGustToDelete(WeatherDetailsResponse weatherDetails,
			List<WeatherDetailsResponse> weatherDetailsWithHighWindAndGust) {
		double windGust = weatherDetails.getDailyForecasts().get(0).getDay().getWindGust().getSpeed().getValue();
		if (itHasSpecialCondition(weatherDetails)) {
			if (windGust >= 18.0) {
				weatherDetailsWithHighWindAndGust.add(weatherDetails);
			}
		} else {
			if (windGust > 25.0) {
				weatherDetailsWithHighWindAndGust.add(weatherDetails);
			}
		}
	}



	/**
	 * Gets the final beaches by direction wind.
	 *
	 * @param directionWindToday   the direction wind today
	 * @param dayCodeForPrediction the day code for prediction
	 * @return the final beaches by direction wind
	 */
	private String getFinalBeachesByDirectionWind(String directionWindToday, String dayCodeForPrediction, int days,
			String originLatLon, int maxDuration, int minDuration) {
		String finalBeaches = null;
		// Filtramos primero de todas las playas las que cumplen condición de duración
		// por el usuario
		List<BeachesGeographicCoordinates> beaches = mapService.getBeachesWithDurationConditions(originLatLon,
				maxDuration, minDuration);
		// Listado con el código asociado a cada playa según AccuWeather
		List<LocationCode> locationCodes = getLocationCodesByCoordinates(beaches);
		// Se obtiene el tiempo/viento asociado a cada código
		List<WeatherDetailsResponse> weatherDetails = getAllBeachWeathersByLocationCode(locationCodes,
				dayCodeForPrediction, days);
		// se comprueba si hoy hay levante en cadiz
		boolean isLevante = CollectionUtils.isNotEmpty(weatherDetails)
				? Arrays.stream(LevanteWind.values()).anyMatch(wind -> wind.getShortName().equals(directionWindToday))
				: Boolean.FALSE;
		// se obtiene en base a si hay o no levante las playas finales
		finalBeaches = isLevante ? getLevanteBeaches(weatherDetails) : sortAndLimitFinalBeaches(weatherDetails);
		return finalBeaches;
	}

	/**
	 * Gets the beaches data by weather.
	 *
	 * @param date the date
	 * @return the beaches data by weather
	 */
	public String getBeachesDataByWeather(String date, String origin, int maxDuration, int minDuration) {
		String dayCodeForPrediction = null;
		String finalMessageResult = null;
		String originLatLon = CadizTownsGeographicCoordinates.valueOf(origin).getLatitude() + ","
				+ CadizTownsGeographicCoordinates.valueOf(origin).getLongitude();
		if (DateUtils.dateHasYear(date) && DateUtils.isNotThisYear(date)) {
			return PromptConstants.DATE_ERROR;
		}
		try {
			dayCodeForPrediction = DateUtils.getDaysForAccuWeatherPrediction(date);
		} catch (IllegalArgumentException e) {
			return PromptConstants.DATE_ERROR;
		}
		int days = DateUtils.countDaysFromToday(date);
		String directionWindToday;
		double kmhWindToday;
		// Se obtiene el tiempo y viento actual en la provincia de Cádiz
		WeatherDetailsResponse weatherDetailsCadiz = getCadizInformationData(dayCodeForPrediction);
		if (existsDayAndWindInWeatherDetails(weatherDetailsCadiz)) {
			if (!itsRaining(weatherDetailsCadiz)) {
				// que dirección de viento hace hoy
				directionWindToday = weatherDetailsCadiz.getDailyForecasts().get(days).getDay().getWind().getDirection()
						.getLocalized();
				// qué kmh hay hoy
				kmhWindToday = Math.round(
						weatherDetailsCadiz.getDailyForecasts().get(days).getDay().getWind().getSpeed().getValue());
				finalMessageResult = kmhWindToday > 30.0 ? PromptConstants.WIND_SUPERIOR_30
						: getFinalBeachesByDirectionWind(directionWindToday, dayCodeForPrediction, days, originLatLon,
								maxDuration, minDuration);
			} else {
				finalMessageResult = PromptConstants.ITS_RAINING;
			}
		} else {
			finalMessageResult = PromptConstants.CANNOT_CONNECT_API;
		}
		return finalMessageResult;
	}

}

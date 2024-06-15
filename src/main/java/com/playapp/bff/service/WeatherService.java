package com.playapp.bff.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.stereotype.Service;

import com.playapp.bff.bean.LocationCode;
import com.playapp.bff.constants.Constants;
import com.playapp.bff.constants.GeographicCoordinates;
import com.playapp.bff.constants.LevanteWind;
import com.playapp.bff.mapper.WeatherMapper;
import com.playapp.bff.service.supplier.AccuWeatherRestService;
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

	/**
	 * Instantiates a new weather service.
	 *
	 * @param accuWeatherRestService the accu weather rest service
	 * @param weatherMapper          the weather mapper
	 * @param chatService            the chat service
	 */
	public WeatherService(AccuWeatherRestService accuWeatherRestService, WeatherMapper weatherMapper,
			ChatService chatService) {
		this.accuWeatherRestService = accuWeatherRestService;
		this.weatherMapper = weatherMapper;
		this.chatService = chatService;
	}

	/**
	 * Gets the location codes by coordinates.
	 *
	 * @return the location codes by coordinates
	 */
	private List<LocationCode> getLocationCodesByCoordinates() {
		List<LocationCode> locationsKeys = new ArrayList<>();
		Arrays.asList(GeographicCoordinates.values()).parallelStream().forEach(coordinates -> {
			LocationResponse locationResponse = accuWeatherRestService.getLocations(coordinates.getLatitude(),
					coordinates.getLongitude());
			locationsKeys.add(weatherMapper.mapToLocationCode(coordinates.name(), locationResponse.getKey()));
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
			String dayCode) {
		List<WeatherDetailsResponse> weatherDetails = new ArrayList<>();
		// Se obtiene relacion info del tiempo por cala (lista)
		locationCodes.parallelStream().forEach(locationCode -> {
			WeatherDetailsResponse weatherDetailsResponse = accuWeatherRestService.getWeatherDetailsByDays(dayCode,
					locationCode.getCode());
			weatherDetailsResponse.setBeachName(locationCode.getName());
			weatherDetails.add(weatherDetailsResponse);
		});
		return weatherDetails;
	}

	private WeatherDetailsResponse getCadizInformationData(String dayCodeForPrediction) {
		WeatherDetailsResponse weatherDetailsCadiz = null;
		LocationResponse cadizCode = accuWeatherRestService.getLocations("36.502971", "-6.276354");
		weatherDetailsCadiz = accuWeatherRestService.getWeatherDetailsByDays(dayCodeForPrediction, cadizCode.getKey());
		return weatherDetailsCadiz;
	}

	private boolean existsDayAndWindInWeatherDetails(WeatherDetailsResponse weatherDetails) {
		return weatherDetails != null && CollectionUtils.isNotEmpty(weatherDetails.getDailyForecasts())
				&& weatherDetails.getDailyForecasts().get(0).getDay() != null
				&& weatherDetails.getDailyForecasts().get(0).getDay().getWind() != null;
	}

	private String getLevanteBeaches(List<WeatherDetailsResponse> weatherDetails) {
		String finalLevanteBeaches = null;
		// se obtiene listado de todas las playas aptas para levante
		List<GeographicCoordinates> levanteBeaches = Arrays.asList(GeographicCoordinates.values()).stream()
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
		List<String> parseDetails = new ArrayList<>();
		weatherDetails.stream().forEach(details -> {
			String name = details.getBeachName();
			String wind = String.valueOf(details.getDailyForecasts().get(0).getDay().getWind().getSpeed().getValue());
			parseDetails.add("Playa: " + name + ", viento: " + wind + " km/h.");
		});
		log.info("Beaches parsed: " + Arrays.toString(parseDetails.toArray()));
		String message = "Ordena esta lista en base a los km/h del viento: " + parseDetails
				+ " y escoge a las tres mejores playas que tengan el viento más bajo";
		// Llamar al servicio de chat para obtener y devolver la respuesta
		ChatResponse comparisonResponse = chatService.getChatResponseByPrompts(message);
		log.info("end - Beaches comparison result: " + comparisonResponse.getResult().getOutput().getContent());
		return comparisonResponse.getResult().getOutput().getContent();

	}

	private String getFinalBeachesByDirectionWind(String directionWindToday, String dayCodeForPrediction) {
		String finalBeaches = null;
		// Listado con el código asociado a cada playa según AccuWeather
		List<LocationCode> locationCodes = getLocationCodesByCoordinates();
		// Se obtiene el tiempo/viento asociado a cada código
		List<WeatherDetailsResponse> weatherDetails = getAllBeachWeathersByLocationCode(locationCodes,
				dayCodeForPrediction);
		// se comprueba si hoy hay levante en cadiz
		boolean isLevante = CollectionUtils.isNotEmpty(weatherDetails)
				? Arrays.stream(LevanteWind.values()).anyMatch(wind -> wind.getShortName().equals(directionWindToday))
				: Boolean.FALSE;
		// se obtiene en base a si hay o no levante las playas finales
		finalBeaches = isLevante ? getLevanteBeaches(weatherDetails)
				: sortAndLimitFinalBeaches(weatherDetails);
		return finalBeaches;
	}

	public String getBeachesDataByWeather(String date) {
		String dayCodeForPrediction = DateUtils.getDaysForAccuWeatherPrediction(date);
		int days = DateUtils.countDaysFromToday(date);
		String finalMessageResult = null;
		String directionWindToday;
		double kmhWindToday;
		// Se obtiene el tiempo y viento actual en la provincia de Cádiz
		WeatherDetailsResponse weatherDetailsCadiz = getCadizInformationData(dayCodeForPrediction);
		boolean exists = existsDayAndWindInWeatherDetails(weatherDetailsCadiz);
		if (exists) {
			// que dirección de viento hace hoy
			directionWindToday = weatherDetailsCadiz.getDailyForecasts().get(days).getDay().getWind().getDirection()
					.getLocalized();
			// qué kmh hay hoy
			kmhWindToday = Math
					.round(weatherDetailsCadiz.getDailyForecasts().get(days).getDay().getWind().getSpeed()
							.getValue());
			finalMessageResult = kmhWindToday > 30.0
					? Constants.WIND_SUPERIOR_30
					: getFinalBeachesByDirectionWind(directionWindToday, dayCodeForPrediction);
		} else {
			finalMessageResult = Constants.CANNOT_CONNECT_API;
		}
		return finalMessageResult;
	}

}

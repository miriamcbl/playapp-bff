package com.playapp.bff.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import com.playapp.bff.bean.LocationCode;
import com.playapp.bff.constants.GeographicCoordinates;
import com.playapp.bff.constants.LevanteWind;
import com.playapp.bff.mapper.WeatherMapper;
import com.playapp.bff.service.supplier.AccuWeatherRestService;
import com.playapp.bff.service.supplier.bean.LocationResponse;
import com.playapp.bff.service.supplier.bean.WeatherDetailsResponse;

/**
 * The Class WeatherService.
 */
@Service
public class WeatherService {

	/** The accu weather rest service. */
	private AccuWeatherRestService accuWeatherRestService;

	/** The weather mapper. */
	private WeatherMapper weatherMapper;

	/**
	 * Instantiates a new weather service.
	 *
	 * @param accuWeatherRestService the accu weather rest service
	 * @param weatherMapper          the weather mapper
	 */
	public WeatherService(AccuWeatherRestService accuWeatherRestService, WeatherMapper weatherMapper) {
		this.accuWeatherRestService = accuWeatherRestService;
		this.weatherMapper = weatherMapper;
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
	private List<WeatherDetailsResponse> getAllBeachWeathersByLocationCode(List<LocationCode> locationCodes) {
		List<WeatherDetailsResponse> weatherDetails = new ArrayList<>();
		// Se obtiene relacion info del tiempo por cala (lista)
		locationCodes.parallelStream().forEach(locationCode -> {
			WeatherDetailsResponse weatherDetailsResponse = accuWeatherRestService.getDetails(locationCode.getCode());
			weatherDetailsResponse.setBeachName(locationCode.getName());
			weatherDetails.add(weatherDetailsResponse);
		});
		return weatherDetails;
	}

	private WeatherDetailsResponse getCadizInformationData() {
		WeatherDetailsResponse weatherDetailsCadiz = null;
		LocationResponse cadizCode = accuWeatherRestService.getLocations("36.502971", "-6.276354");
		weatherDetailsCadiz = accuWeatherRestService.getDetails(cadizCode.getKey());
		return weatherDetailsCadiz;
	}

	private boolean existsDayAndWindInWeatherDetails(WeatherDetailsResponse weatherDetails) {
		return weatherDetails != null && CollectionUtils.isNotEmpty(weatherDetails.getDailyForecasts())
				&& weatherDetails.getDailyForecasts().get(0).getDay() != null
				&& weatherDetails.getDailyForecasts().get(0).getDay().getWind() != null;
	}

	private List<WeatherDetailsResponse> getLevanteBeaches(List<WeatherDetailsResponse> weatherDetails) {
		List<WeatherDetailsResponse> finalLevanteBeaches = new ArrayList<>();
		// se obtiene listado de todas las playas aptas para levante
		List<GeographicCoordinates> levanteBeaches = Arrays.asList(GeographicCoordinates.values()).stream()
				.filter(beach -> Boolean.TRUE.equals(beach.getSuitableForLevante())).toList();
		// del listado de todos los weatherDetails de cada playa, nos quedamos con
		// aquellos cuyo nombre esté indicado en el resultado del filtrado anterior que
		// corresponde a las playas aptas para levante
		List<WeatherDetailsResponse> weatherDetailsForLevanteBeaches = weatherDetails.stream()
				.filter(weatherBeach -> levanteBeaches.stream()
						.anyMatch(levanteBeach -> levanteBeach.name().equals(weatherBeach.getBeachName())))
				.toList();
		// ordenamos las tres mejores opciones
		finalLevanteBeaches = sortAndLimitFinalBeaches(weatherDetailsForLevanteBeaches);
		return finalLevanteBeaches;
	}

	private List<WeatherDetailsResponse> sortAndLimitFinalBeaches(List<WeatherDetailsResponse> weatherDetails) {
		if (weatherDetails.size() >= 3) {
			weatherDetails.sort((firstWeather, secondWeather) -> Double.compare(
					firstWeather.getDailyForecasts().get(0).getDay().getWind().getSpeed().getValue(),
					secondWeather.getDailyForecasts().get(0).getDay().getWind().getSpeed().getValue()));
			return weatherDetails.subList(0, 3);
		} else {
			return weatherDetails;
		}

	}

	private List<WeatherDetailsResponse> getFinalBeachesByDirectionWind(String directionWindToday) {
		List<WeatherDetailsResponse> finalBeaches = new ArrayList<>();
		// Listado con el código asociado a cada playa según AccuWeather
		List<LocationCode> locationCodes = getLocationCodesByCoordinates();
		// Se obtiene el tiempo/viento asociado a cada código
		List<WeatherDetailsResponse> weatherDetails = getAllBeachWeathersByLocationCode(locationCodes);
		// se comprueba si hoy hay levante en cadiz
		boolean isLevante = CollectionUtils.isNotEmpty(weatherDetails)
				? Arrays.stream(LevanteWind.values()).anyMatch(wind -> wind.getShortName().equals(directionWindToday))
				: Boolean.FALSE;
		// se obtiene en base a si hay o no levante las playas finales
		finalBeaches = isLevante ? getLevanteBeaches(weatherDetails)
				: sortAndLimitFinalBeaches(weatherDetails);
		return finalBeaches;
	}

	public List<WeatherDetailsResponse> getBeachesDataByWeather() {
		List<WeatherDetailsResponse> finalBeaches = new ArrayList<>();
		String directionWindToday;
		double kmhWindToday;
		// Se obtiene el tiempo y viento actual en la provincia de Cádiz
		WeatherDetailsResponse weatherDetailsCadiz = getCadizInformationData();
		if (existsDayAndWindInWeatherDetails(weatherDetailsCadiz)) {
			// que dirección de viento hace hoy
			directionWindToday = weatherDetailsCadiz.getDailyForecasts().get(0).getDay().getWind().getDirection()
					.getLocalized();
			// qué kmh hay hoy
			kmhWindToday = Math
					.round(weatherDetailsCadiz.getDailyForecasts().get(0).getDay().getWind().getSpeed().getValue());
			finalBeaches = kmhWindToday > 30.0 ? finalBeaches : getFinalBeachesByDirectionWind(directionWindToday);
		} else {
			// TODO aqui mejor lanzar excepcion "No se puede recuperar el tiempo
			// actualmente"
		}
		return finalBeaches;
	}

}

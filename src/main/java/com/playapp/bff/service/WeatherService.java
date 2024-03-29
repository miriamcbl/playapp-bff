package com.playapp.bff.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
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
	public WeatherService(AccuWeatherRestService accuWeatherRestService, 
			WeatherMapper weatherMapper) {
		this.accuWeatherRestService = accuWeatherRestService;
		this.weatherMapper = weatherMapper;
	}

	public List<LocationCode> getLocationCodesByCoordinates() {
		// TODO lo pillamos del Enum, pero las coordenadas las podríamos meter en BBDD
		// porque son eltos fijos
		// Sacamos los códigos de localización según las coordenadas
		List<LocationCode> locationsKeys = new ArrayList<>();
		Arrays.asList(GeographicCoordinates.values()).parallelStream().forEach(coordinates -> {
			LocationResponse locationResponse = accuWeatherRestService.getLocations(coordinates.getLatitude(),
					coordinates.getLongitude());
			locationsKeys.add(weatherMapper.mapToLocationCode(coordinates.name(), locationResponse.getKey()));
		});
		return locationsKeys;
	}

	public List<WeatherDetailsResponse> getBeachWeathers(List<LocationCode> locationCodes) {
		List<WeatherDetailsResponse> weatherDetails = new ArrayList<>();
		// Se obtiene relacion info del tiempo por cala (lista)
		locationCodes.parallelStream().forEach(locationCode -> {
			WeatherDetailsResponse weatherDetailsResponse = accuWeatherRestService.getDetails(locationCode.getCode());
			weatherDetailsResponse.setBeachName(locationCode.getName());
			weatherDetails.add(weatherDetailsResponse);
		});
		return weatherDetails;
	}

	public void getInformationData() {
		// Listado con el código asociado a cada playa segun AccuWeather
		List<LocationCode> locationCodes = getLocationCodesByCoordinates();
		// Se obtiene el tiempo/viento asociado a cada código
		List<WeatherDetailsResponse> weatherDetails = getBeachWeathers(locationCodes);
		// 1. ¿Qué viento hace en cádiz hoy?
		WeatherDetailsResponse weatherDetailsCadiz = accuWeatherRestService.getDetails("306727");
		if (CollectionUtils.isNotEmpty(weatherDetails)) {
			String windToday = weatherDetailsCadiz.getDailyForecasts().get(0).getDay().getWind().getDirection()
					.getLocalized();
			boolean isLevante = Arrays.stream(LevanteWind.values())
					.anyMatch(wind -> wind.getShortName().equals(windToday));
			if (isLevante) {
				// buscar entre las playas que tienen levante
				// TODO evaluar las playas apropiadas para levante (ponerle notas a esas solo)
				// me saco los nombres de las playas para levante
				// busco entre todas las que tengan menor viento
				// le pongo notas para elegir la mejor
			} else {
				// buscar en base a los km/h del viento
				// TODO montar fórmula para evaluar las playas según los km/h
				// es decir 1 elijo de weatherDetails las playas que tengan menor viento
				Comparator<WeatherDetailsResponse> comparator = new Comparator<WeatherDetailsResponse>() {
					@Override
					public int compare(WeatherDetailsResponse firstWeather, WeatherDetailsResponse secondWeather) {
						double viento1 = firstWeather.getDailyForecasts().get(0).getDay().getWind().getSpeed()
								.getValue();
						double viento2 = secondWeather.getDailyForecasts().get(0).getDay().getWind().getSpeed()
								.getValue();
						return Double.compare(viento1, viento2);
					}
				};
				weatherDetails.sort(comparator);
				// luego aplico la formula para sacarme las 3 mejores
			}
		}


	}


}

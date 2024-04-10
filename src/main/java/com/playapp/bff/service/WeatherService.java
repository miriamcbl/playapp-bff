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
	public WeatherService(AccuWeatherRestService accuWeatherRestService, 
			WeatherMapper weatherMapper) {
		this.accuWeatherRestService = accuWeatherRestService;
		this.weatherMapper = weatherMapper;
	}

	private List<LocationCode> getLocationCodesByCoordinates() {
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

	public List<WeatherDetailsResponse> getInformationData() {
		List<WeatherDetailsResponse> finalBeaches = new ArrayList<>();
		WeatherDetailsResponse weatherDetailsCadiz = accuWeatherRestService.getDetails("306727");
		String directionWindToday = weatherDetailsCadiz.getDailyForecasts().get(0).getDay().getWind().getDirection()
				.getLocalized();
		double kmhWindToday = Math
				.round(weatherDetailsCadiz.getDailyForecasts().get(0).getDay().getWind().getSpeed().getValue());
		if (kmhWindToday > 30.0) {
			return finalBeaches;
		} else {
			// Listado con el código asociado a cada playa segun AccuWeather
			List<LocationCode> locationCodes = getLocationCodesByCoordinates();
			// Se obtiene el tiempo/viento asociado a cada código
			List<WeatherDetailsResponse> weatherDetails = getBeachWeathers(locationCodes);
			if (CollectionUtils.isNotEmpty(weatherDetails)) {
				boolean isLevante = Arrays.stream(LevanteWind.values())
						.anyMatch(wind -> wind.getShortName().equals(directionWindToday));

//				Comparator<WeatherDetailsResponse> comparator = new Comparator<WeatherDetailsResponse>() {
//					@Override
//					public int compare(WeatherDetailsResponse firstWeather, WeatherDetailsResponse secondWeather) {
//						double viento1 = firstWeather.getDailyForecasts().get(0).getDay().getWind().getSpeed()
//								.getValue();
//						double viento2 = secondWeather.getDailyForecasts().get(0).getDay().getWind().getSpeed()
//								.getValue();
//						return Double.compare(viento1, viento2);
//					}
//				};

				if (isLevante) {
					List<GeographicCoordinates> levanteBeaches = Arrays.asList(GeographicCoordinates.values()).stream()
							.filter(beach -> Boolean.TRUE.equals(beach.getSuitableForLevante()))
							.toList();
					List<WeatherDetailsResponse> weatherDetailsForLevanteBeaches = weatherDetails.stream()
							.filter(weatherBeach -> levanteBeaches.stream()
									.anyMatch(levanteBeach -> levanteBeach.name().equals(weatherBeach.getBeachName())))
							.toList();
					weatherDetailsForLevanteBeaches.sort((firstWeather, secondWeather) -> Double.compare(
							firstWeather.getDailyForecasts().get(0).getDay().getWind().getSpeed().getValue(),
							secondWeather.getDailyForecasts().get(0).getDay().getWind().getSpeed().getValue()));
					finalBeaches = weatherDetailsForLevanteBeaches.subList(0, 3);
				} else {
					weatherDetails.sort((firstWeather, secondWeather) -> Double.compare(
							firstWeather.getDailyForecasts().get(0).getDay().getWind().getSpeed().getValue(),
							secondWeather.getDailyForecasts().get(0).getDay().getWind().getSpeed().getValue()));
					finalBeaches = weatherDetails.subList(0, 3);
				}
			}
		}
		return finalBeaches;
	}


}

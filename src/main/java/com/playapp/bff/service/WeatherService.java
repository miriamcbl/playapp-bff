package com.playapp.bff.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.playapp.bff.bean.LocationKey;
import com.playapp.bff.constants.GeographicCoordinates;
import com.playapp.bff.mapper.WeatherMapper;
import com.playapp.bff.service.supplier.AccuWeatherRestService;
import com.playapp.bff.service.supplier.bean.LocationResponse;

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

	public List<LocationKey> getWeatherDetails() {
		List<LocationKey> locationsKeys = new ArrayList<>();
		List<GeographicCoordinates> allCoordinates = List.of(GeographicCoordinates.values());
		allCoordinates.parallelStream().forEach(coordinates -> {
			LocationResponse locationResponse = accuWeatherRestService.getLocations(coordinates.getLatitude(),
					coordinates.getLongitude());
			locationsKeys.add(weatherMapper.mapToLocationKey(coordinates.name(), locationResponse.getKey()));
		});
		return locationsKeys;
	}

}

package com.playapp.bff.mapper;

import org.springframework.stereotype.Component;

import com.playapp.bff.bean.LocationKey;

/**
 * The Class WeatherMapper.
 */
@Component
public class WeatherMapper {

	/**
	 * Map to location key.
	 *
	 * @param name the name
	 * @param key  the key
	 * @return the location key
	 */
	public LocationKey mapToLocationKey(String name, String key) {
		return LocationKey.builder().name(name).key(key).build();

	}

}

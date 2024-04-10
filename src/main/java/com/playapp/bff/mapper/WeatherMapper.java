package com.playapp.bff.mapper;

import org.springframework.stereotype.Component;

import com.playapp.bff.bean.LocationCode;

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
	public LocationCode mapToLocationCode(String name, String key) {
		return LocationCode.builder().name(name).code(key).build();

	}

}

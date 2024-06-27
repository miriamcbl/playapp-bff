package com.playapp.bff.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import com.playapp.bff.constants.BeachesGeographicCoordinates;
import com.playapp.bff.service.supplier.GoogleMapsRestService;
import com.playapp.bff.service.supplier.bean.DistanceMatrixResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MapsService {

	/** The google maps rest service. */
	private GoogleMapsRestService googleMapsRestService;

	/**
	 * Instantiates a new maps service.
	 *
	 * @param googleMapsRestService the google maps rest service
	 */
	public MapsService(GoogleMapsRestService googleMapsRestService) {
		this.googleMapsRestService = googleMapsRestService;
	}

	/**
	 * Gets the distance.
	 *
	 * @param origin      the origin
	 * @param destination the destination
	 * @return the distance
	 */
	public DistanceMatrixResponse getDistance(String origin, String destination) {
		return googleMapsRestService.getDistance(origin, destination);

	}

	/**
	 * Gets the beaches with duration conditions.
	 *
	 * @param originLatLon the origin lat lon
	 * @param maxDuration  the max duration
	 * @param minDuration  the min duration
	 * @return the beaches with duration conditions for locations
	 */
	public List<BeachesGeographicCoordinates> getBeachesWithDurationConditions(String originLatLon, int maxDuration,
			int minDuration) {
		log.info("Start - getBeachesWithDurationConditions");
		List<BeachesGeographicCoordinates> beaches = new ArrayList<>();
		Arrays.asList(BeachesGeographicCoordinates.values()).stream().forEach(beach -> {
			DistanceMatrixResponse distanceResponse = getDistance(originLatLon, beach.getLatLon());
			if (CollectionUtils.isNotEmpty(distanceResponse.getRows())
					&& CollectionUtils.isNotEmpty(distanceResponse.getRows().get(0).getElements())) {
				int minutesToBeach = distanceResponse.getRows().get(0).getElements().get(0).getDuration().getValue()
						/ 60;
				if (minutesToBeach >= minDuration && minutesToBeach <= maxDuration) {
					beaches.add(beach);
				}
			}
		});
		log.info("End - getBeachesWithDurationConditions");
		return beaches;
	}

}

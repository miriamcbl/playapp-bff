package com.playapp.bff.function;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.playapp.bff.service.WeatherService;

@Component
@JsonClassDescription("Servicio que proporciona la comparativa de tiempos para determinar a qué playa es mejor ir en Cadiz")
public class WeatherFunction implements Function<WeatherFunction.Request, WeatherFunction.Response> {

	/**
	 * Weather Function request.
	 */
	@JsonInclude(Include.NON_NULL)
	@JsonClassDescription("Weather API request")
	public record Request(
			@JsonProperty(required = true, value = "location") @JsonPropertyDescription("La localizacion, por ejemplo: Cadiz") String location,
			@JsonProperty(required = true, value = "date") @JsonPropertyDescription("La fecha, por ejemplo: 11/06") String date,
			@JsonProperty(required = true, value = "origin") @JsonPropertyDescription("El origen, municipio donde se encuentra la persona") String origin,
			@JsonProperty(required = true, value = "maxDuration") @JsonPropertyDescription("La duración máxima para ir a la playa en coche") int maxDuration,
			@JsonProperty(required = true, value = "minDuration") @JsonPropertyDescription("La duración mínima para ir a la playa en coche") int minDuration) {
	}

	/**
	 * Weather Function response.
	 */
	public record Response(String beaches) {
	}

	@Autowired
	private WeatherService weatherService;

	@Override
	public Response apply(Request request) {
		// llamada al servicio para obtener el tiempo en roche
		String beaches = weatherService.getBeachesDataByWeather(request.date(), request.origin(),
				request.maxDuration(), request.minDuration());
		return new Response(beaches);
	}
}

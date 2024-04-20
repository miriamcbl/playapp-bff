package com.playapp.bff.config;

import java.util.function.Function;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

import com.playapp.bff.function.WeatherFunction;

@Configuration
public class AIConfig {
	@Bean
	@Description("Servicio que proporciona la comparativa de tiempos para determinar a qué playa es mejor ir en Cadiz")
	public Function<WeatherFunction.Request, WeatherFunction.Response> weatherFunction() {
		return new WeatherFunction();
	}
}

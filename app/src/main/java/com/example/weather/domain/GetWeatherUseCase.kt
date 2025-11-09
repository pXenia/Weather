package com.example.weather.domain

import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(
    private val repository: WeatherRepository
) {
    suspend operator fun invoke(): Result<WeatherResponse> {
        return repository.getWeatherForecast()
    }
}
package com.example.weather.domain

interface WeatherRepository {
    suspend fun getWeatherForecast(): Result<WeatherResponse>
}
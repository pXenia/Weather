package com.example.weather.data

import com.example.weather.domain.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("forecast.json")
    suspend fun getForecast(
        @Query("key") apiKey: String = "fa8b3df74d4042b9aa7135114252304",
        @Query("q") location: String = "55.7569,37.6151",
        @Query("days") days: Int = 3,
    ): WeatherResponse
}
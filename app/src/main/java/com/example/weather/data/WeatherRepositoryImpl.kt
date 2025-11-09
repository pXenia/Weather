package com.example.weather.data

import com.example.weather.domain.WeatherRepository
import com.example.weather.domain.WeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val apiService: WeatherApi
) : WeatherRepository {

    override suspend fun getWeatherForecast(): Result<WeatherResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getForecast()
                Result.success(response)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}
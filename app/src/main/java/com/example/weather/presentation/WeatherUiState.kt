package com.example.weather.presentation

import com.example.weather.domain.WeatherResponse

sealed interface WeatherUiState {
    data object Loading : WeatherUiState
    data class Success(val data: WeatherResponse) : WeatherUiState
    data class Error(val message: String) : WeatherUiState
}
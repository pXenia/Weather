package com.example.weather.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.domain.GetWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getWeatherUseCase: GetWeatherUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<WeatherUiState>(WeatherUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        fetchWeather()
    }

    fun fetchWeather() {
        _uiState.value = WeatherUiState.Loading

        viewModelScope.launch {
            val result = getWeatherUseCase()

            _uiState.update {
                result.fold(
                    onSuccess = { data -> WeatherUiState.Success(data) },
                    onFailure = { error -> WeatherUiState.Error(error.message ?: "Неизвестная ошибка") }
                )
            }
        }
    }
}
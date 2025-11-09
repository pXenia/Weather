package com.example.weather.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.weather.domain.ForecastDay
import com.example.weather.domain.Hour
import com.example.weather.domain.WeatherResponse

@Composable
fun WeatherScreen(
    viewModel: WeatherViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp, vertical = 48.dp),
        contentAlignment = Alignment.Center
    ) {
        when (val state = uiState) {
            is WeatherUiState.Loading -> {
                CircularProgressIndicator()
            }
            is WeatherUiState.Success -> {
                WeatherContent(data = state.data)
            }
            is WeatherUiState.Error -> {
                var showDialog by remember(state) { mutableStateOf(true) }
                if (showDialog) {
                    ErrorDialog(
                        message = state.message,
                        onRetry = {
                            showDialog = false
                            viewModel.fetchWeather()
                        },
                        onDismiss = {
                            showDialog = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun WeatherContent(data: WeatherResponse) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CurrentWeather(data)
        Spacer(Modifier.height(24.dp))
        HourlyForecast(data.forecast.forecastDay.first().hour)
        Spacer(Modifier.height(24.dp))
        DailyForecast(data.forecast.forecastDay)
    }
}

@Composable
fun CurrentWeather(data: WeatherResponse) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = data.location.name,
            fontSize = 28.sp,
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = "${data.current.tempC.toInt()}°",
            fontSize = 64.sp,
        )

    }
}

@Composable
fun HourlyForecast(hours: List<Hour>) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Почасовой прогноз",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(hours) { hour ->
                HourlyItem(hour)
            }
        }
    }
}

@Composable
fun HourlyItem(hour: Hour) {
    val time = hour.time.substringAfter(" ")

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = time,
            fontSize = 14.sp,
        )

        WeatherIcon(url = hour.condition.icon)

        Text(
            text = "${hour.tempC.toInt()}°",
            fontSize = 16.sp,
        )
    }
}

@Composable
fun DailyForecast(days: List<ForecastDay>) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Прогноз на 3 дня",
            fontSize = 18.sp,
            modifier = Modifier.padding(bottom = 12.dp)
        )
        days.forEachIndexed { index, day ->
            DailyItem(day)
        }
    }
}

@Composable
fun DailyItem(day: ForecastDay) {
    val date = day.date

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = date,
            fontSize = 16.sp,
            modifier = Modifier.weight(1f)
        )

        WeatherIcon(url = day.day.condition.icon)

        Spacer(Modifier.width(16.dp))

        Text(
            text = "${day.day.maxTempC.toInt()}°",
            fontSize = 16.sp,
        )
        Spacer(Modifier.width(8.dp))
        Text(
            text = "${day.day.minTempC.toInt()}°",
            fontSize = 16.sp,
            color = Color.Gray
        )
    }
}

@Composable
fun WeatherIcon(url: String) {
    AsyncImage(
        model = "https:$url",
        contentDescription = null,
        modifier = Modifier.size(36.dp),
    )
}

@Composable
fun ErrorDialog(
    message: String,
    onRetry: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Ошибка") },
        text = { Text(text = "Не удалось загрузить данные: $message") },
        confirmButton = {
            TextButton(onClick = onRetry) {
                Text("Повторить")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Закрыть")
            }
        }
    )
}
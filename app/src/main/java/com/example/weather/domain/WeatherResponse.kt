package com.example.weather.domain

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("location") val location: Location,
    @SerializedName("current") val current: Current,
    @SerializedName("forecast") val forecast: Forecast
)

data class Location(
    @SerializedName("name") val name: String
)

data class Current(
    @SerializedName("temp_c") val tempC: Double,
    @SerializedName("condition") val condition: Condition
)

data class Condition(
    @SerializedName("text") val text: String,
    @SerializedName("icon") val icon: String
)

data class Forecast(
    @SerializedName("forecastday") val forecastDay: List<ForecastDay>
)

data class ForecastDay(
    @SerializedName("date") val date: String,
    @SerializedName("day") val day: Day,
    @SerializedName("hour") val hour: List<Hour>
)

data class Day(
    @SerializedName("maxtemp_c") val maxTempC: Double,
    @SerializedName("mintemp_c") val minTempC: Double,
    @SerializedName("condition") val condition: Condition
)

data class Hour(
    @SerializedName("time") val time: String,
    @SerializedName("temp_c") val tempC: Double,
    @SerializedName("condition") val condition: Condition,
    @SerializedName("is_day") val isDay: Int
)
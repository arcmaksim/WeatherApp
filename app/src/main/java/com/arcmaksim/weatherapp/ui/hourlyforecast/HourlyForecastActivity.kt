package com.arcmaksim.weatherapp.ui.hourlyforecast

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.arcmaksim.weatherapp.R
import com.arcmaksim.weatherapp.domain.model.ForecastRecord
import com.arcmaksim.weatherapp.domain.model.WeatherType
import com.arcmaksim.weatherapp.ui.MainActivity
import com.arcmaksim.weatherapp.ui.toIconResId
import java.time.LocalDateTime
import java.time.ZoneOffset

class HourlyForecastActivity : ComponentActivity() {

    override fun onCreate(
        savedInstanceState: Bundle?,
    ) {
        super.onCreate(savedInstanceState)

        val args = intent.getParcelableExtra<HourlyForecastArgs>(MainActivity.HOURLY_FORECAST)!!

        setContent {
            MaterialTheme {
                HourlyForecast(
                    args.timezone,
                    args.hourlyForecast,
                )
            }
        }
    }

    @Composable
    fun HourlyForecast(
        timezone: String,
        forecast: List<ForecastRecord>,
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFF9D34C),
                            Color(0xFFF25019),
                        )
                    ),
                ),
            contentPadding = PaddingValues(
                start = 32.dp,
                top = 16.dp,
                end = 32.dp,
                bottom = 16.dp,
            ),
        ) {
            items(forecast) { item ->
                HourlyForecastLine(
                    timezone = timezone,
                    hour = item,
                )
            }
        }
    }

    @Composable
    fun HourlyForecastLine(
        timezone: String,
        hour: ForecastRecord,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .background(Color.Transparent),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                modifier = Modifier.width(80.dp),
                text = hour.getFormattedTime(timezone),
                color = Color.White,
                style = TextStyle(
                    fontSize = 24.sp,
                ),
            )
            Icon(
                painter = painterResource(hour.type.toIconResId()),
                tint = Color.White,
                contentDescription = stringResource(R.string.icon_image_view_content_desc),
            )
            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 15.dp),
                text = hour.summary,
                color = Color.White,
                style = TextStyle(
                    fontSize = 14.sp,
                ),
            )
            Text(
                text = "${hour.temperature}",
                color = Color.White,
                style = TextStyle(
                    fontSize = 24.sp,
                ),
            )
        }
    }

    @Preview(
        showBackground = true,
        backgroundColor = 0xFFF25019,
    )
    @Composable
    fun HourlyForecastLinePreview() {
        MaterialTheme {
            HourlyForecastLine(
                "Europe/Moscow",
                ForecastRecord(
                    type = WeatherType.ClearDay,
                    time = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC),
                    temperature = 1,
                    summary = "Sunny",
                    precipitationProbability = 0,
                    apparentTemperature = 1,
                    windSpeed = 2,
                    windGust = 5,
                    humidity = 10,
                )
            )
        }
    }

    @Preview(
        widthDp = 393,
        heightDp = 851,
        showBackground = true,
    )
    @Composable
    fun HourlyForecastScreenPreview() {
        val timezone = "Europe/Moscow"
        val hourlyForecast = (1..10).map { index ->
            ForecastRecord(
                type = WeatherType.ClearDay,
                time = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC),
                temperature = index,
                summary = "Sunny",
                precipitationProbability = 0,
                apparentTemperature = index,
                windSpeed = 2,
                windGust = 5,
                humidity = 10,
            )
        }

        MaterialTheme {
            HourlyForecast(timezone, hourlyForecast)
        }
    }

}

package com.arcmaksim.weatherapp.ui.activities

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
import com.arcmaksim.weatherapp.models.Forecast
import com.arcmaksim.weatherapp.models.Hour
import com.arcmaksim.weatherapp.ui.HourlyForecastArgs
import java.time.LocalDateTime
import java.time.ZoneOffset

class HourlyForecastActivity : ComponentActivity() {

    override fun onCreate(
        savedInstanceState: Bundle?,
    ) {
        super.onCreate(savedInstanceState)

        val hourlyForecast = intent.getParcelableExtra<HourlyForecastArgs>(MainActivity.HOURLY_FORECAST)?.hourlyForecast
            ?: emptyList()

        setContent {
            MaterialTheme {
                HourlyForecast(forecast = hourlyForecast)
            }
        }
    }

    @Composable
    fun HourlyForecast(
        forecast: List<Hour>,
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
                HourlyForecastLine(hour = item)
            }
        }
    }

    @Composable
    fun HourlyForecastLine(
        hour: Hour,
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
                text = hour.formattedTime,
                color = Color.White,
                style = TextStyle(
                    fontSize = 24.sp,
                ),
            )
            Icon(
                painter = painterResource(hour.iconId),
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
                Hour(
                    iconId = Forecast.resolveIconId("clear-day"),
                    time = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC),
                    temperature = 1,
                    summary = "Sunny",
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
        val hourlyForecast = (1..10).map { index ->
            Hour(
                iconId = Forecast.resolveIconId("clear-day"),
                time = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC),
                temperature = index,
                summary = "Sunny",
            )
        }

        MaterialTheme {
            HourlyForecast(hourlyForecast)
        }
    }

}

package com.arcmaksim.weatherapp.ui.activities

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arcmaksim.weatherapp.R
import com.arcmaksim.weatherapp.models.Day
import com.arcmaksim.weatherapp.models.Forecast
import com.arcmaksim.weatherapp.ui.DailyForecastArgs
import java.time.LocalDateTime
import java.time.ZoneOffset

class DailyForecastActivity : ComponentActivity() {

    override fun onCreate(
        savedInstanceState: Bundle?,
    ) {
        super.onCreate(savedInstanceState)

        val dailyForecast = intent.getParcelableExtra<DailyForecastArgs>(MainActivity.DAILY_FORECAST)?.forecast
            ?: emptyList()

        setContent {
            MaterialTheme {
                DailyForecast(forecast = dailyForecast)
            }
        }
    }

    @Composable
    fun DailyForecast(
        forecast: List<Day>,
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
                start = 64.dp,
                top = 4.dp,
                end = 32.dp,
                bottom = 4.dp,
            ),
        ) {
            items(forecast) { item ->
                DailyForecastLine(item)
            }
        }
    }

    @Composable
    fun DailyForecastLine(
        day: Day,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .background(Color.Transparent)
                .clickable {
                    val message = String.format(
                        "On %s the high will be %s and it will be %s",
                        day.dayOfTheWeek,
                        day.temperature,
                        day.summary
                    )
                    Toast
                        .makeText(this, message, Toast.LENGTH_LONG)
                        .show()
                },
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = day.temperature.toString(),
                color = Color.White,
                style = TextStyle(
                    fontSize = 24.sp,
                ),
            )
            Icon(
                modifier = Modifier.padding(start = 10.dp),
                painter = painterResource(day.iconId),
                tint = Color.White,
                contentDescription = stringResource(R.string.icon_image_view_content_desc),
            )
            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 10.dp),
                text = day.dayOfTheWeek,
                textAlign = TextAlign.End,
                color = Color.White,
                style = TextStyle(
                    fontSize = 20.sp,
                ),
            )
        }
    }

    @Preview(
        showBackground = true,
        backgroundColor = 0xFFF25019,
    )
    @Composable
    fun DailyForecastLinePreview() {
        MaterialTheme {
            DailyForecastLine(
                Day(
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
    fun DailyForecastScreenPreview() {
        val dailyForecast = (1..10).map { index ->
            Day(
                iconId = Forecast.resolveIconId("clear-day"),
                time = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC),
                temperature = index,
                summary = "Sunny",
            )
        }

        MaterialTheme {
            DailyForecast(dailyForecast)
        }
    }

}

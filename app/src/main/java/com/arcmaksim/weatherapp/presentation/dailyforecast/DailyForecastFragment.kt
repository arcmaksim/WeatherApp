package com.arcmaksim.weatherapp.presentation.dailyforecast

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.arcmaksim.weatherapp.R
import com.arcmaksim.weatherapp.domain.model.ForecastRecord
import com.arcmaksim.weatherapp.domain.model.WeatherType
import com.arcmaksim.weatherapp.presentation.toIconResId
import java.time.LocalDateTime
import java.time.ZoneOffset

class DailyForecastFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnDetachedFromWindow)

        val args: DailyForecastFragmentArgs by navArgs()

        setContent {
            MaterialTheme {
                DailyForecast(
                    args.timezone,
                    args.records.toList(),
                )
            }
        }
    }

    @Composable
    fun DailyForecast(
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
                start = 64.dp,
                top = 4.dp,
                end = 32.dp,
                bottom = 4.dp,
            ),
        ) {
            items(forecast) { item ->
                DailyForecastLine(timezone, item)
            }
        }
    }

    @Composable
    fun DailyForecastLine(
        timezone: String,
        day: ForecastRecord,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .background(Color.Transparent)
                .clickable {
                    val message = String.format(
                        "On %s the high will be %s and it will be %s",
                        day.getDayOfTheWeek(timezone),
                        day.temperature,
                        day.summary
                    )
                    Toast
                        .makeText(requireContext(), message, Toast.LENGTH_LONG)
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
                painter = painterResource(day.type.toIconResId()),
                tint = Color.White,
                contentDescription = stringResource(R.string.icon_image_view_content_desc),
            )
            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 10.dp),
                text = day.getDayOfTheWeek(timezone),
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
    fun DailyForecastScreenPreview() {
        val timezone = "Europe/Moscow"
        val dailyForecast = (1..10).map { index ->
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
            DailyForecast(timezone, dailyForecast)
        }
    }

}

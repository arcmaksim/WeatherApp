package com.arcmaksim.weatherapp.presentation.currentforecast

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.arcmaksim.weatherapp.R
import com.arcmaksim.weatherapp.domain.model.Forecast
import com.arcmaksim.weatherapp.domain.model.ForecastRecord
import com.arcmaksim.weatherapp.domain.model.ForecastRecordGroup
import com.arcmaksim.weatherapp.domain.model.WeatherType
import com.arcmaksim.weatherapp.presentation.toIconResId
import kotlinx.coroutines.flow.MutableStateFlow
import java.time.LocalDateTime
import java.time.ZoneOffset

@Composable
fun CurrentForecastScreen(
    viewModel: ICurrentForecastViewModel,
    showDailyForecast: (String, List<ForecastRecord>) -> Unit,
    showHourlyForecast: (String, List<ForecastRecord>) -> Unit,
) {
    val state by viewModel.state.collectAsState(CurrentForecastState())

    CurrentForecast(
        forecast = state.forecast,
        isLoading = state.isLoading,
        onRefresh = { viewModel.getForecast(true) },
        showDailyForecast = showDailyForecast,
        showHourlyForecast = showHourlyForecast,
    )

    state.error?.let {
        //alertUserAboutError()
    }
}

@Composable
fun CurrentForecast(
    forecast: Forecast?,
    isLoading: Boolean,
    onRefresh: () -> Unit,
    showDailyForecast: (String, List<ForecastRecord>) -> Unit,
    showHourlyForecast: (String, List<ForecastRecord>) -> Unit,
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFF9D34C),
                        Color(0xFFF25019),
                    )
                ),
            )
            .padding(
                start = 32.dp,
                end = 32.dp,
                top = 16.dp,
            ),
    ) {
        val (refreshButtonRef, progressBarRef) = createRefs()

        if (!isLoading) {
            Icon(
                modifier = Modifier
                    .constrainAs(refreshButtonRef) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                    }
                    .clickable {
                        onRefresh()
                    },
                tint = Color.White,
                painter = painterResource(R.mipmap.refresh),
                contentDescription = stringResource(R.string.degree_image_view_content_desc),
            )
        }
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.constrainAs(progressBarRef) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                },
            )
        }

        val (temperatureRef, temperatureIconRef) = createRefs()

        Text(
            modifier = Modifier.constrainAs(temperatureRef) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            text = forecast?.current?.temperature?.toString() ?: stringResource(R.string.default_number_label),
            color = Color.White,
            style = TextStyle(
                fontSize = 150.sp,
            ),
        )
        Icon(
            modifier = Modifier.constrainAs(temperatureIconRef) {
                start.linkTo(temperatureRef.end)
                top.linkTo(temperatureRef.top, margin = 50.dp)
            },
            tint = Color.White,
            painter = painterResource(R.mipmap.degree),
            contentDescription = stringResource(R.string.degree_image_view_content_desc),
        )

        val (timeRef, locationRef, iconRef) = createRefs()

        Text(
            modifier = Modifier.constrainAs(timeRef) {
                bottom.linkTo(temperatureRef.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            text = stringResource(
                R.string.time_label_text,
                forecast?.current?.getFormattedTime(forecast.timezone) ?: stringResource(R.string.default_time_label),
            ),
            color = Color(0x80FFFFFF),
            style = TextStyle(
                fontSize = 18.sp,
            ),
        )
        Text(
            modifier = Modifier.constrainAs(locationRef) {
                bottom.linkTo(timeRef.top, margin = 60.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            text = stringResource(R.string.default_location_label),
            color = Color(0x80FFFFFF),
            style = TextStyle(
                fontSize = 18.sp,
            ),
        )
        forecast?.let {
            Icon(
                modifier = Modifier.constrainAs(iconRef) {
                    start.linkTo(parent.start)
                    bottom.linkTo(locationRef.bottom)
                },
                tint = Color.White,
                painter = painterResource(it.current.type.toIconResId()),
                contentDescription = stringResource(R.string.icon_image_view_content_desc),
            )
        }

        val (humidityLabelRef, humidityValueRef, precipLabelRef, precipValueRef, summaryRef) = createRefs()

        Text(
            modifier = Modifier.constrainAs(humidityLabelRef) {
                top.linkTo(temperatureRef.bottom, margin = 10.dp)
                start.linkTo(parent.start)
                end.linkTo(precipLabelRef.start)
                width = Dimension.fillToConstraints
            },
            textAlign = TextAlign.Center,
            text = stringResource(R.string.humidity_label_name),
            color = Color(0x80FFFFFF),
        )
        Text(
            modifier = Modifier.constrainAs(humidityValueRef) {
                top.linkTo(humidityLabelRef.bottom)
                start.linkTo(humidityLabelRef.start)
                end.linkTo(humidityLabelRef.end)
            },
            text = forecast?.current?.humidity?.toString() ?: stringResource(R.string.default_number_label),
            color = Color.White,
            textAlign = TextAlign.Center,
            style = TextStyle(
                fontSize = 24.sp,
            ),
        )
        Text(
            modifier = Modifier.constrainAs(precipLabelRef) {
                top.linkTo(humidityLabelRef.top)
                bottom.linkTo(humidityLabelRef.bottom)
                start.linkTo(humidityLabelRef.end)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            },
            textAlign = TextAlign.Center,
            text = stringResource(R.string.rainSnow_label_name),
            color = Color(0x80FFFFFF),
        )
        Text(
            modifier = Modifier.constrainAs(precipValueRef) {
                top.linkTo(precipLabelRef.bottom)
                start.linkTo(precipLabelRef.start)
                end.linkTo(precipLabelRef.end)
            },
            text = forecast?.current?.precipitationProbability?.toString()
                ?: stringResource(R.string.default_number_label),
            color = Color.White,
            textAlign = TextAlign.Center,
            style = TextStyle(
                fontSize = 24.sp,
            ),
        )

        val barrier = createBottomBarrier(humidityValueRef, precipValueRef)

        Text(
            modifier = Modifier.constrainAs(summaryRef) {
                top.linkTo(barrier, margin = 40.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            text = forecast?.current?.summary ?: stringResource(R.string.default_summary),
            color = Color.White,
            textAlign = TextAlign.Center,
            style = TextStyle(
                fontSize = 18.sp,
            ),
        )

        val (hourlyButtonRef, dailyButtonRef) = createRefs()

        Button(
            modifier = Modifier
                .constrainAs(hourlyButtonRef) {
                    start.linkTo(parent.start)
                    end.linkTo(dailyButtonRef.start, margin = 1.dp)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.fillToConstraints
                }
                .background(
                    color = Color(0x40FFFFFF),
                ),
            elevation = null,
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
            onClick = {
                forecast?.let {
                    showHourlyForecast(it.timezone, it.hourlyForecast.records)
                }
            },
        ) {
            Text(
                color = Color.White,
                text = "HOURLY",
            )
        }

        Button(
            modifier = Modifier
                .constrainAs(dailyButtonRef) {
                    start.linkTo(hourlyButtonRef.end, margin = 1.dp)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.fillToConstraints
                }
                .width(0.dp)
                .background(
                    color = Color(0x40FFFFFF),
                ),
            elevation = null,
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
            onClick = {
                forecast?.let {
                    showDailyForecast(it.timezone, it.hourlyForecast.records)
                }
            },
        ) {
            Text(
                color = Color.White,
                text = "7 DAYS",
            )
        }
    }
}

@Preview(
    widthDp = 393,
    heightDp = 851,
    showBackground = true,
)
@Composable
fun CurrentForecastPreview() {
    val viewModel = object : ICurrentForecastViewModel {
        override val state: MutableStateFlow<CurrentForecastState>
            get() = MutableStateFlow(
                CurrentForecastState(
                    forecast = Forecast(
                        "Europe/Moscow",
                        current = ForecastRecord(
                            type = WeatherType.ClearDay,
                            time = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC),
                            temperature = 10,
                            summary = "Sunny",
                            precipitationProbability = 0,
                            apparentTemperature = 10,
                            windSpeed = 2,
                            windGust = 5,
                            humidity = 10,
                        ),
                        hourlyForecast = ForecastRecordGroup(
                            summary = "Summary",
                            type = WeatherType.ClearDay,
                            records = listOf(
                                ForecastRecord(
                                    type = WeatherType.ClearDay,
                                    time = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC),
                                    temperature = 10,
                                    summary = "Sunny",
                                    precipitationProbability = 0,
                                    apparentTemperature = 10,
                                    windSpeed = 2,
                                    windGust = 5,
                                    humidity = 10,
                                )
                            ),
                        ),
                        dailyForecast = ForecastRecordGroup(
                            summary = "Summary",
                            type = WeatherType.ClearDay,
                            records = listOf(
                                ForecastRecord(
                                    type = WeatherType.ClearDay,
                                    time = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC),
                                    temperature = 10,
                                    summary = "Sunny",
                                    precipitationProbability = 0,
                                    apparentTemperature = 10,
                                    windSpeed = 2,
                                    windGust = 5,
                                    humidity = 10,
                                )
                            ),
                        ),
                    ),
                    isLoading = false,
                    error = null,
                )
            )

        override fun getForecast(
            isRefreshing: Boolean,
        ) = Unit
    }

    MaterialTheme {
        CurrentForecastScreen(
            viewModel = viewModel,
            showHourlyForecast = { _, _ -> Unit },
            showDailyForecast = { _, _ -> Unit },
        )
    }
}

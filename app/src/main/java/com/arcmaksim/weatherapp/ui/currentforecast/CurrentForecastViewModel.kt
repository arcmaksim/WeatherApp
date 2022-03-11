package com.arcmaksim.weatherapp.ui.currentforecast

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arcmaksim.weatherapp.domain.IForecastInteractor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class CurrentForecastViewModel @Inject constructor(
    private val interactor: IForecastInteractor,
) : ViewModel(), ICurrentForecastViewModel {

    override val state = MutableStateFlow(CurrentForecastScreenState())

    override fun getForecast(
        isRefreshing: Boolean,
    ) {
        if (state.value.isLoading) return

        state.update {
            it.copy(
                isLoading = true,
            )
        }

        viewModelScope.launch {
            interactor.fetchForecast()
                .onFailure { error ->
                    state.update {
                        it.copy(
                            isLoading = false,
                            error = error.message ?: "Unknown error",
                        )
                    }
                }
                .onSuccess { forecast ->
                    state.update {
                        it.copy(
                            forecast = forecast,
                            isLoading = false,
                            error = null,
                        )
                    }
                }
        }
    }

}

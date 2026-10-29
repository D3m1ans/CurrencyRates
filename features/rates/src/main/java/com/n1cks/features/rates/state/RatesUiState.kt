package com.n1cks.features.rates.state

import com.n1cks.core.domain.model.RatesSnapshot

sealed class RatesUiState {
    object Loading : RatesUiState()
    data class Success(val snapshot: RatesSnapshot) : RatesUiState()
    data class Error(val message: String) : RatesUiState()
}
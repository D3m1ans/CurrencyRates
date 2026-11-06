package com.n1cks.features.rates.component

import com.arkivanov.decompose.value.Value
import com.n1cks.features.rates.state.RatesUiState
import kotlinx.coroutines.flow.StateFlow

interface RatesComponent {
    val state: Value<RatesUiState>
    val searchQuery: Value<String>
    val selectedCurrency: StateFlow<String>
    val inputAmount: StateFlow<Double>
    val convertedAmound: StateFlow<Double>
    fun refresh()
    fun onSearchQueryChange(query: String)
    fun selectCurrency(code: String?)
    fun setInputAmount(amount: Double)
}
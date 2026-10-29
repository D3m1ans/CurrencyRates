package com.n1cks.features.rates.component

import com.arkivanov.decompose.value.Value
import com.n1cks.features.rates.state.RatesUiState

interface RatesComponent {
    val state: Value<RatesUiState>
    val searchQuery: Value<String>
    fun refresh()
    fun onSearchQueryChange(query: String)
}
package com.n1cks.features.rates.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.n1cks.features.rates.component.RatesComponent
import com.n1cks.features.rates.ui.content.RatesContent

@Composable
fun RatesScreen(
    component: RatesComponent
) {
    val uiState by component.state.subscribeAsState()
    val searchQuery by component.searchQuery.subscribeAsState()
    val selectedCurrency by component.selectedCurrency.collectAsState()
    val inputAmount by component.inputAmount.collectAsState()
    val convertedAmount by component.convertedAmound.collectAsState()

    RatesContent(
        state = uiState,
        searchQuery = searchQuery,
        selectedCurrency = selectedCurrency,
        inputAmount = inputAmount,
        convertedAmount = convertedAmount,
        onSearchChange = component::onSearchQueryChange,
        onSelectCurrency = component::selectCurrency,
        onInputAmountChange = component::setInputAmount,
        onRefresh = component::refresh
    )
}
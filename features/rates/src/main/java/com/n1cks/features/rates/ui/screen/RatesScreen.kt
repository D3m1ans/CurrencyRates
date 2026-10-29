package com.n1cks.features.rates.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.n1cks.features.rates.component.RatesComponent
import com.n1cks.features.rates.ui.content.RatesContent

@Composable
fun RatesScreen(
    component: RatesComponent
) {
    val state by component.state.subscribeAsState()
    val searchQuery by component.searchQuery.subscribeAsState()

    RatesContent(
        state = state,
        searchQuery = searchQuery,
        onSearchQueryChange = component::onSearchQueryChange,
        onRefresh = { component.refresh() }
    )
}
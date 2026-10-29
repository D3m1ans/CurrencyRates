package com.n1cks.features.rates.ui.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.n1cks.core.domain.model.CurrencyRate
import com.n1cks.core.domain.model.RatesSnapshot
import com.n1cks.features.rates.state.RatesUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RatesContent(
    state: RatesUiState,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onRefresh: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Курсы ЦБ РФ") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onRefresh) {
                Icon(Icons.Default.Refresh, contentDescription = "Refresh")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = onSearchQueryChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                placeholder = { Text("Поиск по коду или названию") },
                singleLine = true
            )

            when (val s = state) {
                is RatesUiState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize()) {
                        CircularProgressIndicator(Modifier.align(Alignment.Center))
                    }
                }
                is RatesUiState.Error -> {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Text(s.message, Modifier.align(Alignment.Center))
                    }
                }
                is RatesUiState.Success -> {
                    val filteredRates = remember(s.snapshot.rates, searchQuery) {
                        if (searchQuery.isBlank()) {
                            s.snapshot.rates
                        } else {
                            s.snapshot.rates.filter { rate ->
                                rate.name.contains(searchQuery, ignoreCase = true) ||
                                        rate.code.contains(searchQuery, ignoreCase = true)
                            }
                        }
                    }

                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(filteredRates) { rate ->
                            RateItem(rate)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun RateItem(rate: CurrencyRate) {
    Card(
        elevation = CardDefaults.cardElevation(2.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        ListItem(
            headlineContent = {
                Text(
                    text = "${rate.name} (${rate.code})",
                    style = MaterialTheme.typography.titleMedium
                )
            },
            supportingContent = {
                Text(
                    text = "Цена: ${rate.value} руб. (Now: ${rate.nominal})",
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            trailingContent = {
                if (rate.previous != rate.value) {
                    val color = if (rate.value > rate.previous)
                        MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
                    Text(
                        text = if (rate.value > rate.previous) "▲" else "▼",
                        color = color,
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            }
        )
    }
}
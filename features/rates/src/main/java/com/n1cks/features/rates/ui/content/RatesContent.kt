package com.n1cks.features.rates.ui.content

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.n1cks.core.domain.model.CurrencyRate
import com.n1cks.features.rates.state.RatesUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RatesContent(
    state: RatesUiState,
    searchQuery: String,
    selectedCurrency: String,
    inputAmount: Double,
    convertedAmount: Double,
    onSearchChange: (String) -> Unit,
    onSelectCurrency: (String?) -> Unit,
    onInputAmountChange: (Double) -> Unit,
    onRefresh: () -> Unit
) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Курсы ЦБ РФ") }) },
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
            ConverterCard(
                rates = (state as? RatesUiState.Success)?.snapshot?.rates ?: emptyList(),
                selectedCode = selectedCurrency,
                amount = inputAmount,
                result = convertedAmount,
                onSelect = onSelectCurrency,
                onAmountChange = onInputAmountChange
            )

            OutlinedTextField(
                value = searchQuery,
                onValueChange = onSearchChange,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                placeholder = { Text("Поиск валюты...") },
                singleLine = true
            )

            Box(modifier = Modifier.fillMaxSize()) {
                when (val s = state) {
                    is RatesUiState.Loading -> {
                        CircularProgressIndicator(Modifier.align(Alignment.Center))
                    }
                    is RatesUiState.Error -> {
                        Text(s.message, Modifier.align(Alignment.Center))
                    }
                    is RatesUiState.Success -> {
                        val filteredRates = remember(s.snapshot.rates, searchQuery) {
                            if (searchQuery.isBlank()) s.snapshot.rates
                            else s.snapshot.rates.filter {
                                it.name.contains(searchQuery, ignoreCase = true) ||
                                        it.code.contains(searchQuery, ignoreCase = true)
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
}


@SuppressLint("DefaultLocale")
@Composable
private fun ConverterCard(
    rates: List<CurrencyRate>,
    selectedCode: String,
    amount: Double,
    result: Double,
    onSelect: (String?) -> Unit,
    onAmountChange: (Double) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Конвертер", style = MaterialTheme.typography.titleMedium)

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = if (amount == 0.0) "" else amount.toString(),
                    onValueChange = {
                        it.toDoubleOrNull()?.let(onAmountChange) ?: onAmountChange(0.0)
                    },
                    label = { Text("Сумма") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    modifier = Modifier.weight(1f)
                )

                CurrencyDropdown(
                    rates = rates,
                    selectedCode = selectedCode,
                    onSelect = onSelect,
                    modifier = Modifier.weight(1f)
                )
            }

            Text(
                text = "≈ ${String.format("%.2f", result)} ₽",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CurrencyDropdown(
    rates: List<CurrencyRate>,
    selectedCode: String,
    onSelect: (String?) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    val label = selectedCode.ifBlank { "RUB" }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        OutlinedTextField(
            readOnly = true,
            value = label,
            onValueChange = {},
            label = { Text("Валюта") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
            modifier = Modifier.menuAnchor().fillMaxWidth()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text("— RUB —") },
                onClick = { onSelect(null); expanded = false }
            )
            rates.forEach { rate ->
                DropdownMenuItem(
                    text = { Text("${rate.code} — ${rate.name}") },
                    onClick = { onSelect(rate.code); expanded = false }
                )
            }
        }
    }
}

@Composable
private fun RateItem(rate: CurrencyRate) {
    Card(modifier = Modifier.fillMaxWidth()) {
        ListItem(
            headlineContent = { Text("${rate.name} (${rate.code})") },
            supportingContent = { Text("Цена: ${rate.value} руб.") },
            trailingContent = {
                val diff = rate.value - rate.previous
                val symbol = if (diff > 0) "▲" else if (diff < 0) "▼" else "-"
                val color = when {
                    diff > 0 -> MaterialTheme.colorScheme.error
                    diff < 0 -> MaterialTheme.colorScheme.primary
                    else -> MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                }
                Text(symbol, color = color, style = MaterialTheme.typography.titleSmall)
            }
        )
    }
}
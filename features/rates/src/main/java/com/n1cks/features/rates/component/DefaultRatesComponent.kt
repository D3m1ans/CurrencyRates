package com.n1cks.features.rates.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.n1cks.core.domain.usecase.ConvertRatesUseCase
import com.n1cks.core.domain.usecase.ObserveRatesUseCases
import com.n1cks.core.domain.usecase.RefreshRatesUseCase
import com.n1cks.features.rates.state.RatesUiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class DefaultRatesComponent(
    componentContext: ComponentContext,
    private val observeRatesUseCases: ObserveRatesUseCases,
    private val refreshRatesUseCase: RefreshRatesUseCase,
    private val convertRatesUseCase: ConvertRatesUseCase,
) : RatesComponent, ComponentContext by componentContext {

    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    private val _state = MutableValue<RatesUiState>(RatesUiState.Loading)
    override val state: Value<RatesUiState> = _state

    private val _searchQuery = MutableValue("")
    override val searchQuery: Value<String> = _searchQuery


    private val _selectedCurrency = MutableStateFlow("")
    override val selectedCurrency: StateFlow<String> = _selectedCurrency.asStateFlow()

    private val _inputAmount = MutableStateFlow(100.0)
    override val inputAmount: StateFlow<Double> = _inputAmount.asStateFlow()

    private val _convertedAmound = MutableStateFlow(0.0)
    override val convertedAmound: StateFlow<Double> = _convertedAmound.asStateFlow()

    init {
        lifecycle.doOnDestroy { scope.cancel() }

        observeRates()

        combine(_inputAmount, _selectedCurrency) { amount, code ->
            Pair(amount, code)
        }.onEach { (amount, code) ->
            recalculate(amount, code.ifBlank { null })
        }.launchIn(scope)
    }

    private fun recalculate(amount: Double, code: String?) {
        val snapshot = (_state.value as? RatesUiState.Success)?.snapshot

        if (code == null || snapshot == null) {
            _convertedAmound.value = 0.0
            return
        }

        val rate = snapshot.rates.find { it.code == code }
        _convertedAmound.value = convertRatesUseCase(amount, rate)
    }

    private fun observeRates() {
        scope.launch {
            observeRatesUseCases().collect { snapshot ->
                _state.value = if (snapshot == null) {
                    RatesUiState.Error("No data available")
                } else {
                    RatesUiState.Success(snapshot)
                }
            }
        }
    }

    override fun refresh() {
        scope.launch {
            val currentState = _state.value
            if (currentState !is RatesUiState.Loading) {
                _state.value = RatesUiState.Loading
            }

            val result = refreshRatesUseCase()

            if (result.isFailure) {
                if (currentState is RatesUiState.Success) {
                    _state.value = currentState
                } else {
                    _state.value = RatesUiState.Error("Failed to update")
                }
            }
        }
    }

    override fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    override fun selectCurrency(code: String?) {
        _selectedCurrency.value = code ?: ""
    }

    override fun setInputAmount(amount: Double) {
        _inputAmount.value = amount
    }
}
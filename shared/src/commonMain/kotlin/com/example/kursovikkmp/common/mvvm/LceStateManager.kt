package com.example.kursovikkmp.common.mvvm

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


class LceStateManager {
    private val _lceState = MutableStateFlow(LceState())
    val lceState = _lceState.asStateFlow()

    private fun updateState(transform: LceState.() -> LceState) {
        _lceState.update { transform.invoke(lceState.value) }
    }

    fun showLoading() {
        updateState { copy(isLoading = true) }
    }

    fun hideLoading() {
        updateState { copy(isLoading = false) }
    }

    fun isLoading(): Boolean {
        return lceState.value.isLoading
    }

    fun showError(errorState: ErrorState.AllertError) {
        updateState { copy(errorState = errorState) }
    }

    fun hideError() {
        updateState { copy(errorState = null) }
    }
}
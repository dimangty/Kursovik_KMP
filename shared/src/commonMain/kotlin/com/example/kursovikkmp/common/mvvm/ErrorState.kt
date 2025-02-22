package com.example.kursovikkmp.common.mvvm

sealed class ErrorState {
    data class AllertError(val title: String,
                          val positiveButtonText: String = "OK",
                          val positiveAction: () -> Unit = {}): ErrorState()
}
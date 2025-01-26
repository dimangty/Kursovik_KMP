package com.example.kursovikkmp.common.mvvm

sealed class ErrorState {
    data class SheetError(val title: String,
                          val message: String,
                          val positiveButtonText: String = "OK",
                          val negativeButtonText: String? = null,
                          val positveAction: () -> Unit = {},
                          val negativeAction: () -> Unit = {}): ErrorState()
}
package com.example.kursovikkmp.common.mvvm

import com.example.kursovikkmp.MR
import com.example.kursovikkmp.common.view.TextState
import com.example.kursovikkmp.common.view.updateValue

sealed class ErrorState {
    data class ApiAlertError(
        val title: String,
        val isCancellable: Boolean = true,
        val positiveButtonText: String = "OK",
        val positiveAction: () -> Unit = {}
    ) : ErrorState() {
        companion object {
            fun getMock() = ApiAlertError(title = "Title")
        }

        val titleState: TextState = TextState.latoSemibold(17, MR.colors.black).updateValue(title)
        val positiveState: TextState = TextState.latoSemibold(13, MR.colors.primary).updateValue(positiveButtonText)
    }

    data class AlertError(
        val title: String,
        val message: String,
        val isCancellable: Boolean = true,
        val positiveButtonText: String = "OK",
        val positiveAction: () -> Unit = {},
        val negativeButtonText: String = "Cancel",
        val negativeAction: () -> Unit = {}
    ) : ErrorState() {

        companion object {
            fun getMock() = AlertError(title = "Title", message = "Message")
        }

        val titleState: TextState = TextState.latoSemibold(17, MR.colors.black).updateValue(title)
        val textState: TextState = TextState.latoRegular(13, MR.colors.black).updateValue(message)
        val positiveState: TextState =
            TextState.latoSemibold(15, MR.colors.primary).updateValue(positiveButtonText)
        val negativeState: TextState =
            TextState.latoSemibold(15, MR.colors.red).updateValue(negativeButtonText)
    }
}
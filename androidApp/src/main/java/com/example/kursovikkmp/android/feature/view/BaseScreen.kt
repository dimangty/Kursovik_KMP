package com.example.kursovikkmp.android.feature.view

import androidx.compose.runtime.Composable
import com.example.kursovikkmp.common.mvvm.LceState
import kotlinx.coroutines.flow.SharedFlow

@Composable
fun BaseScreen(
    lceState: LceState,
    content: @Composable () -> Unit) {

    content()

    LceStateHandlerView(
        lceState = lceState,
        isHandleErrors = false,
    )
}

@Composable
private fun LceStateHandlerView(
    lceState: LceState,
    isHandleErrors: Boolean,
) {
//    if (isHandleErrors) {
//        when (val errorState = lceState.errorState) {
//            null -> Unit
//            is ErrorState.FullScreenError -> {
//                FullScreenErrorView(errorState = errorState)
//            }
//        }
//    }

    if (lceState.isLoading) {
        LoadingDialog()
    }
}
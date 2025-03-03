package com.example.kursovikkmp.common.extensions.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner

@Composable
fun OnScreenStarted(
    lifecycleOwner: LifecycleOwner,
    action: () -> Unit,
) {
    val lifecycleState by lifecycleOwner.lifecycle.currentStateFlow.collectAsState()
    LaunchedEffect(lifecycleState) {
        when (lifecycleState) {
            Lifecycle.State.STARTED -> {
                action()
            }

            else -> {}
        }
    }
}

@Composable
fun OnScreenResumed(
    lifecycleOwner: LifecycleOwner,
    action: () -> Unit,
) {
    val lifecycleState by lifecycleOwner.lifecycle.currentStateFlow.collectAsState()
    LaunchedEffect(lifecycleState) {
        when (lifecycleState) {
            Lifecycle.State.RESUMED -> {
                action()
            }
            else -> {}
        }
    }
}

@Composable
fun OnScreenCreated(
    lifecycleOwner: LifecycleOwner,
    action: () -> Unit,
) {
    val lifecycleState by lifecycleOwner.lifecycle.currentStateFlow.collectAsState()
    LaunchedEffect(lifecycleState) {
        when (lifecycleState) {
            Lifecycle.State.CREATED -> {
                action()
            }

            else -> {}
        }
    }
}

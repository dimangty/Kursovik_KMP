package com.example.kursovikkmp.base

interface UiEvent

interface SingleClickUiEvent : UiEvent

/** Default ui events */
sealed class DefaultUiEvent : UiEvent {
    data object OnScreenCreated : DefaultUiEvent()
    data object OnScreenResumed : DefaultUiEvent()
    data object OnScreenDestroyed : DefaultUiEvent()
}
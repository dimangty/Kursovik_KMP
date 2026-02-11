package com.example.kursovikkmp.feature.auth.pin

import com.example.kursovikkmp.base.BaseEvent

sealed class PinEvents : BaseEvent {
    data class PinChanged(val pin: String) : PinEvents()
    object ConfirmTapped : PinEvents()
}

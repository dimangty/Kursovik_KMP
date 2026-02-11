package com.example.kursovikkmp.feature.profile

import com.example.kursovikkmp.base.BaseViewState
import com.example.kursovikkmp.common.view.TitleBarState

data class ProfileState(
    val fullName: String = "",
    val gender: String = "",
    val birthDate: String = "",
    val location: String = "",
    val email: String = "",
    val phone: String = "",
    val photoPath: String = "",
    val isMockData: Boolean = false,
    override val titleBarState: TitleBarState = TitleBarState.getMock()
) : BaseViewState

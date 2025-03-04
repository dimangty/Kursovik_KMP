package com.example.kursovikkmp.feature.home

import com.example.kursovikkmp.base.BaseViewState
import com.example.kursovikkmp.common.view.TitleBarState

data class HomeState(val tabs: List<String> = listOf(),
                     override val titleBarState: TitleBarState = TitleBarState.getMock()) : BaseViewState {
    companion object {
        fun getMock() = HomeState().run {
            copy(
                tabs = listOf("News", "Favorites",)
            )
        }
    }
}
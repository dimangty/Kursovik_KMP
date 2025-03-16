package com.example.kursovikkmp.feature.home

import com.example.kursovikkmp.MR
import com.example.kursovikkmp.base.BaseViewModel
import com.example.kursovikkmp.common.view.updateImage

class HomeViewModel(): BaseViewModel<HomeState, HomeEvents>() {
    init {
        initScreenData()
    }


    override fun initToolbar() {

    }

    override fun initScreenData() {
        updateState { copy(tabs = listOf(getString(MR.strings.scr_news_tab_title),
                                        getString(MR.strings.scr_favorite_tab_title)))}
    }

    override fun initialState(): HomeState = HomeState.getMock()

    override fun onEvent(event: HomeEvents) {

    }

}
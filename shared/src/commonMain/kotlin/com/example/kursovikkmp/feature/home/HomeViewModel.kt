package com.example.kursovikkmp.feature.home

import com.example.kursovikkmp.base.BaseViewModel

class HomeViewModel(): BaseViewModel<HomeState, HomeEvents>() {
    override fun initToolbar() {

    }

    override fun initScreenData() {

    }

    override fun initialState(): HomeState = HomeState.getMock()

    override fun onEvent(event: HomeEvents) {

    }

}
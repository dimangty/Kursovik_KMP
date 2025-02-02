package com.example.kursovikkmp.feature.login.mvvm

import com.example.kursovikkmp.base.BaseViewModel

class LoginViewModel: BaseViewModel<LoginState, LoginUIEvent>() {
    override fun initialState() = LoginState()
}
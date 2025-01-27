package com.example.kursovikkmp.base

import com.example.kursovikkmp.common.mvvm.ErrorState
import com.example.kursovikkmp.common.mvvm.LceStateManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import org.koin.mp.KoinPlatformTools

abstract class BaseViewModel<State: BaseViewState, Event: BaseEvent> {

    val viewModelScope = CoroutineScope(SupervisorJob())

    private val _state = MutableStateFlow(initialState())
    val state = _state.asStateFlow()

    private val _events = Channel<Event>()
    val events = _events.receiveAsFlow()

    protected val lceStateManager
            by KoinPlatformTools.defaultContext().get().inject<LceStateManager>()

    fun updateState(block: State.() -> State){
        _state.value = block(_state.value)
    }

    fun pushEvent(event: Event){
        viewModelScope.launch { _events.send(event) }
        onEvent(event)
    }

    fun onEvent(event: Event){

    }

    fun onDestroy(){
        viewModelScope.cancel()
    }

    open fun onCleared(){ }

    fun showLoader() {
        lceStateManager.showLoading()
    }

    fun hideLoader() {
        lceStateManager.hideLoading()
    }

    fun showError(errorState: ErrorState.SheetError) {
        lceStateManager.showError(errorState)
    }

    abstract fun initialState(): State
}
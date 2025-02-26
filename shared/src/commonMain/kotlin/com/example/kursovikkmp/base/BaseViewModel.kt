package com.example.kursovikkmp.base

import androidx.lifecycle.ViewModel
import com.example.kursovikkmp.common.mvvm.ErrorState
import com.example.kursovikkmp.common.mvvm.LceStateManager
import com.example.kursovikkmp.shared.common.extension.asCommonFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import org.koin.mp.KoinPlatformTools
import kotlin.experimental.ExperimentalObjCName
import kotlin.native.HiddenFromObjC
import kotlin.native.ObjCName

abstract class BaseViewModel<State: BaseViewState, Event: BaseEvent> : ViewModel() {

    val viewModelScope = CoroutineScope(SupervisorJob())

    val stateFlow = MutableStateFlow(initialState())
    val state: State get() = stateFlow.value
    val flowState = stateFlow.asStateFlow()

    @OptIn(ExperimentalObjCName::class)
    @ObjCName("stateFlow")
    val commonStateFlow get() = stateFlow.asCommonFlow()

    private val _events = Channel<Event>()
    val events = _events.receiveAsFlow()

    val lceStateManager
            by KoinPlatformTools.defaultContext().get().inject<LceStateManager>()

    val lceFlow get() = lceStateManager.lceState.asCommonFlow()

    abstract fun initToolbar()


    fun updateState(block: State.() -> State){
        stateFlow.value = block(stateFlow.value)
    }

    fun pushEvent(event: Event){
        viewModelScope.launch { _events.send(event) }
        onEvent(event)
    }

    abstract fun onEvent(event: Event)

    fun onDestroy(){
        viewModelScope.cancel()
    }

    fun showLoader() {
        lceStateManager.showLoading()
    }

    fun hideLoader() {
        lceStateManager.hideLoading()
    }

    fun showError(error: String) {
        showError(errorState = ErrorState.AllertError(title = error))
    }

    fun showError(errorState: ErrorState.AllertError) {
        lceStateManager.showError(errorState)
    }

    abstract fun initialState(): State
}
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
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.mp.KoinPlatformTools
import kotlin.experimental.ExperimentalObjCName
import kotlin.experimental.ExperimentalObjCRefinement
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
    val lceState get() = lceStateManager.lceState

    private var isScreenDataInitialized = false

    abstract fun initToolbar()
    abstract fun initScreenData()

    open fun onScreenResumed() {

    }

    open fun onScreenCreated() {

    }

    open fun onScreenDestroyed() {

    }

    fun updateState(block: State.() -> State){
        stateFlow.value = block(stateFlow.value)
    }

    fun pushEvent(event: Event){
        viewModelScope.launch { _events.send(event) }
        onEvent(event)
    }

    @OptIn(ExperimentalObjCRefinement::class)
    @HiddenFromObjC
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

    fun onDefaultUiEvent(event: DefaultUiEvent) {

        when (event) {
            DefaultUiEvent.OnScreenCreated -> {
                if (!isScreenDataInitialized) {
                    isScreenDataInitialized = true
                    initializeScreenData()
                }
                onScreenCreated()
            }

            DefaultUiEvent.OnScreenResumed -> onScreenResumed()
            DefaultUiEvent.OnScreenDestroyed -> {
                onScreenDestroyed()
            }
        }
    }

    private fun initializeScreenData() {
        initToolbar()
        initScreenData()
    }

    abstract fun initialState(): State
}
package com.example.kursovikkmp.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kursovikkmp.common.mvvm.ErrorState
import com.example.kursovikkmp.common.mvvm.LceStateManager
import com.example.kursovikkmp.feature.device.DeviceService
import com.example.kursovikkmp.feature.device.ResourceService
import com.example.kursovikkmp.navigation.NavigationAction
import com.example.kursovikkmp.navigation.NavigationService
import com.example.kursovikkmp.shared.common.extension.asCommonFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import dev.icerock.moko.resources.StringResource
import org.koin.mp.KoinPlatform.getKoin
import org.koin.mp.KoinPlatformTools
import kotlin.experimental.ExperimentalObjCName
import kotlin.experimental.ExperimentalObjCRefinement
import kotlin.native.HiddenFromObjC
import kotlin.native.ObjCName

abstract class BaseViewModel<State: BaseViewState, Event: BaseEvent> : ViewModel() {

    private val navigationService by getKoin().inject<NavigationService>()
    private val resourceService
            by getKoin().inject<ResourceService>()

    val deviceService by getKoin().inject<DeviceService>()

    val stateFlow = MutableStateFlow(initialState())
    val state: State get() = stateFlow.value
    val flowState = stateFlow.asStateFlow()

    @OptIn(ExperimentalObjCName::class)
    @ObjCName("stateFlow")
    val commonStateFlow get() = stateFlow.asCommonFlow()

    // For iOS
    private val _navigationEffect = MutableSharedFlow<NavigationAction>()
    val navigationEffectFlow = _navigationEffect.asCommonFlow()


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
        showError(errorState = ErrorState.ApiAlertError(title = error, positiveAction = {
            hideError()
        }))
    }

    private fun showError(errorState: ErrorState.ApiAlertError) {
        lceStateManager.showError(errorState)
    }

    fun showAlert(alert: ErrorState.AlertError) {
        lceStateManager.showAlert(alert)
    }

    fun hideError() {
        lceStateManager.hideError()
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

            DefaultUiEvent.OnBackClicked -> {
                navigateBack()
            }
        }
    }

    private fun initializeScreenData() {
        initToolbar()
        initScreenData()
    }

    protected fun navigate(navigationAction: NavigationAction) {
        navigationService.navigate(navigationAction)

        if (deviceService.isIOS()) {
            viewModelScope.launch {
                _navigationEffect.emit(navigationAction)
            }
        }
    }

    protected open fun navigateBack() {
        navigationService.navigateBack()

        if (deviceService.isIOS()) {
            viewModelScope.launch {
                _navigationEffect.emit(NavigationAction.NavigateBack)
            }
        }
    }

    protected fun getString(stringRes: StringResource): String {
        return resourceService.getString(stringRes)
    }

    abstract fun initialState(): State
}
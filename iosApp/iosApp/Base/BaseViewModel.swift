//
//  BaseViewModel.swift
//  iosApp
//
//  Created by dmitry on 4/12/2567 BE.
//
import Combine
import Foundation
import shared

class BaseViewModel<T: AnyObject, S: AnyObject>: ObservableObject  {
    
    var mViewModel: T?
    var lceState: LceState?
    
    @Published var state: S
    @Published var titleBar: TitleBarState?

    private var cancellable = [AnyCancellable]()
    
    @Injected private var contentService: ContentService?
    
    init() {
        mViewModel = inject()
        let model = mViewModel as! shared.BaseViewModel<shared.UiEvent, S>
        state = model.state
        observe(model: model)
        model.onDefaultUiEvent(event: .OnScreenCreated())
    }
    
    init(param: Any?) {
        mViewModel = inject(param: param)
        let model = mViewModel as! shared.BaseViewModel<shared.UiEvent, S>
        state = model.state
        observe(model: model)
        model.onDefaultUiEvent(event: .OnScreenCreated())
    }
    
    init(parameters: [Any?]) {
        mViewModel = inject(parameters: parameters)
        let model = mViewModel as! shared.BaseViewModel<shared.UiEvent, S>
        state = model.state
        observe(model: model)
        model.onDefaultUiEvent(event: .OnScreenCreated())
    }

    init(mViewModel: T) {
        self.mViewModel = mViewModel
        let model = mViewModel as! shared.BaseViewModel<shared.UiEvent, S>
        state = model.state
        observe(model: model)
        model.onDefaultUiEvent(event: .OnScreenCreated())
    }

    deinit {
        let model = mViewModel as! shared.BaseViewModel<shared.UiEvent, S>
        model.onDefaultUiEvent(event: .OnScreenDestroyed())
        
        mViewModel = nil
        lceState = nil
        
    }

    func onChangeState(_ state: S) {

    }

    func onChangeNavigation(_ action: NavigationAction) {

    }
    
    func onDefaultEffect(_ effect: Any) {
        
    }
    
    func onEffect(_ effect: Any?) {
        
    }

    final func sendViewAppearedEvent() {
        let model = mViewModel as? shared.BaseViewModel<shared.UiEvent, S>
        model?.onDefaultUiEvent(event: .OnScreenResumed())
    }

    func observe(model: shared.BaseViewModel<shared.UiEvent, S>) {
        model.defaultEffectFlow.watch(block: { [weak self] value in
            self?.errorService?.showError(by: value)
            self?.onDefaultEffect(value)
        })
        
        model.effectFlow.watch(block: { [weak self] value in
            self?.onEffect(value)
        })
        
        model.stateFlow.watch { [weak self] state in
            guard let newState = state, let state = newState.state else { return }
            self?.state = state
            self?.lceState = newState.lceState
            self?.titleBar = newState.titleBarState
            self?.updateView()
            self?.onChangeState(state)
        }

        model.navigationEffectFlow.watch { [weak self] action in
            guard let action = action else { return }
            print("BASE \(self.debugDescription) --- \(action)")
            self?.onChangeNavigation(action)
        }
    }
    
    private func updateView() {
        contentService?.showFullscreenLoader(lceState?.isLoading ?? false, animated: true)

        if let error = lceState?.errorState as? FullScreenError {
            contentService?.showFullStateError(error: error)
        } else {
            contentService?.hideFullStateError()
        }
    }
}

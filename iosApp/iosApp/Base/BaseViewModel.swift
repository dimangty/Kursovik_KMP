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
        let model = mViewModel as! shared.BaseViewModel<S, shared.BaseEvent>
        state = model.state
        observe(model: model)
        model.onDefaultUiEvent(event: .OnScreenCreated())
    }
    
    init(param: Any?) {
        mViewModel = inject(param: param)
        let model = mViewModel as! shared.BaseViewModel<S, shared.BaseEvent>
        state = model.state 
        observe(model: model)
        model.onDefaultUiEvent(event: .OnScreenCreated())
    }
    
    init(parameters: [Any?]) {
        mViewModel = inject(parameters: parameters)
        let model = mViewModel as! shared.BaseViewModel<S, shared.BaseEvent>
        state = model.state
        
        observe(model: model)
        model.onDefaultUiEvent(event: .OnScreenCreated())
    }


    deinit {
        let model = mViewModel as! shared.BaseViewModel<S, shared.BaseEvent>
        //model.onDefaultUiEvent(event: .OnScreenDestroyed())
        
        mViewModel = nil
        lceState = nil
        
    }
    
    func observe(model: shared.BaseViewModel<S, shared.BaseEvent>) {
        model.stateFlow.watch { [weak self] state in
            guard let newState = state else { return }
            self?.state = newState
            if let base = newState as? BaseViewState {
                self?.titleBar = base.titleBarState
            }
            self?.onChangeState(newState)
        }
        
        model.lceFlow.watch { [weak self] lceState in
            self?.lceState = lceState
            self?.updateView()
        }
        
        model.navigationEffectFlow.watch { [weak self] action in
            guard let action = action else { return }
            self?.onChangeNavigation(action)
        }
        
    }

    func onChangeState(_ state: S) {

    }

    func onChangeNavigation(_ action: NavigationAction) {

    }
    
    func onDefaultEffect(_ effect: Any) {
        
    }
    
    func onEffect(_ effect: Any?) {
        
    }

    
    
    private func updateView() {
        if let isLoading = lceState?.isLoading,
           isLoading == true {
            contentService?.showLoader()
        } else {
            contentService?.hideLoader()
        }
//        
//        if let error = lceState?.errorState as? FullScreenError {
//            contentService?.showFullStateError(error: error)
//        } else {
//            contentService?.hideFullStateError()
//        }
    }
}

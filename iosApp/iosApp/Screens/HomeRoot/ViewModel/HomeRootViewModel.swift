//
//  HomeRootViewModel.swift
//  EPS
//
//  Created by Dmitry on 04/03/2025.
//  Copyright © 2025 EPS. All rights reserved.
//

import Combine
import Foundation
import shared

final class HomeRootViewModel: BaseViewModel<HomeViewModel, HomeState> {
    @Published var tabSelection: Int = 0
    @Published var tabArray = ["News", "Favorite"]
    
    required override init() {
        super.init()
    }

    // MARK: - HomeRootViewOutput methods
    func didLoad() {
        
    }

    func onEvent(event: HomeRootViewActions) {
    	switch event {
        case .empty:
            //mViewModel?.onUiEvent(event: )
            break
        }
    }
    
    override func onChangeState(_ state: HomeState) {
        tabArray = state.tabs
    }

}

// MARK: - HomeRootModuleInput methods
extension HomeRootViewModel: HomeRootModuleInput {

    func configure(data: HomeRootConfigData) {

    }

}

//
//  FavoritesViewModel.swift
//  EPS
//
//  Created by Dmitry on 23/02/2025.
//  Copyright © 2025 EPS. All rights reserved.
//

import Combine
import Foundation
import shared

final class FavoritesViewModel: BaseViewModel<AnyObject, AnyObject> {

    required override init() {
        super.init()
    }

    // MARK: - FavoritesViewOutput methods
    func didLoad() {
        
    }

    func onEvent(event: FavoritesViewActions) {
    	switch event {
        case .empty:
            //mViewModel?.onUiEvent(event: )
            break
        }
    }

}

// MARK: - FavoritesModuleInput methods
extension FavoritesViewModel: FavoritesModuleInput {

    func configure(data: FavoritesConfigData) {

    }

}

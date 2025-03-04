//
//  FavoriteDetailsViewModel.swift
//  EPS
//
//  Created by Dmitry on 04/03/2025.
//  Copyright © 2025 EPS. All rights reserved.
//

import Combine
import Foundation
import shared

final class FavoriteDetailsViewModel: BaseViewModel<shared.FavoriteDetailsViewModel, FavoriteDetailsState> {

    init(title: String) {
        super.init(param: title)
    }

    // MARK: - FavoriteDetailsViewOutput methods
    func didLoad() {
        
    }

    func onEvent(event: FavoriteDetailsViewActions) {
    	switch event {
        case .openClicked:
            mViewModel?.pushEvent(event: .OnOpenClicked())
            break
        }
    }

}

// MARK: - FavoriteDetailsModuleInput methods
extension FavoriteDetailsViewModel: FavoriteDetailsModuleInput {

    func configure(data: FavoriteDetailsConfigData) {

    }

}

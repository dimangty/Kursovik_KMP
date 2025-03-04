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

struct FavoritesRow: Identifiable {
        let id = UUID()
        var items = [FavoriteUiState]()
}

final class FavoritesViewModel: BaseViewModel<FavoritesListViewModel, FavoritesListState> {

    required override init() {
        super.init()
    }

    // MARK: - FavoritesViewOutput methods
    func didLoad() {
        
    }

    func onEvent(event: FavoritesViewActions) {
    	switch event {
        case .articleTapped(let title):
            break
        case .favoriteTapped(let title):
            mViewModel?.pushEvent(event: .OnFavoriteClicked(title: title))
        }
    }
    
    override func onChangeState(_ state: FavoritesListState) {

    }

}

// MARK: - FavoritesModuleInput methods
extension FavoritesViewModel: FavoritesModuleInput {

    func configure(data: FavoritesConfigData) {

    }

}

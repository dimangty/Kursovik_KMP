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
    @Published var rows: [FavoritesRow] = []
    
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
        }
    }
    
    override func onChangeState(_ state: FavoritesListState) {
        var result: [FavoritesRow] = []
        
        for (index, element) in state.favoritesItems.enumerated() {
            if index % 2 == 1 { continue }
            
            if index + 1 >= state.favoritesItems.count {
                let favoriteRow: FavoritesRow = FavoritesRow(items: [element])
                result.append(favoriteRow)
            } else {
                let items: [FavoriteUiState] = [state.favoritesItems[index], state.favoritesItems[index + 1]]
                let favoriteRow: FavoritesRow = FavoritesRow(items: items)
                result.append(favoriteRow)
            }
        }
        
        rows = result
    }

}

// MARK: - FavoritesModuleInput methods
extension FavoritesViewModel: FavoritesModuleInput {

    func configure(data: FavoritesConfigData) {

    }

}

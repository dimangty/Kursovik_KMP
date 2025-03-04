//
//  NewsDetailsViewModel.swift
//  EPS
//
//  Created by Dmitry on 23/02/2025.
//  Copyright © 2025 EPS. All rights reserved.
//

import Combine
import Foundation
import shared

final class NewsDetailsViewModel: BaseViewModel<shared.NewsDetailsViewModel, NewsDetailsState> {

    init(title: String) {
        super.init(param: title)
    }

    // MARK: - NewsDetailsViewOutput methods
    func didLoad() {
        
    }

    func onEvent(event: NewsDetailsViewActions) {
        switch event {
        case .favoriteTapped:
            mViewModel?.pushEvent(event: .OnFavoriteClicked())
        case .openTapped:
            mViewModel?.pushEvent(event: .OnOpenClicked())
        }
    }

}

// MARK: - NewsDetailsModuleInput methods
extension NewsDetailsViewModel: NewsDetailsModuleInput {

    func configure(data: NewsDetailsConfigData) {

    }

}

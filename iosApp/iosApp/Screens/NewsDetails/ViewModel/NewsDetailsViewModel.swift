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

final class NewsDetailsViewModel: BaseViewModel<AnyObject, AnyObject> {

    required override init() {
        super.init()
    }

    // MARK: - NewsDetailsViewOutput methods
    func didLoad() {
        
    }

    func onEvent(event: NewsDetailsViewActions) {
    	switch event {
        case .empty:
            //mViewModel?.onUiEvent(event: )
            break
        }
    }

}

// MARK: - NewsDetailsModuleInput methods
extension NewsDetailsViewModel: NewsDetailsModuleInput {

    func configure(data: NewsDetailsConfigData) {

    }

}

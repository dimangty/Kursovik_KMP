//
//  NewsListViewModel.swift
//  EPS
//
//  Created by Dmitry on 23/02/2025.
//  Copyright © 2025 EPS. All rights reserved.
//

import Combine
import Foundation
import shared

final class NewsListViewModel: BaseViewModel<NewsListViewModel, NewsListState> {

    required override init() {
        super.init()
    }

    // MARK: - NewsListViewOutput methods
    func didLoad() {
        
    }

    func onEvent(event: NewsListViewActions) {
    	switch event {
        case .empty:
            //mViewModel?.onUiEvent(event: )
            break
        }
    }
    
    override func onChangeState(_ state: NewsListState) {
        print("News = \(state.newsItems.count)")
    }

}

// MARK: - NewsListModuleInput methods
extension NewsListViewModel: NewsListModuleInput {

    func configure(data: NewsListConfigData) {

    }

}

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

final class NewsListViewModel: BaseViewModel<shared.NewsListViewModel, NewsListState> {

    required override init() {
        super.init()
    }

    // MARK: - NewsListViewOutput methods
    func didLoad() {
        
    }

    func onEvent(event: NewsListViewActions) {
    	switch event {
        case .articleTapped(let item):
            mViewModel?.pushEvent(event: .OnNewsClicked(title: item))
            break
        }
    }
    
    override func onChangeState(_ state: NewsListState) {
        print("\nNews = \(state.newsItems.count)")
    }

}

// MARK: - NewsListModuleInput methods
extension NewsListViewModel: NewsListModuleInput {

    func configure(data: NewsListConfigData) {

    }

}

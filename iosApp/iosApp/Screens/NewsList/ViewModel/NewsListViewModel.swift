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
        case .articleTapped(let title):
            mViewModel?.pushEvent(event: .OnItemClicked(title: title))
            break
        case .favoriteTapped(let title):
            mViewModel?.pushEvent(event: .OnFavoriteClicked(title: title))
        case .searchChanged(let query):
            mViewModel?.pushEvent(event: .OnSearchQueryChanged(query: query))
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

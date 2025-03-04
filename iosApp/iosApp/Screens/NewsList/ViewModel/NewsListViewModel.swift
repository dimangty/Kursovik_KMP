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
    @Published var isShowingDetails: Bool = false
    var selectedItem: String = ""
    
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
        }
    }
    
    override func onChangeState(_ state: NewsListState) {
        print("\nNews = \(state.newsItems.count)")
    }
    
    override func onChangeNavigation(_ action: NavigationAction) {
        if let action = action as? NavigationAction.NavigateToNewsDetails {
            selectedItem = action.title
            isShowingDetails = true
        }
    }

}

// MARK: - NewsListModuleInput methods
extension NewsListViewModel: NewsListModuleInput {

    func configure(data: NewsListConfigData) {

    }

}

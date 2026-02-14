//
//  HomeRootView.swift
//  EPS
//
//  Created by Dmitry on 04/03/2025.
//  Copyright © 2025 EPS. All rights reserved.
//

import SwiftUI

struct HomeRootView: View {

    @StateObject private var viewModel = HomeRootViewModel()
    private let defaultTabs = ["News", "Favorites", "Рецепты", "Холодильник"]

    private var mainTabs: [String] {
        let tabs = Array(viewModel.tabArray.prefix(4))
        return tabs.count == 4 ? tabs : defaultTabs
    }

    var body: some View {
        TabView(selection: $viewModel.tabSelection) {
            NewsListView()
                .tabItem {
                    Image(systemName: "newspaper")
                    Text(mainTabs[0])
                }
                .tag(0)

            FavoritesView()
                .tabItem {
                    Image(systemName: "list.bullet")
                    Text(mainTabs[1])
                }
                .tag(1)

            RecipesView()
                .tabItem {
                    Image(systemName: "fork.knife")
                    Text(mainTabs[2])
                }
                .tag(2)

            FridgeTabView()
                .tabItem {
                    Image(systemName: "snowflake")
                    Text(mainTabs[3])
                }
                .tag(3)
        }
        .edgesIgnoringSafeArea(.top)
    }

}

#Preview {
    HomeRootView()
}

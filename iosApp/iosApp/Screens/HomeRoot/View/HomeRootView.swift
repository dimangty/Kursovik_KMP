//
//  HomeRootView.swift
//  EPS
//
//  Created by Dmitry on 04/03/2025.
//  Copyright © 2025 EPS. All rights reserved.
//

import SwiftUI
import SUINavigation

struct HomeRootView: View {

    @StateObject private var viewModel = HomeRootViewModel()

    var body: some View {
        TabView(selection: $viewModel.tabSelection) {
            NavigationViewStorage {
                NewsListView()
            }.tabItem {
                Image(systemName: "person.crop.circle")
                Text(viewModel.tabArray[0])
            }.tag(0)
            
            NavigationViewStorage {
                FavoritesView()
            }.tabItem {
                Image(systemName: "list.bullet")
                Text(viewModel.tabArray[1])
            }.tag(1)
        }
    }

}

#Preview {
    HomeRootView()
}

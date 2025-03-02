//
//  NewsListView.swift
//  EPS
//
//  Created by Dmitry on 23/02/2025.
//  Copyright © 2025 EPS. All rights reserved.
//

import SwiftUI

struct NewsListView: View {

    @StateObject private var viewModel = NewsListViewModel()

    var body: some View {
        ZStack {
            Rectangle()
                .fill(viewModel.state.backGroundColor.uiColor.toColor())
                .edgesIgnoringSafeArea(.all)
            ScrollView {
                LazyVStack {
                    ForEach(0 ..< viewModel.state.newsItems.count, id: \.self) { index in
                        let state = viewModel.state.newsItems[index]
                        NewsListItemView(state: state) {
                            viewModel.onEvent(event: .articleTapped(state.title))
                        }.padding(.top, 8)
                    }
                }
            }.padding(.horizontal, 16)
        }

         

    }

}

#Preview {
    NewsListView()
}

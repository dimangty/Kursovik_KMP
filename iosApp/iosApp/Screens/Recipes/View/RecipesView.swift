//
//  RecipesView.swift
//  EPS
//
//  Created by Dmitry on 04/03/2025.
//  Copyright © 2025 EPS. All rights reserved.
//

import SwiftUI
import shared

struct RecipesView: View {
    @StateObject private var viewModel = RecipesViewModel()

    var body: some View {
        ZStack {
            Rectangle()
                .fill(viewModel.state.backGroundColor.uiColor.toColor())
                .edgesIgnoringSafeArea(.all)
            VStack {
                CustomNavigationStateView(
                    titleBar: viewModel.state.titleBarState,
                    trailingView: AnyView(
                        NavigationLink(destination: ProfileTabView()) {
                            Image(systemName: "person.crop.circle")
                                .font(.title3)
                        }
                    )
                )
                ScrollView {
                    LazyVStack(spacing: 12) {
                        ForEach(0 ..< viewModel.state.recipesItems.count, id: \.self) { index in
                            let state = viewModel.state.recipesItems[index]
                            RecipeItemView(state: state)
                                .onTapGesture {
                                    viewModel.onEvent(.recipeTapped(state.id))
                                }
                        }

                        if viewModel.state.recipesItems.isEmpty {
                            Button("Повторить") {
                                viewModel.onEvent(.retryTapped)
                            }
                            .padding(.top, 8)
                        }
                    }
                }
                .padding(.horizontal, 16)
                .padding(.top, 4)
            }
            .navigationBarHidden(true)
        }
        .onAppear {
            viewModel.sendViewAppearedEvent()
        }
    }
}

private struct RecipeItemView: View {
    let state: RecipeUiState

    var body: some View {
        ZStack(alignment: .topLeading) {
            Rectangle()
                .fill(state.cellBackground.uiColor.toColor())
                .cornerRadius(22)
            VStack(alignment: .leading, spacing: 8) {
                if let url = URL(string: state.imageUrl), !state.imageUrl.isEmpty {
                    AsyncImage(url: url) { image in
                        image
                            .resizable()
                            .scaledToFill()
                    } placeholder: {
                        Rectangle()
                            .fill(Color.gray.opacity(0.15))
                    }
                    .frame(maxWidth: .infinity)
                    .frame(height: 140)
                    .clipShape(RoundedRectangle(cornerRadius: 12))
                }
                TextWithState(state.titleState)
                TextWithState(state.textState)
                    .lineLimit(2)
                TextWithState(state.durationState)
            }
            .frame(maxWidth: .infinity, alignment: .leading)
            .padding(16)
        }
        .frame(maxWidth: .infinity, alignment: .leading)
        .fixedSize(horizontal: false, vertical: true)
    }
}

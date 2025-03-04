//
//  NewsDetailsView.swift
//  EPS
//
//  Created by Dmitry on 23/02/2025.
//  Copyright © 2025 EPS. All rights reserved.
//

import SwiftUI

struct NewsDetailsView: View {

    @StateObject private var viewModel: NewsDetailsViewModel

    var body: some View {
        VStack {
            VStack(spacing: 8) {
                AsyncImage(url: URL(string: viewModel.state.imageUrl ?? "")) { image in
                    image
                        .resizable()
                        .scaledToFill()
                } placeholder: {
                    EmptyView()
                }
                .frame(height: 132)
                .cornerRadius(22)
                
                details
                
            }.padding(.horizontal, 16)
            
        }
        
    }
    
    var details: some View {
        VStack {
            HStack{
                TextWithState(viewModel.state.dateState)
                Spacer()
                ButtonWithState(viewModel.state.favoriteButton) {
                    viewModel.onEvent(event: .favoriteTapped)
                }
            }
            HStack {
                TextWithState(viewModel.state.titleState)
                Spacer()
            }.padding(.vertical, 16)
            
            HStack {
                TextWithState(viewModel.state.textState)
                    .lineLimit(2)
                Spacer()
            }
            
            Spacer()
            ButtonWithState(viewModel.state.openButton) {
                viewModel.onEvent(event: .openTapped)
            }.padding(.bottom, 16)
        }
    }
    
    init(title: String) {
        _viewModel = StateObject(wrappedValue: NewsDetailsViewModel(title: title))
    }

}

#Preview {
    NewsDetailsView(title: "Test")
}

//
//  FavoriteDetailsView.swift
//  EPS
//
//  Created by Dmitry on 04/03/2025.
//  Copyright © 2025 EPS. All rights reserved.
//

import SwiftUI
import SUINavigation

struct FavoriteDetailsView: View {

    @StateObject private var viewModel: FavoriteDetailsViewModel
    @OptionalEnvironmentObject private var navigationStorage: NavigationStorage?
    
    var body: some View {
        VStack {
            CustomNavigationStateView(titleBar: viewModel.state.titleBarState) {
                navigationStorage?.pop()
            }.padding(.bottom, 20)
            
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
            
        }.navigationBarHidden(true)
        
    }
    
    var details: some View {
        VStack(alignment: .leading) {
            HStack{
                TextWithState(viewModel.state.dateState)
            }
            HStack {
                TextWithState(viewModel.state.titleState)
            }.padding(.vertical, 16)
            
            HStack {
                TextWithState(viewModel.state.textState)
                    .lineLimit(2)
            }
            
            Spacer()
            ButtonWithState(viewModel.state.openButton) {
                viewModel.onEvent(event: .openClicked)
            }.padding(.bottom, 16)
        }
    }
    
    init(title: String) {
        _viewModel = StateObject(wrappedValue: FavoriteDetailsViewModel(title: title))
    }

}



#Preview {
    FavoriteDetailsView(title: "")
}

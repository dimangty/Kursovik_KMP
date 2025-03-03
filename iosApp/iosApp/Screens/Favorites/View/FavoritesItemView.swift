//
//  FavoritesItemView.swift
//  iosApp
//
//  Created by Дмитрий Быков on 02.03.2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI
import shared

struct FavoritesItemView: View {
    let state: FavoriteUiState
    let favoriteTapped: VoidBlock?
    let widht: CGFloat
    
    var body: some View {
        ZStack(alignment: .top) {
            Rectangle()
                .fill(state.cellBackground.uiColor.toColor())
                .cornerRadius(22)
            
            VStack {
                AsyncImage(url: URL(string: state.imageUrl ?? "")) { image in
                    image
                        .resizable()
                        .frame(width: widht, height: 132)
                } placeholder: {
                    EmptyView()
                }
                .cornerRadius(22, corners: [.topLeft, .topRight])
                
                content.frame(width: widht)
                
            }
        }
    }
    
    var content: some View {
        VStack {
            HStack(alignment: .top) {
                TextWithState(state.dateState)
                Spacer()
                ButtonWithState(state.favoriteButton) {
                    favoriteTapped?()
                }
            }
            HStack {
                TextWithState(state.titleState)
                Spacer()
            }.padding(.vertical, 2)
        }.padding(.bottom, 8)
         .padding(.horizontal, 12)
    }
}

#Preview {
    FavoritesItemView(state: .companion.getMock(),
                      favoriteTapped: nil,
                      widht: 100)
}

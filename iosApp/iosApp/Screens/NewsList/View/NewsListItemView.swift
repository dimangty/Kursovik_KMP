//
//  NewsListItemView.swift
//  iosApp
//
//  Created by Дмитрий Быков on 25.02.2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI
import shared

struct NewsListItemView: View {
    let state: NewsUiState
    let favoriteTapped: VoidBlock?
    
    var body: some View {
        ZStack(alignment: .top) {
            Rectangle()
                .fill(state.cellBackground.uiColor.toColor())
                .cornerRadius(22)
            
           
            
            VStack {
                AsyncImage(url: URL(string: state.imageUrl ?? "")) { image in
                    image
                        .resizable()
                        .scaledToFill()
                } placeholder: {
                    EmptyView()
                }
                .frame(height: 132)
                .cornerRadius(22, corners: [.topLeft, .topRight])
                VStack {
                    HStack{
                        TextWithState(state.dateState)
                        Spacer()
                        ButtonWithState(state.favoriteButton) {
                            favoriteTapped?()
                        }
                    }
                    HStack {
                        TextWithState(state.titleState)
                        Spacer()
                    }.padding(.vertical, 16)
                    
                    HStack {
                        TextWithState(state.textState)
                            .lineLimit(2)
                        Spacer()
                    }
                }.padding(.bottom, 8)
                 .padding(.horizontal, 12)
                
                
            }
        }.fixedSize(horizontal: false, vertical: true)
    }
}

#Preview {
    NewsListItemView(state: .companion.getMock(), favoriteTapped: nil)
}

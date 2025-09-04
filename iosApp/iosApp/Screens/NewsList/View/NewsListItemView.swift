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
            
            Image(uiImage: ResourceManager.companion.getDrawableResource().uiImage)
            
//            Text("Wef")
//                .font(Font(ResourceManager.companion.getFontResource().toUIFont(size: 12)))
            
            
        }.fixedSize(horizontal: false, vertical: true)
    }
}

#Preview {
    NewsListItemView(state: .companion.getMock(), favoriteTapped: nil)
}

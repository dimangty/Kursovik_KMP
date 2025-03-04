//
//  FavoritesView.swift
//  EPS
//
//  Created by Dmitry on 23/02/2025.
//  Copyright © 2025 EPS. All rights reserved.
//

import SwiftUI


struct FavoritesView: View {
    
    @StateObject private var viewModel = FavoritesViewModel()
    
    var body: some View {
        GeometryReader { geometry in
            VStack(alignment: .leading, spacing: 16) {
                
                LazyVGrid(columns: Array(repeating: GridItem(), count: 2)) {
                    ForEach(0 ..< viewModel.state.favoritesItems.count, id: \.self) { index in
                        let item = viewModel.state.favoritesItems[index]
                        FavoritesItemView(state: item,
                                          favoriteTapped: {viewModel.onEvent(event: .favoriteTapped(item.title))},
                                          widht: getWidht(maxWidht: geometry.size.width,
                                                          columns: 2))
                    }
                }.padding(.horizontal, 16)
            }
        }.background(viewModel.state.backGroundColor.uiColor.toColor())
        
    }
    
    func getWidht(maxWidht: CGFloat, columns: Int) -> CGFloat {
        var result = maxWidht
        result -= 48
        
        if columns > 1 {
            result = result / CGFloat(columns)
        }
        
        return result
    }
    
}

#Preview {
    FavoritesView()
}

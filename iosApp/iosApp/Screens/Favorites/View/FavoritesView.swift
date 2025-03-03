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
                ForEach(0 ..< viewModel.rows.count, id: \.self) { index in
                   let row = self.viewModel.rows[index]
                    HStack(alignment: .top, spacing: 16) {
                        ForEach(0 ..< row.items.count, id: \.self) { index in
                            let item = row.items[index]
                            let column = getWidht(maxWidht: geometry.size.width,
                                                  columns: row.items.count)
                            
                            FavoritesItemView(state: item,
                                              favoriteTapped: {},
                                              widht: column)
                        }
                    }.padding(.horizontal, 16)
                }
            }
        }
        
    }
    
    func getWidht(maxWidht: CGFloat, columns: Int) -> CGFloat {
        var result = maxWidht
        result -= 32
        
        if columns > 1 {
            result = result / CGFloat(columns)
        }
        
        return result
    }

}

#Preview {
    FavoritesView()
}

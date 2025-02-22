//
//  ContainerEmptyView.swift
//  iosApp
//
//  Created by Дмитрий Быков on 22.02.2025.
//

import SwiftUI

struct ContainerEmptyView: View, IModelView {
    mutating func setContainer(container: any IContainer) {
        
    }
    
    var body: some View {
        EmptyView()
    }
}

#Preview {
    ContainerEmptyView()
}

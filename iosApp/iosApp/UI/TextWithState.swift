//
//  TextWithState.swift
//  EPS
//
//  Created by Дмитрий Быков on 22.02.2025.
//

import SwiftUI
import shared

struct TextWithState: View {

    private let state: TextState
    private let maxLines: Int
    
    var body: some View {
        HStack(alignment: .center, spacing: 8) {
            if let iconStart = state.iconStart?.uiImage {
                getImage(with: iconStart)
            }
            if !state.value.isEmpty {
                Text(state.value)
                    .lineLimit(maxLines)
            }
            if let iconEnd = state.iconEnd?.uiImage {
                getImage(with: iconEnd)
            }
        }
        .font(.init(state.fontState.uiFont))
        .foregroundStyle(state.color.uiColor.toColor())
    }

    init(_ state: TextState, maxLines: Int = 99) {
        self.state = state
        self.maxLines = maxLines
    }

    func getImage(with uiImage: UIImage) -> Image? {
        Image(uiImage: uiImage)
            .renderingMode(state.overrideIconsTint ? .template : .original)
    }

}

#Preview {
    TextWithState(PlatformIOSKt.getTextStateMock(text: "Text"))
}

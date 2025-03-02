//
//  FilledButtonWithState.swift
//  iosApp
//
//  Created by Дмитрий Быков on 01.03.2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI
import shared

struct FilledButtonWithState: View {

    private let state: TextState
    private let bgColor: UIColor
    private let borderColor: UIColor
    private let action: VoidBlock

    var body: some View {
        Button(action: action) {
            TextWithState(state)
                .frame(maxWidth: .infinity)
                .frame(height: 44)
                .background(bgColor.toColor())
                .clipShape(shape)
                .contentShape(shape)
                .overlay {
                    shape
                        .stroke(borderColor.toColor(), lineWidth: 1)
                        .padding(.all, 0.5)
                }
        }
        .buttonStyle(.plain)
    }

    var shape: some Shape {
        return RoundedRectangle(cornerRadius: 8)
    }

    init(state: TextState,
         bgColor: UIColor,
         borderColor: UIColor,
         action: @escaping VoidBlock) {
        self.state = state
        self.bgColor = bgColor
        self.borderColor = borderColor
        self.action = action
    }

    init(primaryData: ButtonData.PrimaryButton,
         action: @escaping VoidBlock) {
        self.init(
            state: primaryData.textState,
            bgColor: primaryData.color.uiColor,
            borderColor: .clear,
            action: action
        )
    }

}

#Preview {
    FilledButtonWithState(primaryData: .companion.getMock(), action: {})
}

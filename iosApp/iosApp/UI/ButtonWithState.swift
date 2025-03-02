//
//  ButtonWithState.swift
//  iosApp
//
//  Created by Дмитрий Быков on 01.03.2025.
//  Copyright © 2025 orgName. All rights reserved.
//
import SwiftUI
import shared

struct ButtonWithState: View {

    private let state: ButtonState
    private let action: VoidBlock

    var body: some View {
        content
            .disabled(!state.isEnabled)
    }

    var content: AnyView {
        switch state.data {
        case let imageData as ButtonData.ImageButton:
            return AnyView(
                ImageButtonWithState(imageData: imageData, action: action)
            )
        case let primaryData as ButtonData.PrimaryButton:
            return AnyView(
                FilledButtonWithState(primaryData: primaryData, action: action)
            )
        default:
            return AnyView(
                EmptyView()
            )
        }
    }

    init(_ state: ButtonState, action: @escaping VoidBlock) {
        self.state = state
        self.action = action
    }

}


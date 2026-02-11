//
//  TextFieldWithState.swift
//  iosApp
//
//  Custom TextField configured from shared layer
//

import SwiftUI
import shared

struct TextFieldWithState: View {
    let state: TextFieldState
    let onValueChange: (String) -> Void

    var body: some View {
        VStack(alignment: .leading, spacing: 4) {
            TextField(
                state.placeholder,
                text: Binding(
                    get: { state.value },
                    set: onValueChange
                )
            )
            .keyboardType(keyboardType(for: state.keyboardType))
            .textFieldStyle(RoundedBorderTextFieldStyle())
            .disabled(!state.isEnabled)

            if let error = state.error {
                Text(error)
                    .font(.caption)
                    .foregroundColor(.red)
            }
        }
    }

    private func keyboardType(for type: TextFieldState.KeyboardType) -> UIKeyboardType {
        switch type {
        case .text:
            return .default
        case .number:
            return .numberPad
        case .email:
            return .emailAddress
        case .phone:
            return .phonePad
        default:
            return .default
        }
    }
}

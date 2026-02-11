//
//  PickerWithState.swift
//  iosApp
//
//  Custom Picker configured from shared layer
//

import SwiftUI
import shared

struct PickerWithState: View {
    let state: DropdownFieldState
    let onValueChange: (String) -> Void

    var body: some View {
        VStack(alignment: .leading, spacing: 4) {
            Picker(
                state.placeholder,
                selection: Binding(
                    get: { state.value },
                    set: onValueChange
                )
            ) {
                Text(state.placeholder).tag("")
                ForEach(state.options, id: \.self) { option in
                    Text(option).tag(option)
                }
            }
            .pickerStyle(.menu)
            .disabled(!state.isEnabled)
        }
    }
}

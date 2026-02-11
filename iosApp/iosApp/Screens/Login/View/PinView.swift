//
//  PinView.swift
//  iosApp
//
//  Created on iOS KMP Migration
//

import SwiftUI
import shared

struct PinView: View {

    @StateObject private var viewModel = PinViewModel()
    private let maxPinLength = 6

    var body: some View {
        VStack(spacing: 20) {
            Spacer()

            if !viewModel.state.pinFieldState.placeholder.isEmpty {
                Text(viewModel.state.pinFieldState.placeholder)
                    .font(.system(size: 34, weight: .semibold))
                    .multilineTextAlignment(.center)
                    .padding(.horizontal)
            }

            PinDotsInputView(
                pin: viewModel.state.pinFieldState.value,
                maxPinLength: maxPinLength,
                isError: viewModel.state.errorTextState != nil,
                isEnabled: viewModel.state.pinFieldState.isEnabled,
                onValueChange: { newValue in
                    let filtered = newValue.filter { $0.isNumber }
                    if filtered.count > maxPinLength {
                        viewModel.onEvent(event: .PinChanged(pin: String(filtered.prefix(maxPinLength))))
                    } else {
                        viewModel.onEvent(event: .PinChanged(pin: filtered))
                    }
                }
            )
            .padding(.horizontal)

            if let errorState = viewModel.state.errorTextState {
                TextWithState(errorState)
                    .frame(maxWidth: .infinity, alignment: .center)
                    .padding(.horizontal)
            } else {
                TextWithState(viewModel.state.helperTextState)
                    .frame(maxWidth: .infinity, alignment: .center)
                    .padding(.horizontal)
            }

            if let primaryData = viewModel.state.confirmButtonState.data as? ButtonData.PrimaryButton {
                FilledButtonWithState(
                    primaryData: primaryData,
                    action: {
                        viewModel.onEvent(event: .ConfirmTapped())
                    }
                )
                .disabled(!viewModel.state.confirmButtonState.isEnabled)
                .padding(.horizontal)
            }

            Spacer()
        }
        .overlay {
            if viewModel.state.isLoading {
                LoaderView(isOpaque: false)
            }
        }
        .onAppear {
            viewModel.sendViewAppearedEvent()
        }
    }
}

private struct PinDotsInputView: View {

    let pin: String
    let maxPinLength: Int
    let isError: Bool
    let isEnabled: Bool
    let onValueChange: (String) -> Void

    @FocusState private var isFocused: Bool

    private var inactiveDotColor: SwiftUI.Color { UIColor(hex6: 0xC7C7CC).toColor() }
    private var activeDotColor: SwiftUI.Color { UIColor(hex6: 0x4D8DFF).toColor() }
    private var errorDotColor: SwiftUI.Color { UIColor(hex6: 0xFF3B30).toColor() }

    var body: some View {
        ZStack {
            TextField(
                "",
                text: Binding(
                    get: { pin },
                    set: onValueChange
                )
            )
            .keyboardType(.numberPad)
            .textContentType(.oneTimeCode)
            .disabled(!isEnabled)
            .focused($isFocused)
            .frame(width: 1, height: 1)
            .opacity(0.01)

            HStack(spacing: 14) {
                ForEach(0..<maxPinLength, id: \.self) { index in
                    Circle()
                        .fill(dotColor(for: index))
                        .frame(width: 16, height: 16)
                }
            }
            .contentShape(Rectangle())
            .onTapGesture {
                if isEnabled {
                    isFocused = true
                }
            }
        }
        .onAppear {
            guard isEnabled else { return }
            DispatchQueue.main.asyncAfter(deadline: .now() + 0.2) {
                isFocused = true
            }
        }
    }

    private func dotColor(for index: Int) -> SwiftUI.Color {
        if isError {
            return errorDotColor
        }
        return index < pin.count ? activeDotColor : inactiveDotColor
    }
}

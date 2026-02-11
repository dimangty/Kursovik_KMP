//
//  LoginView.swift
//  iosApp
//
//  Created on iOS KMP Migration
//

import SwiftUI
import shared

struct LoginView: View {

    @StateObject private var viewModel = LoginViewModel()

    var body: some View {
        VStack(spacing: 20) {
            Spacer()

            // Phone field configured from shared layer
            TextFieldWithState(
                state: viewModel.state.phoneFieldState,
                onValueChange: { newValue in
                    let filtered = newValue.filter { $0.isNumber }
                    if filtered.count > 15 {
                        viewModel.onEvent(event: .PhoneChanged(phone: String(filtered.prefix(15))))
                    } else {
                        viewModel.onEvent(event: .PhoneChanged(phone: filtered))
                    }
                }
            )
            .padding(.horizontal)

            // Confirm button configured from shared layer
            if let primaryData = viewModel.state.confirmButtonState.data as? ButtonData.PrimaryButton {
                FilledButtonWithState(
                    primaryData: primaryData,
                    action: {
                        viewModel.onEvent(event: .LoginButtonTapped())
                    }
                )
                .disabled(!viewModel.state.confirmButtonState.isEnabled)
                .padding(.horizontal)
            }

            // Sign Up text button configured from shared layer
            Button(action: {
                viewModel.onEvent(event: .SignUpButtonTapped())
            }) {
                TextWithState(viewModel.state.signUpButtonState)
            }
            .padding()

            // Error text configured from shared layer
            if let errorState = viewModel.state.errorTextState {
                TextWithState(errorState)
                    .padding()
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

extension LoginView {
    static var navigationID: String { "LoginView" }
}

#Preview {
    LoginView()
}

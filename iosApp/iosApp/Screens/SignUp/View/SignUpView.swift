//
//  SignUpView.swift
//  iosApp
//
//  Created on iOS KMP Migration
//

import SwiftUI
import shared

struct SignUpView: View {

    @StateObject private var viewModel = SignUpViewModel()
    @Environment(\.presentationMode) var presentationMode

    var body: some View {
        Form {
            Section("Personal Information") {
                // All fields configured from shared layer
                TextFieldWithState(
                    state: viewModel.state.firstNameField,
                    onValueChange: { viewModel.onEvent(event: .FirstNameChanged(value: $0)) }
                )

                TextFieldWithState(
                    state: viewModel.state.lastNameField,
                    onValueChange: { viewModel.onEvent(event: .LastNameChanged(value: $0)) }
                )

                PickerWithState(
                    state: viewModel.state.genderField,
                    onValueChange: { viewModel.onEvent(event: .GenderChanged(value: $0)) }
                )

                TextFieldWithState(
                    state: viewModel.state.birthDateField,
                    onValueChange: { viewModel.onEvent(event: .BirthDateChanged(value: $0)) }
                )
            }

            Section("Location") {
                PickerWithState(
                    state: viewModel.state.countryField,
                    onValueChange: { viewModel.onEvent(event: .CountryChanged(value: $0)) }
                )

                PickerWithState(
                    state: viewModel.state.cityField,
                    onValueChange: { viewModel.onEvent(event: .CityChanged(value: $0)) }
                )
            }

            Section("Contact") {
                TextFieldWithState(
                    state: viewModel.state.emailField,
                    onValueChange: { viewModel.onEvent(event: .EmailChanged(value: $0)) }
                )

                TextFieldWithState(
                    state: viewModel.state.phoneField,
                    onValueChange: { newValue in
                        let filtered = newValue.filter { $0.isNumber }
                        if filtered.count > 15 {
                            viewModel.onEvent(event: .PhoneChanged(value: String(filtered.prefix(15))))
                        } else {
                            viewModel.onEvent(event: .PhoneChanged(value: filtered))
                        }
                    }
                )
            }

            Section {
                // Button configured from shared layer
                if let primaryData = viewModel.state.createAccountButton.data as? ButtonData.PrimaryButton {
                    FilledButtonWithState(
                        primaryData: primaryData,
                        action: {
                            viewModel.onEvent(event: .CreateAccountTapped())
                        }
                    )
                    .disabled(!viewModel.state.createAccountButton.isEnabled)
                }

                // Error text configured from shared layer
                if let errorState = viewModel.state.errorTextState {
                    TextWithState(errorState)
                }
            }
        }
        .navigationTitle("Sign Up")
        .navigationBarTitleDisplayMode(.inline)
        .toolbar {
            ToolbarItem(placement: .navigationBarLeading) {
                Button("Back") {
                    viewModel.onEvent(event: .BackButtonTapped())
                }
            }
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

extension SignUpView {
    static var navigationID: String { "SignUpView" }
}

#Preview {
    SignUpView()
}

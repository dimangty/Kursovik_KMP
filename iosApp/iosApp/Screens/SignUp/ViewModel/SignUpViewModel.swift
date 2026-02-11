//
//  SignUpViewModel.swift
//  iosApp
//
//  Created on iOS KMP Migration
//

import Combine
import Foundation
import shared

final class SignUpViewModel: BaseViewModel<shared.SignUpViewModel, SignUpState> {

    required override init() {
        super.init()
    }

    func onEvent(event: SignUpEvents) {
        mViewModel?.pushEvent(event: event)
    }

    override func onChangeState(_ state: SignUpState) {
        // State updates are handled automatically by @Published state
    }
}

protocol SignUpModuleInput: AnyObject {
    func configure()
}

extension SignUpViewModel: SignUpModuleInput {
    func configure() {
        // Configure if needed
    }
}

struct SignUpConfigData {
    // Add any configuration data if needed
}

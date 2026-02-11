//
//  LoginViewModel.swift
//  iosApp
//
//  Created on iOS KMP Migration
//

import Combine
import Foundation
import shared

enum LoginViewActions {
    case phoneChanged(String)
    case loginTapped
    case signUpTapped
}

final class LoginViewModel: BaseViewModel<shared.LoginViewModel, LoginState> {

    required override init() {
        super.init()
    }

    func onEvent(event: LoginEvents) {
        mViewModel?.pushEvent(event: event)
    }

    override func onChangeState(_ state: LoginState) {
        // State updates are handled automatically by @Published state
    }
}

protocol LoginModuleInput: AnyObject {
    func configure()
}

extension LoginViewModel: LoginModuleInput {
    func configure() {
        // Configure if needed
    }
}

struct LoginConfigData {
    // Add any configuration data if needed
}

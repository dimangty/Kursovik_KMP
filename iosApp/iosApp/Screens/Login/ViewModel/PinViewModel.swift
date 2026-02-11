//
//  PinViewModel.swift
//  iosApp
//
//  Created on iOS KMP Migration
//

import Foundation
import shared

final class PinViewModel: BaseViewModel<shared.PinViewModel, PinState> {

    required override init() {
        super.init()
    }

    func onEvent(event: PinEvents) {
        mViewModel?.pushEvent(event: event)
    }
}

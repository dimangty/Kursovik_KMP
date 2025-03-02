//
//  ImageButtonWithState.swift
//  iosApp
//
//  Created by Дмитрий Быков on 01.03.2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI
import shared

struct ImageButtonWithState: View {

    private let state: TextState
    private let action: VoidBlock

    var body: some View {
        Button(action: action) {
            TextWithState(state)
        }
    }

    init(state: TextState,
         action: @escaping VoidBlock) {
        self.state = state
        self.action = action
    }

    init(imageData: ButtonData.ImageButton,
         action: @escaping VoidBlock) {
        self.init(
            state: imageData.textState,
            action: action
        )
    }

}

#Preview {
    ImageButtonWithState(imageData: .companion.getMock(),
                         action: {})
}

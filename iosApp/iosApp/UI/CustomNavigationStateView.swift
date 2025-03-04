//
//  CustomNavigationStateView.swift
//  iosApp
//
//  Created by Дмитрий Быков on 04.03.2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI
import SUINavigation
import shared

struct CustomNavigationStateView: View {

    private let titleBar: TitleBarState
    private let backButtonHandler: (() -> Void)?

    private var showBack: Bool {
        return titleBar.isNavigateBackVisible
    }

    init(titleBar: TitleBarState?, backButtonHandler: (() -> Void)? = nil) {
        self.titleBar = titleBar ?? TitleBarState.companion.getMock()
        self.backButtonHandler = backButtonHandler
    }

    var body: some View {
        ZStack {
            HStack {
                Button(action: {
                    backButtonHandler?()
                }) {
                    HStack {
                        Image(uiImage: titleBar.backIcon.uiImage)
                            .renderingMode(.template)
                        Spacer()
                    }
                    .frame(width: 50)
                }
                .isHidden(!showBack)

                Spacer()
            }

            TextWithState(titleBar.title)
                .lineLimit(1)
                .padding(.horizontal, 32)
        }
        .foregroundStyle(titleBar.contentColor.uiColor.toColor())
        .padding(.horizontal, 20)
        .frame(height: 40)
    }

}

#Preview {
    CustomNavigationStateView(titleBar: .companion.getMock())
}

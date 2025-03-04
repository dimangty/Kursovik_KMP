//
//  View+Hidden.swift
//  iosApp
//
//  Created by Дмитрий Быков on 04.03.2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI
import UIKit

extension View {

    @ViewBuilder func isHidden(_ hidden: Bool, remove: Bool = false) -> some View {
        if hidden {
            if !remove {
                self.hidden()
            }
        } else {
            self
        }
    }

}

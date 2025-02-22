//
//  LoaderView.swift
//  EPS
//
//  Created by Михаил Жданов on 04.10.2024.
//

import SwiftUI
import shared

struct LoaderView: View {

    let isOpaque: Bool

    private var loaderColor: UIColor {
        let res = isOpaque ? MR.colors().fs_loader_opaque : MR.colors().fs_loader
        return res.uiColor
    }

    private var loaderBGColor: UIColor {
        let res = isOpaque ? MR.colors().fs_loader_opaque_background : MR.colors().fs_loader_background
        return res.uiColor
    }

    private var bgColor: UIColor {
        let res = isOpaque ? MR.colors().fs_loader_opaque_screen_background : MR.colors().fs_loader_screen_background
        return res.uiColor
    }

    var body: some View {
        ActivityIndicator(color: loaderColor.toColor(), bgColor: loaderBGColor.toColor())
            .frame(width: 64, height: 64)
            .frame(maxWidth: .infinity, maxHeight: .infinity)
            .background(bgColor.toColor())
    }

}

#Preview {
    LoaderView(isOpaque: false)
}

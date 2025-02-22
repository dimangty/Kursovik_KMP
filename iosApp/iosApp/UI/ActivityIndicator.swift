//
//  ActivityIndicator.swift
//  NewsSwiftUI-mvvm
//
//  Created by 1 on 23.02.2020.
//  Copyright © 2020 azharkova. All rights reserved.
//

import SwiftUI


struct ActivityIndicator: UIViewRepresentable {

    let color: Color
    let bgColor: Color
    let lineWidth: CGFloat

    init(color: Color = .white, bgColor: Color = .white.opacity(0.2), lineWidth: CGFloat = 6) {
        self.color = color
        self.bgColor = bgColor
        self.lineWidth = lineWidth
    }

    func makeUIView(context: Context) -> ProgressView {
        return ProgressView(color: .init(color), bgColor: .init(bgColor), lineWidth: lineWidth)
    }

    func updateUIView(_ uiView: ProgressView, context: UIViewRepresentableContext<ActivityIndicator>) {

    }

}

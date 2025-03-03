//
//  UIUtils.swift
//  iosApp
//
//  Created by Дмитрий Быков on 02.03.2025.
//  Copyright © 2025 orgName. All rights reserved.
//
import UIKit

class UIUtils {
    static func getColumn(count: Int, space: CGFloat) -> CGFloat {
        var screen = UIScreen.main.bounds.width
        screen -= 16 * 2
        screen -= space * CGFloat(count - 1)
        
        let column = screen / CGFloat(count)
        return column
    }
}

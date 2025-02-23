//
//  FavoritesAssembly.swift
//  EPS
//
//  Created by Dmitry on 23/02/2025.
//  Copyright © 2025 EPS. All rights reserved.
//

import UIKit

// For module inputs and outputs
typealias FavoritesConfiguration = (FavoritesModuleInput) -> (Void)

final class FavoritesModuleAssembly {

    /// Assembles Module components and returns a target controller
    ///
    /// - Parameter configuration: optional configuration closure called by module owner
    /// - Returns: Assembled module's ViewController
    func assemble(_ configuration: FavoritesConfiguration? = nil) -> UIViewController {
        // let viewModel = FavoritesViewModel()
        let view = FavoritesView()
        let hostingController = FavoritesHostingController(rootView: view)
        // hostingController.viewModel = viewModel
        // configuration?(viewModel)
        return hostingController
    }

}

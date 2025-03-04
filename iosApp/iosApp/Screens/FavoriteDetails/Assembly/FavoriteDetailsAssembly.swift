//
//  FavoriteDetailsAssembly.swift
//  EPS
//
//  Created by Dmitry on 04/03/2025.
//  Copyright © 2025 EPS. All rights reserved.
//

import UIKit

// For module inputs and outputs
typealias FavoriteDetailsConfiguration = (FavoriteDetailsModuleInput) -> (Void)

final class FavoriteDetailsModuleAssembly {

    /// Assembles Module components and returns a target controller
    ///
    /// - Parameter configuration: optional configuration closure called by module owner
    /// - Returns: Assembled module's ViewController
    func assemble(_ configuration: FavoriteDetailsConfiguration? = nil) -> UIViewController {
        // let viewModel = FavoriteDetailsViewModel()
        let view = FavoriteDetailsView(title: "")
        let hostingController = FavoriteDetailsHostingController(rootView: view)
        // hostingController.viewModel = viewModel
        // configuration?(viewModel)
        return hostingController
    }

}

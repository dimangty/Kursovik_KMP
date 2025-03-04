//
//  HomeRootAssembly.swift
//  EPS
//
//  Created by Dmitry on 04/03/2025.
//  Copyright © 2025 EPS. All rights reserved.
//

import UIKit

// For module inputs and outputs
typealias HomeRootConfiguration = (HomeRootModuleInput) -> (Void)

final class HomeRootModuleAssembly {

    /// Assembles Module components and returns a target controller
    ///
    /// - Parameter configuration: optional configuration closure called by module owner
    /// - Returns: Assembled module's ViewController
    func assemble(_ configuration: HomeRootConfiguration? = nil) -> UIViewController {
        // let viewModel = HomeRootViewModel()
        let view = HomeRootView()
        let hostingController = HomeRootHostingController(rootView: view)
        // hostingController.viewModel = viewModel
        // configuration?(viewModel)
        return hostingController
    }

}

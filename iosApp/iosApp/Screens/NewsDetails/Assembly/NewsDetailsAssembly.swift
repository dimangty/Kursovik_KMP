//
//  NewsDetailsAssembly.swift
//  EPS
//
//  Created by Dmitry on 23/02/2025.
//  Copyright © 2025 EPS. All rights reserved.
//

import UIKit

// For module inputs and outputs
typealias NewsDetailsConfiguration = (NewsDetailsModuleInput) -> (Void)

final class NewsDetailsModuleAssembly {

    /// Assembles Module components and returns a target controller
    ///
    /// - Parameter configuration: optional configuration closure called by module owner
    /// - Returns: Assembled module's ViewController
    func assemble(_ configuration: NewsDetailsConfiguration? = nil) -> UIViewController {
        // let viewModel = NewsDetailsViewModel()
        let view = NewsDetailsView()
        let hostingController = NewsDetailsHostingController(rootView: view)
        // hostingController.viewModel = viewModel
        // configuration?(viewModel)
        return hostingController
    }

}

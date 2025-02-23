//
//  NewsListAssembly.swift
//  EPS
//
//  Created by Dmitry on 23/02/2025.
//  Copyright © 2025 EPS. All rights reserved.
//

import UIKit

// For module inputs and outputs
typealias NewsListConfiguration = (NewsListModuleInput) -> (Void)

final class NewsListModuleAssembly {

    /// Assembles Module components and returns a target controller
    ///
    /// - Parameter configuration: optional configuration closure called by module owner
    /// - Returns: Assembled module's ViewController
    func assemble(_ configuration: NewsListConfiguration? = nil) -> UIViewController {
        // let viewModel = NewsListViewModel()
        let view = NewsListView()
        let hostingController = NewsListHostingController(rootView: view)
        // hostingController.viewModel = viewModel
        // configuration?(viewModel)
        return hostingController
    }

}

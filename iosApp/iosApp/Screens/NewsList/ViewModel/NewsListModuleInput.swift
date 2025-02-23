//
//  NewsListModuleInput.swift
//  EPS
//
//  Created by Dmitry on 23/02/2025.
//  Copyright © 2025 EPS. All rights reserved.
//

/// Adapter struct for NewsList initial configuration through NewsListModuleInput
struct NewsListConfigData {

}

/// Protocol with public methods to configure NewsList from its parent module (usually implemented by this module's ViewModel)
protocol NewsListModuleInput: AnyObject {

	/// public configuration method for parent modules (add configurating parameters)
    func configure(data: NewsListConfigData)

}

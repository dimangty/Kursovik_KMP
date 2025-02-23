//
//  NewsDetailsModuleInput.swift
//  EPS
//
//  Created by Dmitry on 23/02/2025.
//  Copyright © 2025 EPS. All rights reserved.
//

/// Adapter struct for NewsDetails initial configuration through NewsDetailsModuleInput
struct NewsDetailsConfigData {

}

/// Protocol with public methods to configure NewsDetails from its parent module (usually implemented by this module's ViewModel)
protocol NewsDetailsModuleInput: AnyObject {

	/// public configuration method for parent modules (add configurating parameters)
    func configure(data: NewsDetailsConfigData)

}

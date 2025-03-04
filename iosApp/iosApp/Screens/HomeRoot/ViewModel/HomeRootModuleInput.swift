//
//  HomeRootModuleInput.swift
//  EPS
//
//  Created by Dmitry on 04/03/2025.
//  Copyright © 2025 EPS. All rights reserved.
//

/// Adapter struct for HomeRoot initial configuration through HomeRootModuleInput
struct HomeRootConfigData {

}

/// Protocol with public methods to configure HomeRoot from its parent module (usually implemented by this module's ViewModel)
protocol HomeRootModuleInput: AnyObject {

	/// public configuration method for parent modules (add configurating parameters)
    func configure(data: HomeRootConfigData)

}

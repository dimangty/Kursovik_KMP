//
//  FavoriteDetailsModuleInput.swift
//  EPS
//
//  Created by Dmitry on 04/03/2025.
//  Copyright © 2025 EPS. All rights reserved.
//

/// Adapter struct for FavoriteDetails initial configuration through FavoriteDetailsModuleInput
struct FavoriteDetailsConfigData {

}

/// Protocol with public methods to configure FavoriteDetails from its parent module (usually implemented by this module's ViewModel)
protocol FavoriteDetailsModuleInput: AnyObject {

	/// public configuration method for parent modules (add configurating parameters)
    func configure(data: FavoriteDetailsConfigData)

}

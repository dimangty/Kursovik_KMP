//
//  FavoritesModuleInput.swift
//  EPS
//
//  Created by Dmitry on 23/02/2025.
//  Copyright © 2025 EPS. All rights reserved.
//

/// Adapter struct for Favorites initial configuration through FavoritesModuleInput
struct FavoritesConfigData {

}

/// Protocol with public methods to configure Favorites from its parent module (usually implemented by this module's ViewModel)
protocol FavoritesModuleInput: AnyObject {

	/// public configuration method for parent modules (add configurating parameters)
    func configure(data: FavoritesConfigData)

}

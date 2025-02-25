//
//  Configurator.swift
//  TestingTask
//
//  Created by DBykov on 19.07.2022.
//

import Foundation

class Configurator {

    static let shared = Configurator()

    let serviceLocator = ServiceLocator()

    func setup() {
        registerServices()
    }

    private func registerServices() {
        serviceLocator.addService(service: ContentService())
    }

}

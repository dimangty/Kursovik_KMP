//
//  Injected.swift
//  TestingTask
//
//  Created by DBykov on 19.07.2022.
//

import Foundation

@propertyWrapper
struct Injected<Service: AnyObject> {

    private var service: Service?
    private weak var serviceLocator = Configurator.shared.serviceLocator

    var wrappedValue: Service? {
        mutating get {
            if serviceLocator?.isEmpty() ?? false {
                Configurator.shared.setup()
            }
            if service == nil {
                service = serviceLocator?.getService(type: Service.self)
            }
            return service
        }
        mutating set {
            service = newValue
        }
    }

    var projectedValue: Injected<Service> {
        mutating set {self = newValue}
        get {return self}
    }

}

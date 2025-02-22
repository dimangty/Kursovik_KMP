//
//  KoinHelper.swift
//  iosApp
//
//  Created by Дмитрий Быков on 22.02.2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import shared

typealias KoinApplication = Koin_coreKoinApplication


extension KoinApplication {
    static let shared = Di_iosKt.doInitKoin()

  @discardableResult
  static func start() -> KoinApplication {
    shared
  }
}

private class KoinQualifier: Koin_coreQualifier {
    init(value: String) {
        self.value = value
    }
    var value: String
}

func inject<T : AnyObject>() -> T {
    return KoinApplication.shared.koin.get(objCClass: T.self) as! T
}

func inject<T : AnyObject>(param: Any?) -> T {
    return KoinApplication.shared.koin.get(objCClass: T.self, parameter: param) as! T
}

func inject<T : AnyObject>(parameters: [Any?]?) -> T {
    if let ktClass = shared.Di_iosKt.getOriginalKotlinClass(objCClass: T.self) {
        return KoinApplication.shared.koin.get(
            clazz: ktClass,
            qualifier: nil,
            parameters: {
            .init(_values: .init(array: parameters ?? []), useIndexedValues: true)
        }) as! T
    }
    fatalError("Cant resolve ViewModel \(T.self)")
}

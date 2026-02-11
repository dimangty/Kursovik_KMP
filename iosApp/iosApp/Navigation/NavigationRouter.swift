//
//  NavigationRouter.swift
//  iosApp
//
//  Navigation coordinator using NavigationStack (iOS 16+)
//

import SwiftUI

enum NavigationDestination: Hashable {
    case login
    case signUp
    case main
    case newsDetails(title: String)
    case favoriteDetails(title: String)
}

@MainActor
class NavigationRouter: ObservableObject {
    @Published var path = NavigationPath()

    func navigate(to destination: NavigationDestination) {
        path.append(destination)
    }

    func navigateBack() {
        path.removeLast()
    }

    func navigateToRoot() {
        path.removeLast(path.count)
    }

    func pop(to destination: NavigationDestination) {
        // Find the destination in the path and pop to it
        // This is a simplified version - you may need to enhance this
        while !path.isEmpty {
            path.removeLast()
        }
        path.append(destination)
    }
}

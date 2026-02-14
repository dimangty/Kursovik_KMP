//
//  NavigationService.swift
//  iosApp
//
//  Centralized navigation service for managing NavigationPath
//

import SwiftUI
import shared

enum NavigationDestination: Hashable {
    case login
    case signUp
    case pin
    case main
    case newsDetails(title: String)
    case favoriteDetails(title: String)
    case recipesDetails(recipeId: String)
}

class NavigationService: ObservableObject {
    @Published var path = NavigationPath()

    // MARK: - Navigation Methods

    func navigate(to destination: NavigationDestination) {
        path.append(destination)
    }

    func navigateBack() {
        guard !path.isEmpty else { return }
        path.removeLast()
    }

    func navigateToRoot() {
        path.removeLast(path.count)
    }

    func replace(with destination: NavigationDestination) {
        path.removeLast(path.count)
        path.append(destination)
    }

    // MARK: - Kotlin NavigationAction Mapping

    func handle(_ action: NavigationAction) {
        switch action {
        case is NavigationAction.NavigateToLogin:
            navigateToRoot()

        case is NavigationAction.NavigateToSignUp:
            navigate(to: .signUp)

        case is NavigationAction.NavigateToPin:
            navigate(to: .pin)

        case is NavigationAction.NavigateToMain:
            replace(with: .main)

        case let navAction as NavigationAction.NavigateToNewsDetails:
            navigate(to: .newsDetails(title: navAction.title))

        case let navAction as NavigationAction.NavigateToFavoritesDetails:
            navigate(to: .favoriteDetails(title: navAction.title))

        case let navAction as NavigationAction.NavigateToRecipesDetails:
            navigate(to: .recipesDetails(recipeId: navAction.recipeId))

        case is NavigationAction.NavigateBack:
            navigateBack()

        default:
            print("⚠️ Unhandled NavigationAction: \(action)")
        }
    }
}

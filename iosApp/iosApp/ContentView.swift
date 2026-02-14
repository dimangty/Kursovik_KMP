import SwiftUI
import shared

struct ContentView: View {
	let greet = Greeting().greet()
    @StateObject private var navigationService: NavigationService = {
        Configurator.shared.setup()
        return Configurator.shared.serviceLocator.getService(type: NavigationService.self)!
    }()

	var body: some View {
        NavigationStack(path: $navigationService.path) {
            LoginView()
                .navigationDestination(for: NavigationDestination.self) { destination in
                    switch destination {
                    case .login:
                        LoginView()
                    case .signUp:
                        SignUpView()
                    case .pin:
                        PinView()
                    case .main:
                        HomeRootView()
                    case .newsDetails(let title):
                        NewsDetailsView(title: title)
                    case .favoriteDetails(let title):
                        FavoriteDetailsView(title: title)
                    case .recipesDetails(let recipeId):
                        RecipesDetailsView(recipeId: recipeId)
                    }
                }
        }
	}
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}

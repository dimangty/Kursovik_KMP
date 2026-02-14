import shared

enum FridgeTabViewActions {
    case productTapped(String)
    case recipeTapped(String)
    case recommendTapped
    case retryTapped
}

final class FridgeTabViewModel: BaseViewModel<shared.FridgeViewModel, FridgeState> {
    required override init() {
        super.init()
    }

    func onEvent(_ event: FridgeTabViewActions) {
        switch event {
        case .productTapped(let productId):
            mViewModel?.pushEvent(event: .OnProductClicked(productId: productId))
        case .recipeTapped(let recipeId):
            mViewModel?.pushEvent(event: .OnRecipeClicked(recipeId: recipeId))
        case .recommendTapped:
            mViewModel?.pushEvent(event: .OnRecommendRecipesClicked())
        case .retryTapped:
            mViewModel?.pushEvent(event: .OnRetryClicked())
        }
    }
}

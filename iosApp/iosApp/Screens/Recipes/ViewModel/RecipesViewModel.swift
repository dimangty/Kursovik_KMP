import Foundation
import shared

enum RecipesViewActions {
    case retryTapped
    case recipeTapped(String)
}

final class RecipesViewModel: BaseViewModel<shared.RecipesListViewModel, RecipesListState> {
    required override init() {
        super.init()
    }

    func onEvent(_ event: RecipesViewActions) {
        switch event {
        case .retryTapped:
            mViewModel?.pushEvent(event: .OnRetryClicked())
        case .recipeTapped(let recipeId):
            mViewModel?.pushEvent(event: .OnItemClicked(recipeId: recipeId))
        }
    }
}

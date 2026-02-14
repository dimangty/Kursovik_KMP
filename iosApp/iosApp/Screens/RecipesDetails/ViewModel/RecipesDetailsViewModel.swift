import Foundation
import shared

enum RecipesDetailsViewActions {
    case retryTapped
}

final class RecipesDetailsViewModel: BaseViewModel<shared.RecipesDetailsViewModel, RecipesDetailsState> {
    init(recipeId: String) {
        super.init(param: recipeId)
    }

    func onEvent(_ event: RecipesDetailsViewActions) {
        switch event {
        case .retryTapped:
            mViewModel?.pushEvent(event: .OnRetryClicked())
        }
    }
}

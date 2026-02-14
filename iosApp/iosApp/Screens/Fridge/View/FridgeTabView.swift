import SwiftUI
import shared

struct FridgeTabView: View {
    @StateObject private var viewModel = FridgeTabViewModel()

    var body: some View {
        ZStack {
            Rectangle()
                .fill(viewModel.state.backGroundColor.uiColor.toColor())
                .edgesIgnoringSafeArea(.all)

            VStack {
                CustomNavigationStateView(
                    titleBar: viewModel.state.titleBarState,
                    trailingView: AnyView(
                        NavigationLink(destination: ProfileTabView()) {
                            Image(systemName: "person.crop.circle")
                                .font(.title3)
                        }
                    )
                )

                ScrollView {
                    VStack(alignment: .leading, spacing: 12) {
                        TextWithState(viewModel.state.productsTitleState)

                        ForEach(0 ..< viewModel.state.productsItems.count, id: \.self) { index in
                            let item = viewModel.state.productsItems[index]
                            HStack(spacing: 10) {
                                Image(systemName: item.isSelected ? "checkmark.circle.fill" : "circle")
                                    .foregroundColor(item.isSelected ? .blue : .gray)
                                Text(item.name)
                            }
                            .onTapGesture {
                                viewModel.onEvent(.productTapped(item.id))
                            }
                        }

                        Button(action: {
                            viewModel.onEvent(.recommendTapped)
                        }) {
                            Text(viewModel.state.recommendButtonTitle)
                                .frame(maxWidth: .infinity)
                                .padding(.vertical, 12)
                        }
                        .buttonStyle(.borderedProminent)
                        .disabled(!viewModel.state.isRecommendButtonEnabled)
                        .padding(.top, 4)

                        if viewModel.state.hasRecommendationsRequest {
                            TextWithState(viewModel.state.recommendationsTitleState)
                                .padding(.top, 8)

                            if viewModel.state.recommendationsItems.isEmpty {
                                TextWithState(viewModel.state.emptyRecommendationsState)
                                Button("Повторить") {
                                    viewModel.onEvent(.retryTapped)
                                }
                            } else {
                                ForEach(0 ..< viewModel.state.recommendationsItems.count, id: \.self) { index in
                                    let item = viewModel.state.recommendationsItems[index]
                                    FridgeRecipeItemView(state: item)
                                        .onTapGesture {
                                            viewModel.onEvent(.recipeTapped(item.id))
                                        }
                                }
                            }
                        }
                    }
                    .frame(maxWidth: .infinity, alignment: .leading)
                    .padding(.horizontal, 16)
                    .padding(.vertical, 12)
                }
            }
            .navigationBarHidden(true)
        }
        .onAppear {
            viewModel.sendViewAppearedEvent()
        }
    }
}

private struct FridgeRecipeItemView: View {
    let state: FridgeRecommendedRecipeUiState

    var body: some View {
        ZStack(alignment: .topLeading) {
            Rectangle()
                .fill(state.cellBackground.uiColor.toColor())
                .cornerRadius(22)

            VStack(alignment: .leading, spacing: 8) {
                if let url = URL(string: state.imageUrl), !state.imageUrl.isEmpty {
                    AsyncImage(url: url) { image in
                        image
                            .resizable()
                            .scaledToFill()
                    } placeholder: {
                        Rectangle()
                            .fill(Color.gray.opacity(0.15))
                    }
                    .frame(maxWidth: .infinity)
                    .frame(height: 140)
                    .clipShape(RoundedRectangle(cornerRadius: 12))
                }

                TextWithState(state.titleState)
                TextWithState(state.descriptionState)
                    .lineLimit(2)
                TextWithState(state.durationState)
                TextWithState(state.matchedState)
            }
            .frame(maxWidth: .infinity, alignment: .leading)
            .padding(16)
        }
        .frame(maxWidth: .infinity, alignment: .leading)
        .fixedSize(horizontal: false, vertical: true)
    }
}

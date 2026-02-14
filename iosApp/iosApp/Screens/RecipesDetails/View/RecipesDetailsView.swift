import SwiftUI
import shared

struct RecipesDetailsView: View {
    @StateObject private var viewModel: RecipesDetailsViewModel

    init(recipeId: String) {
        _viewModel = StateObject(wrappedValue: RecipesDetailsViewModel(recipeId: recipeId))
    }

    var body: some View {
        VStack(spacing: 0) {
            CustomNavigationStateView(titleBar: viewModel.state.titleBarState) {
                viewModel.navigationService?.navigateBack()
            }

            ScrollView(showsIndicators: false) {
                VStack(alignment: .leading, spacing: 12) {
                    Text(viewModel.state.titleState.value)
                        .font(.system(size: 28, weight: .semibold))
                        .foregroundStyle(Color(red: 0.12, green: 0.12, blue: 0.12))

                    HStack(spacing: 6) {
                        Image(systemName: "clock.fill")
                            .font(.system(size: 13, weight: .medium))
                            .foregroundStyle(Color(red: 0.18, green: 0.56, blue: 0.36))
                        Text(viewModel.state.durationState.value.replacingOccurrences(of: "⏱", with: "").trimmingCharacters(in: .whitespaces).replacingOccurrences(of: "мин", with: "минут"))
                            .font(.system(size: 13, weight: .medium))
                            .foregroundStyle(Color(red: 0.18, green: 0.56, blue: 0.36))
                    }

                    AsyncImage(url: URL(string: viewModel.state.imageUrl ?? "")) { image in
                        image
                            .resizable()
                            .scaledToFill()
                    } placeholder: {
                        Rectangle()
                            .fill(Color.gray.opacity(0.15))
                    }
                    .frame(height: 188)
                    .clipShape(RoundedRectangle(cornerRadius: 10))

                    VStack(alignment: .leading, spacing: 10) {
                        Text(viewModel.state.ingredientsTitleState.value)
                            .font(.system(size: 19, weight: .semibold))
                            .foregroundStyle(Color(red: 0.18, green: 0.56, blue: 0.36))

                        if viewModel.state.ingredientsItems.isEmpty {
                            ForEach(fallbackIngredients.indices, id: \.self) { index in
                                let item = fallbackIngredients[index]
                                ingredientRow(name: item.name, amount: item.amount)
                            }
                        } else {
                            ForEach(0 ..< viewModel.state.ingredientsItems.count, id: \.self) { index in
                                let item = viewModel.state.ingredientsItems[index]
                                ingredientRow(name: item.name, amount: item.amount)
                            }
                        }
                    }
                    .padding(14)
                    .frame(maxWidth: .infinity, alignment: .leading)
                    .background(Color(red: 0.97, green: 0.97, blue: 0.97))
                    .overlay {
                        RoundedRectangle(cornerRadius: 10)
                            .stroke(Color.black.opacity(0.08), lineWidth: 1)
                    }
                    .clipShape(RoundedRectangle(cornerRadius: 10))

                    Text("Шаги приготовления")
                        .font(.system(size: 22, weight: .semibold))
                        .foregroundStyle(Color(red: 0.12, green: 0.12, blue: 0.12))

                    if viewModel.state.stepsItems.isEmpty {
                        ForEach(fallbackSteps.indices, id: \.self) { index in
                            let item = fallbackSteps[index]
                            stepCard(number: item.number, text: item.text, duration: item.duration)
                        }
                    } else {
                        ForEach(0 ..< viewModel.state.stepsItems.count, id: \.self) { index in
                            let item = viewModel.state.stepsItems[index]
                            stepCard(number: Int(item.number), text: item.text, duration: item.duration)
                        }
                    }
                }
                .padding(.horizontal, 16)
                .padding(.top, 8)
                .padding(.bottom, 12)
            }

            if viewModel.state.titleState.value.isEmpty {
                Button("Повторить") {
                    viewModel.onEvent(.retryTapped)
                }
                .padding(.bottom, 12)
            } else {
                Button(action: {}) {
                    Text(viewModel.state.startCookingButtonTitle)
                        .font(.system(size: 17, weight: .semibold))
                        .foregroundStyle(.white)
                        .frame(maxWidth: .infinity)
                        .frame(height: 50)
                        .background(Color(red: 0.18, green: 0.56, blue: 0.36))
                        .clipShape(Capsule())
                }
                .padding(.horizontal, 20)
                .padding(.bottom, 14)
            }
        }
        .background(Color(red: 0.95, green: 0.95, blue: 0.95).ignoresSafeArea())
        .navigationBarHidden(true)
        .onAppear {
            viewModel.sendViewAppearedEvent()
        }
    }

    @ViewBuilder
    private func ingredientRow(name: String, amount: String) -> some View {
        HStack(alignment: .top, spacing: 8) {
            Text("• \(name)")
                .font(.system(size: 15, weight: .regular))
                .foregroundStyle(Color(red: 0.17, green: 0.17, blue: 0.17))
                .frame(maxWidth: .infinity, alignment: .leading)

            Text(amount)
                .font(.system(size: 13, weight: .regular))
                .foregroundStyle(Color(red: 0.43, green: 0.43, blue: 0.43))
                .multilineTextAlignment(.trailing)
        }
    }

    @ViewBuilder
    private func stepCard(number: Int, text: String, duration: String) -> some View {
        HStack(alignment: .top, spacing: 10) {
            ZStack {
                Circle()
                    .fill(Color(red: 0.93, green: 0.93, blue: 0.93))
                    .frame(width: 28, height: 28)
                Text("\(number)")
                    .font(.system(size: 16, weight: .semibold))
                    .foregroundStyle(Color(red: 0.48, green: 0.48, blue: 0.48))
            }

            VStack(alignment: .leading, spacing: 8) {
                Text(text)
                    .font(.system(size: 14, weight: .regular))
                    .foregroundStyle(Color(red: 0.35, green: 0.35, blue: 0.35))
                    .frame(maxWidth: .infinity, alignment: .leading)

                if !duration.isEmpty {
                    Text(duration)
                        .font(.system(size: 12, weight: .regular))
                        .foregroundStyle(Color(red: 0.56, green: 0.56, blue: 0.56))
                }
            }

            Image(systemName: "square")
                .font(.system(size: 20, weight: .regular))
                .foregroundStyle(Color(red: 0.74, green: 0.74, blue: 0.74))
        }
        .padding(12)
        .background(Color(red: 0.96, green: 0.96, blue: 0.96))
        .overlay {
            RoundedRectangle(cornerRadius: 10)
                .stroke(Color.black.opacity(0.07), lineWidth: 1)
        }
        .clipShape(RoundedRectangle(cornerRadius: 10))
    }

    private var fallbackIngredients: [FallbackIngredient] {
        viewModel.state.ingredientsState.value
            .components(separatedBy: "\n")
            .map { $0.replacingOccurrences(of: "•", with: "").trimmingCharacters(in: .whitespaces) }
            .filter { !$0.isEmpty }
            .map { FallbackIngredient(name: $0, amount: "по вкусу") }
    }

    private var fallbackSteps: [FallbackStep] {
        viewModel.state.stepsState.value
            .components(separatedBy: "\n")
            .map { line -> String in
                if let dotIndex = line.firstIndex(of: ".") {
                    let nextIndex = line.index(after: dotIndex)
                    return String(line[nextIndex...]).trimmingCharacters(in: .whitespaces)
                }
                return line
            }
            .filter { !$0.isEmpty }
            .enumerated()
            .map { FallbackStep(number: $0.offset + 1, text: $0.element, duration: "") }
    }
}

private struct FallbackIngredient {
    let name: String
    let amount: String
}

private struct FallbackStep {
    let number: Int
    let text: String
    let duration: String
}

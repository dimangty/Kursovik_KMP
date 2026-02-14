package com.example.kursovikkmp.feature.fridge

import com.example.kursovikkmp.feature.fridge.model.FridgeProduct
import com.example.kursovikkmp.feature.fridge.model.FridgeRecommendedRecipe
import com.example.kursovikkmp.feature.recipes.RecipesService
import kotlinx.coroutines.delay

class FridgeMockNetworkService(
    private val recipesService: RecipesService
) {

    suspend fun getFridgeProducts(): List<FridgeProduct> {
        delay(350)
        val recipes = recipesService.getRecipes()
        return recipes
            .flatMap { recipe -> recipe.ingredients }
            .map { ingredient -> ingredient.name.trim() }
            .filter { it.isNotBlank() }
            .distinctBy { it.lowercase() }
            .sortedBy { it.lowercase() }
            .take(20)
            .mapIndexed { index, name ->
                FridgeProduct(id = (index + 1).toString(), name = name)
            }
    }

    suspend fun getRecommendedRecipes(selectedProducts: List<String>): List<FridgeRecommendedRecipe> {
        delay(600)
        if (selectedProducts.isEmpty()) return emptyList()

        val selected = selectedProducts.map { it.lowercase() }.toSet()

        return recipesService.getRecipes()
            .map { recipe ->
                val matchedIngredients = recipe.ingredients
                    .map { it.name }
                    .filter { ingredient ->
                        selected.any { picked -> ingredient.lowercase().contains(picked) || picked.contains(ingredient.lowercase()) }
                    }

                FridgeRecommendedRecipe(
                    id = recipe.id,
                    title = recipe.title,
                    description = recipe.description,
                    durationMinutes = recipe.durationMinutes,
                    imageUrl = recipe.imageUrl,
                    matchedIngredients = matchedIngredients
                )
            }
            .filter { it.matchedIngredients.isNotEmpty() }
            .sortedWith(
                compareByDescending<FridgeRecommendedRecipe> { it.matchedIngredients.size }
                    .thenBy { it.durationMinutes }
            )
            .take(10)
    }
}

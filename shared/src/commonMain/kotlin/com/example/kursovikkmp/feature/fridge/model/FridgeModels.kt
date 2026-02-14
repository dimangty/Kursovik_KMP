package com.example.kursovikkmp.feature.fridge.model

data class FridgeProduct(
    val id: String,
    val name: String
)

data class FridgeRecommendedRecipe(
    val id: String,
    val title: String,
    val description: String,
    val durationMinutes: Int,
    val imageUrl: String,
    val matchedIngredients: List<String>
)

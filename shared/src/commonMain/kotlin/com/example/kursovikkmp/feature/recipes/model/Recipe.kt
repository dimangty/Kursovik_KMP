package com.example.kursovikkmp.feature.recipes.model

data class Recipe(
    val id: String,
    val title: String,
    val description: String,
    val durationMinutes: Int,
    val imageUrl: String,
    val ingredients: List<RecipeIngredient>,
    val steps: List<RecipeStep>
)

data class RecipeIngredient(
    val name: String,
    val amount: String
)

data class RecipeStep(
    val text: String,
    val durationMinutes: Int? = null
)

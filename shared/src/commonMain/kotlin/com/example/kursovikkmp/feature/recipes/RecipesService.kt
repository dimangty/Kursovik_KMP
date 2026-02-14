package com.example.kursovikkmp.feature.recipes

import com.example.kursovikkmp.feature.recipes.model.Recipe
import com.example.kursovikkmp.feature.recipes.model.RecipeIngredient
import com.example.kursovikkmp.feature.recipes.model.RecipeStep
import kotlinx.coroutines.delay

interface RecipesService {
    suspend fun getRecipes(): List<Recipe>
    suspend fun getRecipeById(id: String): Recipe?
}

class RecipesServiceImpl : RecipesService {
    private val mockRecipes = listOf(
        Recipe(
            id = "1",
            title = "Паста Карбонара",
            description = "Классическая паста с беконом и сыром.",
            durationMinutes = 25,
            imageUrl = "https://images.unsplash.com/photo-1621996346565-e3dbc353d2e5",
            ingredients = listOf(
                RecipeIngredient("Спагетти", "200 г"),
                RecipeIngredient("Бекон", "120 г"),
                RecipeIngredient("Пармезан", "50 г"),
                RecipeIngredient("Яйца", "2 шт"),
                RecipeIngredient("Черный перец", "по вкусу")
            ),
            steps = listOf(
                RecipeStep("Отвари спагетти до состояния al dente.", 6),
                RecipeStep("Обжарь бекон до золотистой корочки.", 5),
                RecipeStep("Смешай яйца с тертым пармезаном и перцем.", 4),
                RecipeStep("Соедини пасту с беконом и сними с огня.", 5),
                RecipeStep("Добавь яично-сырную смесь и быстро перемешай.", 5)
            )
        ),
        Recipe(
            id = "2",
            title = "Том Ям",
            description = "Острый тайский суп с морепродуктами.",
            durationMinutes = 35,
            imageUrl = "https://images.unsplash.com/photo-1547592166-23ac45744acd",
            ingredients = listOf(
                RecipeIngredient("Креветки", "300 г"),
                RecipeIngredient("Шампиньоны", "150 г"),
                RecipeIngredient("Лемонграсс", "2 стебля"),
                RecipeIngredient("Кокосовое молоко", "250 мл"),
                RecipeIngredient("Лайм", "1 шт")
            ),
            steps = listOf(
                RecipeStep("Подготовь бульон с лемонграссом и листьями лайма.", 10),
                RecipeStep("Добавь грибы и провари 5 минут.", 5),
                RecipeStep("Добавь креветки и пасту том ям.", 8),
                RecipeStep("Влей кокосовое молоко и доведи до вкуса лаймом.", 12)
            )
        ),
        Recipe(
            id = "3",
            title = "Сырники",
            description = "Нежные творожные сырники на завтрак.",
            durationMinutes = 20,
            imageUrl = "https://images.unsplash.com/photo-1603360946369-dc9bb6258143",
            ingredients = listOf(
                RecipeIngredient("Творог", "400 г"),
                RecipeIngredient("Яйцо", "1 шт"),
                RecipeIngredient("Мука", "3 ст. ложки"),
                RecipeIngredient("Сахар", "1 ст. ложка"),
                RecipeIngredient("Ваниль", "щепотка")
            ),
            steps = listOf(
                RecipeStep("Смешай творог, яйцо, сахар и немного муки.", 6),
                RecipeStep("Сформируй небольшие сырники.", 4),
                RecipeStep("Обжарь с двух сторон до румяной корочки.", 10)
            )
        ),
        Recipe(
            id = "4",
            title = "Салат Цезарь",
            description = "Салат с курицей, романо и соусом цезарь.",
            durationMinutes = 18,
            imageUrl = "https://images.unsplash.com/photo-1546793665-c74683f339c1",
            ingredients = listOf(
                RecipeIngredient("Курица", "200 г"),
                RecipeIngredient("Романо", "1 пучок"),
                RecipeIngredient("Пармезан", "40 г"),
                RecipeIngredient("Сухарики", "60 г"),
                RecipeIngredient("Соус цезарь", "80 мл")
            ),
            steps = listOf(
                RecipeStep("Обжарь курицу со специями.", 7),
                RecipeStep("Собери салат с листьями романо и сухариками.", 5),
                RecipeStep("Добавь соус и посыпь пармезаном.", 6)
            )
        ),
        Recipe(
            id = "5",
            title = "Лосось в соусе терияки",
            description = "Лосось в соусе терияки",
            durationMinutes = 45,
            imageUrl = "https://images.unsplash.com/photo-1467003909585-2f8a72700288",
            ingredients = listOf(
                RecipeIngredient("Соевый соус", "8 ст. ложек"),
                RecipeIngredient("Вода", "8 ст. ложек"),
                RecipeIngredient("Мёд", "3 ст. ложки"),
                RecipeIngredient("Коричневый сахар", "2 ст. ложки"),
                RecipeIngredient("Чеснок", "2 зубчика"),
                RecipeIngredient("Тёртый свежий имбирь", "1 ст. ложка"),
                RecipeIngredient("Лимонный сок", "1,5 ст. ложки"),
                RecipeIngredient("Кукурузный крахмал", "2 ст. ложки"),
                RecipeIngredient("Растительное масло", "2 ч. ложки"),
                RecipeIngredient("Филе лосося или сёмги", "600 г"),
                RecipeIngredient("Кунжут", "по вкусу")
            ),
            steps = listOf(
                RecipeStep("В небольшой кастрюле соедините соевый соус, воду, мёд, сахар, измельчённый чеснок, имбирь и лимонный сок.", 6),
                RecipeStep("Поставьте на средний огонь и, часто помешивая, доведите до лёгкого кипения.", 7),
                RecipeStep("Смешайте оставшуюся воду с крахмалом. Добавьте в кастрюлю и перемешайте.", 6),
                RecipeStep("Готовьте, непрерывно помешивая венчиком, 1 минуту. Снимите с огня.", 1),
                RecipeStep("Смажьте форму маслом и выложите на неё рыбу. Полейте её соусом.", 6),
                RecipeStep("Поставьте в разогретую до 200°C духовку примерно на 15 минут.", 15),
                RecipeStep("Перед подачей полейте соусом из формы и посыпьте кунжутом.", 4)
            )
        ),
        Recipe(
            id = "6",
            title = "Поке боул с сыром тофу",
            description = "Поке боул с сыром тофу",
            durationMinutes = 30,
            imageUrl = "https://images.unsplash.com/photo-1512621776951-a57141f2eefd",
            ingredients = listOf(
                RecipeIngredient("Тофу", "200 г"),
                RecipeIngredient("Рис", "150 г"),
                RecipeIngredient("Авокадо", "1 шт"),
                RecipeIngredient("Огурец", "1 шт"),
                RecipeIngredient("Соевый соус", "2 ст. ложки")
            ),
            steps = listOf(
                RecipeStep("Отвари рис и остуди.", 12),
                RecipeStep("Обжарь тофу до хрустящей корочки.", 8),
                RecipeStep("Собери боул из риса, овощей и тофу.", 10)
            )
        ),
        Recipe(
            id = "7",
            title = "Тосты с голубикой и бананом",
            description = "Тосты с голубикой и бананом",
            durationMinutes = 45,
            imageUrl = "https://images.unsplash.com/photo-1484723091739-30a097e8f929",
            ingredients = listOf(
                RecipeIngredient("Хлеб", "4 ломтика"),
                RecipeIngredient("Банан", "1 шт"),
                RecipeIngredient("Голубика", "80 г"),
                RecipeIngredient("Мед", "2 ч. ложки"),
                RecipeIngredient("Творожный сыр", "100 г")
            ),
            steps = listOf(
                RecipeStep("Подсуши тосты.", 10),
                RecipeStep("Намажь творожный сыр.", 8),
                RecipeStep("Добавь банан, голубику и немного меда.", 27)
            )
        ),
        Recipe(
            id = "8",
            title = "Паста с морепродуктами",
            description = "Паста с морепродуктами",
            durationMinutes = 25,
            imageUrl = "https://images.unsplash.com/photo-1473093295043-cdd812d0e601",
            ingredients = listOf(
                RecipeIngredient("Паста", "220 г"),
                RecipeIngredient("Морепродукты", "250 г"),
                RecipeIngredient("Чеснок", "2 зубчика"),
                RecipeIngredient("Сливки", "180 мл"),
                RecipeIngredient("Петрушка", "по вкусу")
            ),
            steps = listOf(
                RecipeStep("Отвари пасту.", 8),
                RecipeStep("Обжарь чеснок и морепродукты.", 7),
                RecipeStep("Добавь сливки и пасту, доведи до готовности.", 10)
            )
        ),
        Recipe(
            id = "9",
            title = "Пицца Маргарита домашняя",
            description = "Пицца Маргарита домашняя",
            durationMinutes = 55,
            imageUrl = "https://images.unsplash.com/photo-1513104890138-7c749659a591",
            ingredients = listOf(
                RecipeIngredient("Тесто", "1 основа"),
                RecipeIngredient("Томатный соус", "120 мл"),
                RecipeIngredient("Моцарелла", "200 г"),
                RecipeIngredient("Базилик", "по вкусу")
            ),
            steps = listOf(
                RecipeStep("Раскатай тесто и смажь соусом.", 18),
                RecipeStep("Добавь моцареллу и выпекай 12-15 минут при 220°C.", 27),
                RecipeStep("Укрась свежим базиликом.", 10)
            )
        )
    )

    override suspend fun getRecipes(): List<Recipe> {
        delay(300)
        return mockRecipes
    }

    override suspend fun getRecipeById(id: String): Recipe? {
        delay(150)
        return mockRecipes.firstOrNull { it.id == id }
    }
}

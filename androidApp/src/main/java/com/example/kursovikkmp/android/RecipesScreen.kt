package com.example.kursovikkmp.android

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.core.AppShapes
import com.example.core.BaseScreen
import com.example.core.MyText
import com.example.core.Toolbar
import com.example.kursovikkmp.feature.recipes.details.RecipeIngredientUiState
import com.example.kursovikkmp.feature.recipes.details.RecipeStepUiState
import com.example.kursovikkmp.feature.recipes.details.RecipesDetailsEvents
import com.example.kursovikkmp.feature.recipes.details.RecipesDetailsState
import com.example.kursovikkmp.feature.recipes.details.RecipesDetailsViewModel
import com.example.kursovikkmp.feature.recipes.list.RecipeUiState
import com.example.kursovikkmp.feature.recipes.list.RecipesListEvents
import com.example.kursovikkmp.feature.recipes.list.RecipesListState
import com.example.kursovikkmp.feature.recipes.list.RecipesListViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

private val ScreenBg = Color(0xFFF2F2F2)
private val CardBg = Color(0xFFF7F7F7)
private val AccentGreen = Color(0xFF2F8E5B)

@Composable
fun RecipesScreen() {
    val viewModel: RecipesListViewModel = koinViewModel()
    val state by viewModel.flowState.collectAsState()
    val lceState by viewModel.lceState.collectAsState()

    BaseScreen(
        lceState = lceState,
        onDefaultUiEvent = viewModel::onDefaultUiEvent
    ) {
        RecipesScreenView(state = state, onUiEvent = viewModel::pushEvent)
    }
}

@Composable
fun RecipesScreenView(
    state: RecipesListState,
    onUiEvent: (RecipesListEvents) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        LazyColumn(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
            items(state.recipesItems) { item ->
                RecipeItemView(item) { recipeId ->
                    onUiEvent(RecipesListEvents.OnItemClicked(recipeId))
                }
            }
            item {
                if (state.recipesItems.isEmpty()) {
                    TextButton(onClick = {
                        onUiEvent(RecipesListEvents.OnRetryClicked)
                    }) {
                        Text("Повторить")
                    }
                }
            }
        }
    }
}

@Composable
private fun RecipeItemView(item: RecipeUiState, onClicked: (String) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clickable { onClicked(item.id) },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            if (item.imageUrl.isNotBlank()) {
                AsyncImage(
                    model = item.imageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
            Text(text = item.title)
            Text(text = item.text, modifier = Modifier.padding(top = 8.dp))
            Text(text = item.duration, modifier = Modifier.padding(top = 8.dp))
        }
    }
}

@Composable
fun RecipesDetailsScreen(recipeId: String) {
    val viewModel: RecipesDetailsViewModel = koinViewModel(parameters = { parametersOf(recipeId) })
    val state by viewModel.flowState.collectAsState()
    val lceState by viewModel.lceState.collectAsState()

    BaseScreen(
        lceState = lceState,
        onDefaultUiEvent = viewModel::onDefaultUiEvent
    ) {
        RecipesDetailsView(
            state = state,
            onUiEvent = viewModel::pushEvent
        )
    }
}

@Composable
private fun RecipesDetailsView(
    state: RecipesDetailsState,
    onUiEvent: (RecipesDetailsEvents) -> Unit
) {
    val ingredientItems = if (state.ingredientsItems.isNotEmpty()) {
        state.ingredientsItems
    } else {
        state.ingredientsState.value
            .lines()
            .map { it.replace("•", "").trim() }
            .filter { it.isNotBlank() }
            .map { RecipeIngredientUiState(name = it, amount = "по вкусу") }
    }

    val stepItems = if (state.stepsItems.isNotEmpty()) {
        state.stepsItems
    } else {
        state.stepsState.value
            .lines()
            .map { it.substringAfter('.').trim().ifEmpty { it } }
            .filter { it.isNotBlank() }
            .mapIndexed { index, text ->
                RecipeStepUiState(number = index + 1, text = text, duration = "")
            }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ScreenBg)
    ) {
        Toolbar(toolbarState = state.titleBarState)

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text(
                    text = state.titleState.value,
                    style = MaterialTheme.typography.titleLarge,
                    color = Color(0xFF1C1C1C),
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(top = 8.dp)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 6.dp)
                ) {
                    Text(
                        text = "⏱",
                        style = MaterialTheme.typography.bodySmall,
                        color = AccentGreen
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = state.durationState.value.replace("⏱", "").trim().replace("мин", "минут"),
                        style = MaterialTheme.typography.bodySmall,
                        color = AccentGreen,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            item {
                state.imageUrl?.let { imageUrl ->
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(190.dp)
                            .clip(RoundedCornerShape(10.dp)),
                        contentScale = ContentScale.Crop
                    )
                }
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(containerColor = CardBg),
                    border = BorderStroke(1.dp, Color(0xFFE1E1E1))
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(
                            text = state.ingredientsTitleState.value,
                            style = MaterialTheme.typography.titleMedium,
                            color = AccentGreen,
                            fontWeight = FontWeight.SemiBold
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        ingredientItems.forEachIndexed { index, item ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.Top
                            ) {
                                Text(
                                    text = "• ${item.name}",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color(0xFF2A2A2A),
                                    modifier = Modifier.weight(1f)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = item.amount,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color(0xFF6E6E6E),
                                    textAlign = TextAlign.End
                                )
                            }
                            if (index != ingredientItems.lastIndex) {
                                Spacer(modifier = Modifier.height(6.dp))
                            }
                        }
                    }
                }
            }

            item {
                Text(
                    text = "Шаги приготовления",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF1F1F1F)
                )
            }

            items(stepItems) { step ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
                    border = BorderStroke(1.dp, Color(0xFFE4E4E4))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFEDEDED)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = step.number.toString(),
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF777777)
                            )
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = step.text,
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color(0xFF5A5A5A)
                            )
                            if (step.duration.isNotEmpty()) {
                                Text(
                                    text = step.duration,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color(0xFF8C8C8C),
                                    modifier = Modifier.padding(top = 8.dp)
                                )
                            }
                        }

                        Checkbox(
                            checked = false,
                            onCheckedChange = null,
                            colors = CheckboxDefaults.colors(
                                uncheckedColor = Color(0xFFBCBCBC),
                                checkedColor = AccentGreen,
                                disabledUncheckedColor = Color(0xFFBCBCBC),
                                disabledCheckedColor = AccentGreen
                            )
                        )
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        if (state.titleState.value.isEmpty()) {
            TextButton(
                onClick = { onUiEvent(RecipesDetailsEvents.OnRetryClicked) },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Повторить")
            }
        } else {
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(containerColor = AccentGreen),
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 14.dp)
                    .height(50.dp)
            ) {
                Text(text = state.startCookingButtonTitle)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewRecipesScreenView() {
    MaterialTheme {
        RecipesScreenView(
            state = RecipesListState.getMock(),
            onUiEvent = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewRecipesDetailsView() {
    MaterialTheme {
        RecipesDetailsView(
            state = RecipesDetailsState.getMock(),
            onUiEvent = {}
        )
    }
}

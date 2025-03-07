package com.example.feature_favorites

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.core.BaseScreen
import com.example.core.Toolbar
import com.example.core.VSpacer
import com.example.core.extensions.COMPOSE_PREVIEW_BACKGROUND_COLOR
import com.example.core.extensions.color
import com.example.kursovikkmp.feature.favorites.list.FavoritesListEvents
import com.example.kursovikkmp.feature.favorites.list.FavoritesListState
import com.example.kursovikkmp.feature.favorites.list.FavoritesListViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun FavoriteScreen() {
    val viewModel: FavoritesListViewModel = koinViewModel()
    val state by viewModel.flowState.collectAsState()
    val lceState by viewModel.lceState.collectAsState()

    BaseScreen(lceState = lceState,
        onDefaultUiEvent = viewModel::onDefaultUiEvent) {
        FavoriteScreenView(state = state,
            onUiEvent = viewModel::pushEvent
        )
    }
}


@Composable
fun FavoriteScreenView(
    state: FavoritesListState,
    onUiEvent: (FavoritesListEvents) -> Unit) {
    Column(modifier = Modifier.fillMaxSize().background(state.backGroundColor.color()),) {
        Toolbar(toolbarState = state.titleBarState)
        Column(
            verticalArrangement = Arrangement.SpaceBetween) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(12.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
            ) {
                items(state.favoritesItems.size) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        FavoriteItemView(article = state.favoritesItems[it],
                            onClicked = { title ->
                                onUiEvent(
                                    FavoritesListEvents.OnItemClicked(
                                        title
                                    )
                                )
                            },
                            onFavorite = { title ->
                                onUiEvent(
                                    FavoritesListEvents.OnFavoriteClicked(
                                        title
                                    )
                                )
                            })
                    }
                }
            }

            VSpacer(10.dp)
        }
    }
}

@Preview(showBackground = true, backgroundColor = COMPOSE_PREVIEW_BACKGROUND_COLOR)
@Composable
private fun PreviewFavoriteScreenView() {
    MaterialTheme {
        FavoriteScreenView(state = FavoritesListState.getMock(),
            onUiEvent = {})
    }
}
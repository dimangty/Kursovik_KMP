package com.example.kursovikkmp.android.feature.News

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.kursovikkmp.android.Common.Extensions.COMPOSE_PREVIEW_BACKGROUND_COLOR
import com.example.kursovikkmp.android.Common.theme.AppShapes
import com.example.kursovikkmp.android.MyApplicationTheme
import com.example.kursovikkmp.android.feature.view.BaseScreen
import com.example.kursovikkmp.android.feature.view.MyButton
import com.example.kursovikkmp.android.feature.view.MyText
import com.example.kursovikkmp.android.feature.view.Toolbar
import com.example.kursovikkmp.android.feature.view.VSpacer
import com.example.kursovikkmp.feature.news.details.NewsDetailsEvents
import com.example.kursovikkmp.feature.news.details.NewsDetailsState
import com.example.kursovikkmp.feature.news.details.NewsDetailsViewModel
import com.example.kursovikkmp.feature.news.list.NewsUiState
import com.example.kursovikkmp.feature.news.model.Article
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun NewsDetailsScreen(title: String) {
    val viewModel: NewsDetailsViewModel = koinViewModel(parameters = { parametersOf(title) })
    val state by viewModel.flowState.collectAsState()
    val lceState by viewModel.lceState.collectAsState()

    BaseScreen(
        lceState = lceState,
        onDefaultUiEvent = viewModel::onDefaultUiEvent
    ) {
        NewsDetailsView(
            state = state,
            onUiEvent = viewModel::pushEvent
        )
    }
}

@Composable
fun NewsDetailsView(
    state: NewsDetailsState,
    onUiEvent: (NewsDetailsEvents) -> Unit
) {
    Column {
        Toolbar(toolbarState = state.titleBarState)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column() {
                state.imageUrl?.let { imageUrl ->
                    Column(modifier = Modifier.clip(AppShapes.rounded)) {
                        AsyncImage(
                            model = imageUrl,
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(240.dp),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
                VSpacer(16.dp)
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        MyText(state = state.dateState)
                        MyButton(
                            modifier = Modifier,
                            onClick = { onUiEvent(NewsDetailsEvents.OnFavoriteClicked) },
                            state = state.favoriteButton
                        )
                    }

                    MyText(state = state.titleState, maxLines = 3)
                    MyText(state = state.textState, maxLines = 10)
                }

            }

            MyButton(state = state.openButton) {
                onUiEvent(NewsDetailsEvents.OnOpenClicked)
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = COMPOSE_PREVIEW_BACKGROUND_COLOR)
@Composable
private fun PreviewNewsDetailsView() {
    MyApplicationTheme {
        NewsDetailsView(state = NewsDetailsState.getMock()) { }
    }
}
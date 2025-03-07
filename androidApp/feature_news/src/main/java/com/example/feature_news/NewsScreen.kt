package com.example.feature_news

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.core.BaseScreen
import com.example.core.Toolbar
import com.example.core.VSpacer
import com.example.core.extensions.COMPOSE_PREVIEW_BACKGROUND_COLOR
import com.example.core.extensions.color
import com.example.kursovikkmp.feature.news.list.NewsListEvents
import com.example.kursovikkmp.feature.news.list.NewsListState
import com.example.kursovikkmp.feature.news.list.NewsListViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun NewsScreen() {
    val viewModel: NewsListViewModel = koinViewModel()
    val state by viewModel.flowState.collectAsState()
    val lceState by viewModel.lceState.collectAsState()

    BaseScreen(
        lceState = lceState,
        onDefaultUiEvent = viewModel::onDefaultUiEvent
    ) {
        NewsScreenView(
            state = state,
            onUiEvent = viewModel::pushEvent
        )
    }
}


@Composable
fun NewsScreenView(
    state: NewsListState,
    onUiEvent: (NewsListEvents) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = state.backGroundColor.color())
    ) {
        Toolbar(toolbarState = state.titleBarState)
        LazyColumn(modifier = Modifier.padding(horizontal = 8.dp)) {
            item { VSpacer(8.dp) }
            items(state.newsItems) { item ->
                ArticleItemView(article = item,
                    onClicked = {
                        onUiEvent(NewsListEvents.OnItemClicked(item.title))
                    },
                    onFavorite = { title -> onUiEvent(NewsListEvents.OnFavoriteClicked(title)) })
                VSpacer(8.dp)
            }
        }
    }

}

@Preview(showBackground = true, backgroundColor = COMPOSE_PREVIEW_BACKGROUND_COLOR)
@Composable
private fun PreviewNewsScreenView() {
    MaterialTheme {
        NewsScreenView(state = NewsListState.getMock(),
            onUiEvent = {})
    }
}
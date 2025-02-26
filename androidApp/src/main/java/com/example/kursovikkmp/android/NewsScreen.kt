package com.example.kursovikkmp.android



import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import com.example.kursovikkmp.feature.news.list.NewsListViewModel
import org.koin.androidx.compose.koinViewModel
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kursovikkmp.android.Common.Extensions.COMPOSE_PREVIEW_BACKGROUND_COLOR
import com.example.kursovikkmp.android.feature.view.VSpacer
import com.example.kursovikkmp.feature.news.list.NewsListState

@Composable
fun NewsScreen(navController: NavController, viewModel: NewsListViewModel = koinViewModel(),) {
    val state by viewModel.flowState.collectAsState()

    MyApplicationTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            NewsScreenView(state)
        }
    }
}

@Composable
fun NewsScreenView(state: NewsListState) {
    LazyColumn(modifier = Modifier) {
        item { VSpacer(8.dp) }
        items(state.newsItems) { item ->
            ArticleItemView(article = item)
        }
    }
}

@Preview(showBackground = true, backgroundColor = COMPOSE_PREVIEW_BACKGROUND_COLOR)
@Composable
private fun PreviewNewsScreenView() {
    MyApplicationTheme {
        NewsScreenView(NewsListState.getMock())
    }
}
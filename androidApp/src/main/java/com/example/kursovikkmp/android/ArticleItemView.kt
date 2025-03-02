package com.example.kursovikkmp.android

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.kursovikkmp.android.Common.Extensions.COMPOSE_PREVIEW_BACKGROUND_COLOR
import com.example.kursovikkmp.android.Common.theme.AppShapes
import com.example.kursovikkmp.android.feature.view.MyButton
import com.example.kursovikkmp.android.feature.view.MyText
import com.example.kursovikkmp.android.feature.view.VSpacer
import com.example.kursovikkmp.feature.news.list.NewsUiState

@Composable
fun ArticleItemView(article: NewsUiState,
                    onClicked: (String) -> Unit,
                    onFavorite: (String) -> Unit) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .clickable { onClicked(article.id) }
        ) {
        Column (modifier = Modifier.padding(16.dp).clip(AppShapes.rounded).background(Color.White)) {
            article.imageUrl?.let { imageUrl ->
                AsyncImage(
                    model = imageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
//                        .clip(AppShapes.primaryTop)
                        .height(132.dp),
                    contentScale = ContentScale.FillWidth
                )
            }
            Column(modifier = Modifier.padding(16.dp).fillMaxWidth()) {
                Row( modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    MyText(state = article.dateState)
                    MyButton(modifier = Modifier,
                        onClick = {onFavorite(article.title)},
                        state = article.favoriteButton)
                }
                MyText(state = article.titleState)
                VSpacer(8.dp)
                MyText(state = article.textState)
            }

        }
    }
}

@Preview(showBackground = true, backgroundColor = COMPOSE_PREVIEW_BACKGROUND_COLOR)
@Composable
private fun PreviewArticleItemView() {
    MyApplicationTheme {
        ArticleItemView(article = NewsUiState.getMock(),
                        onClicked = {},
                        onFavorite = {})
    }
}
package com.example.feature_favorites

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.core.AppShapes
import com.example.core.MyButton
import com.example.core.MyText
import com.example.core.extensions.COMPOSE_PREVIEW_BACKGROUND_COLOR
import com.example.core.extensions.color
import com.example.kursovikkmp.feature.favorites.list.FavoriteUiState

@Composable
fun FavoriteItemView(
    article: FavoriteUiState,
    onClicked: (String) -> Unit,
    onFavorite: (String) -> Unit
) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .clip(AppShapes.rounded)
        .clickable { onClicked(article.title) },
        colors = CardDefaults.cardColors(
            containerColor = article.cellBackground.color(), //Card background color
            contentColor = Color.Gray  //Card content color,e.g.text
        )
    ) {
        Column(modifier = Modifier
            .clip(AppShapes.rounded)) {

            AsyncImage(
                model = article.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(132.dp),
                contentScale = ContentScale.FillBounds
            )

            Column(modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()) {
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween) {
                    MyText(state = article.dateState)
                    MyButton(
                        modifier = Modifier,
                        onClick = { onFavorite(article.title) },
                        state = article.favoriteButton
                    )
                }
                MyText(state = article.titleState)
            }

        }
    }
}

@Preview(showBackground = true, backgroundColor = COMPOSE_PREVIEW_BACKGROUND_COLOR)
@Composable
private fun PreviewFavoriteItemView() {
    MaterialTheme {
        FavoriteItemView(article = FavoriteUiState.getMock(),
            onClicked = {},
            onFavorite = {})
    }
}
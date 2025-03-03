package com.example.kursovikkmp.feature.news.list

import com.example.kursovikkmp.base.BaseEvent

sealed class NewsListEvents: BaseEvent {
    class OnFavoriteClicked(val title: String) : NewsListEvents()
    class OnItemClicked(val title: String) : NewsListEvents()
}
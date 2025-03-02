package com.example.kursovikkmp.feature.news.list

import com.example.kursovikkmp.base.BaseEvent

sealed class NewsListEvents: BaseEvent {
    class OnNewsClicked(val title: String) : NewsListEvents()
}
package com.example.kursovikkmp.DB

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ArticleEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAt: String
)
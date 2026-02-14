package com.example.kursovikkmp.feature.favorites.list

import com.example.kursovikkmp.DB.ArticleDao
import com.example.kursovikkmp.database.ArticleDb
import com.example.kursovikkmp.feature.news.model.Article

interface FavoritesRepository {
    fun getAllFlow(): List<Article>
    suspend fun get(title: String): ArticleDb?
    suspend fun insert(article: Article)
    suspend fun delete(title: String)
    suspend fun check(title: String): Boolean
}

class FavoritesRepositoryImpl(
    private val dao: ArticleDao
) : FavoritesRepository {
    override fun getAllFlow(): List<Article> = dao.getAll()
    override suspend fun get(title: String): ArticleDb? = dao.get(title)
    override suspend fun insert(article: Article) = dao.insert(article)
    override suspend fun delete(title: String) = dao.delete(title)
    override suspend fun check(title: String): Boolean = dao.check(title)
}

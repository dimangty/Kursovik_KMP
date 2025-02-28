package com.example.kursovikkmp.feature.favorites.list

import com.example.kursovikkmp.DB.AppDatabase
import com.example.kursovikkmp.DB.ArticleDao
import com.example.kursovikkmp.DB.ArticleEntity
import kotlinx.coroutines.flow.Flow

class FavoritesRepository(private val database: AppDatabase) {
    private val dao: ArticleDao by lazy {
        database.getDao()
    }

    suspend fun addTodo(todoEntity: ArticleEntity) {
        dao.insert(todoEntity)
    }

    suspend fun loadTodos(): Flow<List<ArticleEntity>> {
        return dao.getAllAsFlow()
    }
}
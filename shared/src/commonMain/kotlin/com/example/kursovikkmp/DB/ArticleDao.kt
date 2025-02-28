package com.example.kursovikkmp.DB

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {
    @Insert
    suspend fun insert(item: ArticleEntity)

    @Query("SELECT count(*) FROM ArticleEntity")
    suspend fun count(): Int

    @Query("SELECT * FROM ArticleEntity")
    fun getAllAsFlow(): Flow<List<ArticleEntity>>
}
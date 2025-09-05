package com.dalima.wikipedia_codenicely_assignment

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WikiDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(list: List<ArticleEntity>)

    @Query("SELECT * FROM articles ORDER BY fetchedAt DESC")
    suspend fun getAllArticles(): List<ArticleEntity>

    @Query("SELECT pageId FROM articles")
    suspend fun getAllArticleIds(): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImages(list: List<ImageEntity>)

    @Query("SELECT * FROM images ORDER BY fetchedAt DESC")
    suspend fun getAllImages(): List<ImageEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategories(list: List<CategoryEntity>)

    @Query("SELECT * FROM categories ORDER BY name ASC")
    suspend fun getAllCategories(): List<CategoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContinueToken(token: ContinueTokensEntity)

    @Query("SELECT token FROM continue_tokens WHERE endpoint = :endpoint LIMIT 1")
    suspend fun getContinueToken(endpoint: String): String?
}

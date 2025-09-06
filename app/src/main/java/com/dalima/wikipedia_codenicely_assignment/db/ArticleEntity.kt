package com.dalima.wikipedia_codenicely_assignment.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles")
data class ArticleEntity(
    @PrimaryKey val pageId: Long,
    val title: String,
    val snippet: String?,
    val content: String?,
    val imageUrl: String? = null,
    val fetchedAt: Long = System.currentTimeMillis()
)
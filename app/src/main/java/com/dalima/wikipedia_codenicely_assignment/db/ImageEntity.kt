package com.dalima.wikipedia_codenicely_assignment.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "images")
data class ImageEntity(
    @PrimaryKey val url: String,
    val user: String?,
    val timestamp: String?,
    val title: String?,
    val fetchedAt: Long = System.currentTimeMillis()
)
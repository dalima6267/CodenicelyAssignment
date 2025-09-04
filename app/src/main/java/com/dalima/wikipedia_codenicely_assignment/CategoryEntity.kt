package com.dalima.wikipedia_codenicely_assignment

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class CategoryEntity(
    @PrimaryKey val name: String,
    val fetchedAt: Long = System.currentTimeMillis()
)

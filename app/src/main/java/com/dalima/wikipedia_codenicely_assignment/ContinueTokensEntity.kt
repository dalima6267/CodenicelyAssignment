package com.dalima.wikipedia_codenicely_assignment

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "continue_tokens")
data class ContinueTokensEntity(
    @PrimaryKey val endpoint: String, // "random", "featured", "categories"
    val token: String?
)

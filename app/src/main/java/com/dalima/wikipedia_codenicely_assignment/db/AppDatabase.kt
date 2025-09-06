package com.dalima.wikipedia_codenicely_assignment.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dalima.wikipedia_codenicely_assignment.db.ArticleEntity
import com.dalima.wikipedia_codenicely_assignment.db.CategoryEntity
import com.dalima.wikipedia_codenicely_assignment.db.ContinueTokensEntity
import com.dalima.wikipedia_codenicely_assignment.db.ImageEntity
import com.dalima.wikipedia_codenicely_assignment.db.WikiDao

@Database(
    entities = [ ArticleEntity::class,
        ImageEntity::class,
        CategoryEntity::class,
        ContinueTokensEntity::class],
    version = 4,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun wikiDao(): WikiDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val inst = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "wiki_reader_db"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = inst
                inst
            }
        }
    }
}
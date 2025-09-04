package com.dalima.wikipedia_codenicely_assignment

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        ArticleEntity::class,
        ImageEntity::class,
        CategoryEntity::class,
        ContinueTokensEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun wikiDao(): WikiDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "wiki_reader_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

package com.fivesysdev.newsapp.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.fivesysdev.newsapp.room.models.news.favorite.FavoriteNews
import com.fivesysdev.newsapp.room.models.news.favorite.favoriteNewsDao
import com.fivesysdev.newsapp.room.models.news.news.News
import com.fivesysdev.newsapp.room.models.news.news.newsDao

@Database(
    entities = arrayOf(News::class, FavoriteNews::class),
    version = 4,
    exportSchema = false
)
abstract class NewsDatabase: RoomDatabase() {
    abstract fun newsDao(): newsDao
    abstract fun favoriteNewsDao(): favoriteNewsDao

    companion object {
        private var INSTANCE: NewsDatabase? = null
        private val DB_NAME = "NewsApp"
        private val LOCK = Any()

        operator fun invoke(context: Context) = INSTANCE ?: synchronized(LOCK) {
            INSTANCE ?: buildDatabase(context).also {
                INSTANCE = it
            }
        }
        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            NewsDatabase::class.java,
            DB_NAME
        ).fallbackToDestructiveMigration().build()
    }
}
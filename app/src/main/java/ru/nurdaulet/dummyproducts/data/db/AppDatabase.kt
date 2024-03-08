package ru.nurdaulet.dummyproducts.data.db

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ProductDbModel::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun productsDao(): ProductsDao

    companion object {
        private const val DATABASE_NAME = "app_database.db"

        private var INSTANCE: AppDatabase? = null
        private var LOCK = Any()

        fun getInstance(application: Application): AppDatabase {
            INSTANCE?.let {
                return it
            }
            synchronized(LOCK) {
                INSTANCE?.let {
                    return it
                }
                val db = Room.databaseBuilder(
                    application,
                    AppDatabase::class.java,
                    DATABASE_NAME
                ).build()

                INSTANCE = db
                return db
            }
        }
    }
}
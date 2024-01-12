package com.example.shopping.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ShoppingItem::class], version = 1)
abstract class ShoppingItemDatabase : RoomDatabase() {
    abstract fun shoppingItemDao(): ShoppingItemDao
    companion object {
        //Singleton
        @Volatile
        private var instance: ShoppingItemDatabase? = null
        private val lock = Any()
        operator fun invoke(context: Context) = instance ?: synchronized(lock) {
            instance ?: makeDatabase(context).also {
                instance = it
            }
        }

        private fun makeDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext, ShoppingItemDatabase::class.java, "product"
        ).build()
    }
}
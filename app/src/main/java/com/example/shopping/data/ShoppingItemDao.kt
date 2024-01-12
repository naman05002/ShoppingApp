package com.example.shopping.data

import androidx.annotation.RequiresPermission
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

import retrofit2.http.GET

@Dao
interface ShoppingItemDao {
    @Insert
    suspend fun insertEntity(shoppingItem: ShoppingItem)
    @Query(value = "DELETE FROM shoppingitem")
    suspend fun deleteAllRecords()
    @Query(value = "SELECT * FROM shoppingitem")
    suspend fun getAllRecords(): List<ShoppingItem>
}
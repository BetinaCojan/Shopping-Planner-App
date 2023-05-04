package com.example.myapplication.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.myapplication.data.db.entities.ShoppingItem
import androidx.room.Dao
import androidx.room.Query

@Dao
interface ShoppingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(item: ShoppingItem): Long

    @Delete
    suspend fun delete(item: ShoppingItem): Int

    @Query("SELECT * FROM shopping_items")
    fun getAllShoppingItems(): LiveData<List<ShoppingItem>>
}
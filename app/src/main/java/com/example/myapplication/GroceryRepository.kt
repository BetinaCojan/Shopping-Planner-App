package com.example.myapplication

class GroceryRepository(private val db:GroceryDatabase) {

    suspend fun insert(items: GroceryItems) = db.getGroceryDao().insert(items)
    suspend fun delete(items: GroceryItems) = db.getGroceryDao().delete(items)

    fun gelAllItems() = db.getGroceryDao().getAllGroceryItems()
}
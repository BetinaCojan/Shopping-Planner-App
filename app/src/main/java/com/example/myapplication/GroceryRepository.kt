package com.example.myapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import kotlin.collections.filter
import com.example.myapplication.GroceryItems

class GroceryRepository(private val db: GroceryDatabase) {

    suspend fun insert(items: GroceryItems) = db.getGroceryDao().insert(items)
    suspend fun delete(items: GroceryItems) = db.getGroceryDao().delete(items)

    fun getAllItems() = db.getGroceryDao().getAllGroceryItems()

    fun searchGroceryItems(query: String): MediatorLiveData<List<GroceryItems>?> {
        val result = MediatorLiveData<List<GroceryItems>?>()

        result.addSource(db.getGroceryDao().getAllGroceryItems()) { allItems ->
            val filteredList = allItems?.let { items ->
                items.filter { it.itemName.contains(query, true) }
            }
            result.postValue(filteredList)
        }
        return result
    }
}








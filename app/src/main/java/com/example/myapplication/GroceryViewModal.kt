package com.example.myapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class GroceryViewModal(private val repository: GroceryRepository) : ViewModel() {

    fun insert(items: GroceryItems) = GlobalScope.launch {
        repository.insert(items)
    }

    fun  delete(items: GroceryItems) = GlobalScope.launch {
        repository.delete(items)
    }

    fun getAllItems(): LiveData<List<GroceryItems>> {
        return repository.getAllItems()
    }

    fun searchGroceryItems(query: String): MediatorLiveData<List<GroceryItems>?> {
        return repository.searchGroceryItems(query)
    }

}

package com.example.myapplication

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import kotlin.collections.filter
import com.example.myapplication.GroceryItems
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class GroceryRepository(private val db: GroceryDatabase) {

    suspend fun insert(items: GroceryItems) {
        db.getGroceryDao().insert(items)
//        val firebaseFirestore = Firebase.firestore
//        val groceryItem = hashMapOf(
//            "itemName" to items.itemName,
//            "itemQuantity" to items.itemQuantity,
//            "itemPrice" to items.itemPrice
//        )
//        firebaseFirestore.collection("grocery_items")
//            .add(groceryItem)
//            .addOnSuccessListener { documentReference ->
//                println("DocumentSnapshot added with ID: ${documentReference.id}")
//            }
//            .addOnFailureListener { e ->
//                println("Error adding document: $e")
//            }

        val firebaseDatabase = Firebase.database
        val groceryItem = hashMapOf(
            "itemName" to items.itemName,
            "itemQuantity" to items.itemQuantity,
            "itemPrice" to items.itemPrice
        )
        firebaseDatabase.getReference("grocery_items")
            .push()
            .setValue(groceryItem)
            .addOnSuccessListener {
                println("DocumentSnapshot added with ID: ")
            }
            .addOnFailureListener { e ->
                println("Error adding document: $e")
            }
    }
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








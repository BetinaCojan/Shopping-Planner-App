package com.example.myapplication.ui.shoppinglist

import com.example.myapplication.data.db.entities.ShoppingItem

interface AddDialogListener {
    fun onAddButtonClicked(item: ShoppingItem)
}
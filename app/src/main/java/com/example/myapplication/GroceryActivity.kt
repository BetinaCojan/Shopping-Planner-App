package com.example.myapplication

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class GroceryActivity : AppCompatActivity() , GroceryRVAdapter.GroceryItemClickInterface{
    lateinit var itemsRV: RecyclerView
    lateinit var addFAB: FloatingActionButton
    lateinit var list: List<GroceryItems>
    lateinit var groceryRVAdapter: GroceryRVAdapter
    lateinit var groceryViewModal: GroceryViewModal
    private lateinit var searchEditText: EditText
    private lateinit var searchButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grocery)

        // Găsește elementele de căutare din XML
        searchEditText = findViewById(R.id.searchEditText)
        searchButton = findViewById(R.id.searchButton)

        // Setează un listener pentru butonul de căutare
        searchButton.setOnClickListener {
            // Get text din EditText pentru căutare
            val query = searchEditText.text.toString()

            // Get text din EditText pentru cautare
            val itemName = searchEditText.text.toString()

            // Cauta item-ul cu numele dat de utilizator
            groceryViewModal.searchGroceryItems(itemName).observe(this, Observer {
                if (it != null) {
                    groceryRVAdapter.list = it
                }
                groceryRVAdapter.notifyDataSetChanged()

                if (it != null) {
                    if (it.isEmpty()) {
                        Toast.makeText(this, "No results found", Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }

        itemsRV = findViewById(R.id.idRVItems)
        addFAB = findViewById(R.id.idFABAdd)
        list = ArrayList<GroceryItems>()
        groceryRVAdapter = GroceryRVAdapter(list,this)
        itemsRV.layoutManager = LinearLayoutManager(this)
        itemsRV.adapter = groceryRVAdapter

        val groceryRepository = GroceryRepository(GroceryDatabase(this))
        val factory = GroceryViewModalFactory(groceryRepository)

        groceryViewModal = ViewModelProvider(this,factory).get(GroceryViewModal::class.java)
        groceryViewModal.getAllItems().observe(this, Observer {
            groceryRVAdapter.list = it
            groceryRVAdapter.notifyDataSetChanged()
        })

        addFAB.setOnClickListener{
            openDialog()
        }

    }

    fun openDialog(){
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.grocery_add_dialog)
        val cancelBtn = dialog.findViewById<Button>(R.id.idBtnCancel)
        val addBtn = dialog.findViewById<Button>(R.id.idBtnAdd)
        val itemEdt = dialog.findViewById<EditText>(R.id.idEdtItemName)
        val itemPriceEdt = dialog.findViewById<EditText>(R.id.idEdtItemPrice)
        val itemQuantityEdt = dialog.findViewById<EditText>(R.id.idEdtItemQuantity)
        cancelBtn.setOnClickListener {
            dialog.dismiss()
        }
        addBtn.setOnClickListener {
            val itemName : String = itemEdt.text.toString()
            val itemPrice : String = itemPriceEdt.text.toString()
            val itemQuantity : String = itemQuantityEdt.text.toString()
            val qty : Int = itemQuantity.toInt()
            var pr : Int = itemPrice.toInt()
            if (itemName.isNotEmpty() && itemPrice.isNotEmpty() && itemQuantity.isNotEmpty()){
                val items = GroceryItems(itemName,qty,pr)
                groceryViewModal.insert(items)
                Toast.makeText(applicationContext,"Item Inserted..", Toast.LENGTH_SHORT).show()
                groceryRVAdapter.notifyDataSetChanged()
                dialog.dismiss()
            }
            else {

                Toast.makeText(applicationContext, "Please Enter All The Data...", Toast.LENGTH_SHORT).show()
            }
        }
        dialog.show()
    }

    override fun onItemClick(groceryItems: GroceryItems) {
        groceryViewModal.delete(groceryItems)
        groceryRVAdapter.notifyDataSetChanged()
        Toast.makeText(applicationContext,"Item Deleted..", Toast.LENGTH_SHORT).show()
    }
}
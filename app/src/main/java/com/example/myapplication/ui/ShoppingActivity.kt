
package com.example.myapplication.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.data.db.entities.ShoppingItem
import com.example.myapplication.other.ShoppingItemAdapter
import com.example.myapplication.ui.shoppinglist.ShoppingViewModel
import com.example.myapplication.ui.shoppinglist.ShoppingViewModelFactory
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import com.example.myapplication.databinding.ActivityShoppingBinding
import com.example.myapplication.ui.shoppinglist.AddDialogListener
import com.example.myapplication.ui.shoppinglist.AddShoppingItemDialog

class ShoppingActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()
    private val factory: ShoppingViewModelFactory by instance()

    private lateinit var binding: ActivityShoppingBinding
    private lateinit var viewModel: ShoppingViewModel
    private lateinit var adapter: ShoppingItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShoppingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, factory).get(ShoppingViewModel::class.java)
        adapter = ShoppingItemAdapter(listOf(), viewModel)

        binding.rvShoppingItems.layoutManager = LinearLayoutManager(this)
        binding.rvShoppingItems.adapter = adapter

        viewModel.getAllShoppingItems().observe(this, Observer {
            adapter.items = it
            adapter.notifyDataSetChanged()
        })

        binding.fab.setOnClickListener {
            AddShoppingItemDialog(
                this,
                object : AddDialogListener {
                    override fun onAddButtonClicked(item: ShoppingItem) {
                        viewModel.upsert(item)
                    }
                }).show()
        }
    }
}

package com.example.myapplication.my_ui.organize_lists

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.myapplication.R
import com.example.myapplication.my_ui.dashboard.DashboardFragment

class ItemsOfList : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_items_of_list)

        // Get reference to the Back button
        val btnBack = findViewById<Button>(R.id.btn_back)

        // Set a click listener for the Back button
        btnBack.setOnClickListener {
            // Finish the activity and go back to DashboardFragment
            finish()
        }
    }

    override fun onBackPressed() {
        // Create a FragmentManager object
        val fragmentManager = supportFragmentManager

        // Begin a FragmentTransaction
        val fragmentTransaction = fragmentManager.beginTransaction()

        // Replace the contents of the container with the DashboardFragment
        fragmentTransaction.replace(R.id.fragment_container, DashboardFragment())

        // Commit the FragmentTransaction
        fragmentTransaction.commit()
    }
}

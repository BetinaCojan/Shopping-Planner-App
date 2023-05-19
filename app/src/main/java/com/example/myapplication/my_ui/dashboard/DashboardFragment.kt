package com.example.myapplication.my_ui.dashboard

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.myapplication.GroceryActivity
import com.example.myapplication.R

class DashboardFragment : Fragment() {
    private lateinit var sharedPref: SharedPreferences
    private val countKey = "count_key"

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            // Inflate the layout for this fragment
            return inflater.inflate(R.layout.fragment_dashboard, container, false)
        }
    private fun incrementCount() {
        val currentCount = getCount()
        sharedPref.edit().putInt(countKey, currentCount + 1).apply()
    }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            val go_to_activity_item: Button = view.findViewById(R.id.btn_add)
            go_to_activity_item.setOnClickListener {
                val intent = Intent(requireContext(), GroceryActivity::class.java)
                startActivity(intent)
            }

            sharedPref = requireActivity().getSharedPreferences("myPref", Context.MODE_PRIVATE)

            // serializare locala a datelor
            sharedPref.edit().putInt(countKey, 0).apply()

//            val sharedPreferences = requireActivity().getSharedPreferences("sharedPrefs", 0)
//            val button = view.findViewById<Button>(R.id.btn_add)
//            button.setOnClickListener {
//                incrementCount()
//            }

        }
    private fun incrementCount() {
        val currentCount = getCount()
        sharedPref.edit().putInt(countKey, currentCount + 1).apply()
    }

    private fun getCount(): Int {
        return sharedPref.getInt(countKey, 0)
    }
}

package com.example.myapplication.my_ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.myapplication.GroceryActivity
import com.example.myapplication.R
import com.example.myapplication.my_ui.organize_lists.ItemsOfList
import com.example.myapplication.ui.ShoppingActivity


class DashboardFragment : Fragment() {

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            // Inflate the layout for this fragment
            return inflater.inflate(R.layout.fragment_dashboard, container, false)
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            val go_to_activity_item: Button = view.findViewById(R.id.btn_add)
            go_to_activity_item.setOnClickListener {
                val intent = Intent(requireContext(), GroceryActivity::class.java)
                startActivity(intent)
            }
        }

}

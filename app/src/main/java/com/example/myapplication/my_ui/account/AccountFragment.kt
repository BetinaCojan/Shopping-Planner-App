package com.example.myapplication.my_ui.account

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentAccountBinding
import com.example.myapplication.my_ui.authentication.Welcome

class AccountFragment : Fragment() {

    // Inițializați obiectul AccountViewModel.
    private val viewModel: AccountViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentAccountBinding.inflate(inflater, container, false)
        val logoutButton = binding.root.findViewById<Button>(R.id.logout_button)
        logoutButton.setOnClickListener {

            // apelarea metodei de logout din AccountViewModel
            viewModel.logout()

            // navigare inapoi la ecranul de pornire
            val intent = Intent(requireContext(), Welcome::class.java)
            startActivity(intent)
        }
        return binding.root
    }
}
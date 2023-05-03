package com.example.myapplication.my_ui.account

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AccountViewModel : ViewModel() {

    fun logout() {
        Firebase.auth.signOut()
    }
}

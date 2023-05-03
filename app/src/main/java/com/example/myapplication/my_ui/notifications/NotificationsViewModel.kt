package com.example.myapplication.my_ui.notifications

import android.app.Application
import android.location.Location
import androidx.lifecycle.AndroidViewModel

class NotificationsViewModel(application: Application) : AndroidViewModel(application) {
    private val targetLocation = Location("").apply {
        latitude = 52.5200
        longitude = 13.4050
    }

    fun isInTargetArea(latitude: Double, longitude: Double): Boolean {
        val userLocation = Location("").apply {
            this.latitude = latitude
            this.longitude = longitude
        }
        return userLocation.distanceTo(targetLocation) <= 500
    }
}
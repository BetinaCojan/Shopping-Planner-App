package com.example.myapplication.my_ui.notifications

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentNotificationsBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class NotificationsFragment : Fragment() {

    private val viewModel: NotificationsViewModel by viewModels()
    private lateinit var binding: FragmentNotificationsBinding
    private lateinit var locationProvider: FusedLocationProviderClient
    private lateinit var notificationManager: NotificationManagerCompat

    companion object {
        private const val CHANNEL_ID = "location_notification_channel"
        private const val NOTIFICATION_ID = 0
        private const val LOCATION_PERMISSION_REQUEST_CODE = 123
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        locationProvider = LocationServices.getFusedLocationProviderClient(requireContext())
        notificationManager = NotificationManagerCompat.from(requireContext())

        createNotificationChannel()
        sendNotification("You are in the target area!")

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        return binding.root
    }



    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Location Notifications"
            val descriptionText = "Shows notifications based on location"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }

            val notificationManager =
                requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonSendNotification.setOnClickListener {
            requestLocationPermission()
        }
    }

    private fun requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            getLocation()
        } else {
            if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                showLocationPermissionDeniedDialog()
            } else {
                requestPermissions(
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    LOCATION_PERMISSION_REQUEST_CODE
                )
            }
        }
    }

    private fun getLocation() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        locationProvider.lastLocation.addOnSuccessListener { location: Location? ->
            if (location != null) {
                if (viewModel.isInTargetArea(location.latitude, location.longitude)) {
                    sendNotification("You are in the target area!")
                } else {
                    sendNotification("You are not in the target area.")
                }
            } else {
                sendNotification("Could not get location.")
            }
        }.addOnFailureListener { e ->
            sendNotification("Error getting location: ${e.message}")
        }
    }

    private fun showLocationPermissionDeniedDialog() {
        AlertDialog.Builder(requireContext())
            .setMessage("Location permission denied.")
            .setPositiveButton("OK") { dialogInterface: DialogInterface, _: Int ->
                dialogInterface.dismiss()
            }
            .show()
    }

    @SuppressLint("MissingPermission")
    private fun sendNotification(message: String) {
        val notification = NotificationCompat.Builder(requireContext(), CHANNEL_ID)
            .setSmallIcon(R.drawable.icon_notifications)
            .setContentTitle("Location Notification")
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        notificationManager.notify(NOTIFICATION_ID, notification)
    }
}

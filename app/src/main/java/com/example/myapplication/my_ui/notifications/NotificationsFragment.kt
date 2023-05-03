package com.example.myapplication.my_ui.notifications

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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
import android.Manifest
import android.app.*


class NotificationsFragment : Fragment() {

    private val viewModel: NotificationsViewModel by viewModels()

    private lateinit var binding: FragmentNotificationsBinding

    private lateinit var locationProvider: FusedLocationProviderClient

    private lateinit var notificationManager: NotificationManagerCompat
    private lateinit var button_send_notification: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotificationsBinding.inflate(inflater, container, false)

        locationProvider = LocationServices.getFusedLocationProviderClient(requireContext())
        notificationManager = NotificationManagerCompat.from(requireContext())

        button_send_notification = binding.buttonSendNotification

        button_send_notification.setOnClickListener {
            requestLocationPermission()
        }

        return binding.root
    }

    private fun requestLocationPermission() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                getLocation()
            }
            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {
                showLocationPermissionDeniedDialog()
            }
            else -> {
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
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        locationProvider.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                if (viewModel.isInTargetArea(location.latitude, location.longitude)) {
                    showNotification("You are in the target area!")
                } else {
                    showNotification("You are not in the target area.")
                }
            } else {
                showNotification("Could not get location.")
            }
        }.addOnFailureListener { e ->
            showNotification("Error getting location: ${e.message}")
        }
    }

    private fun showLocationPermissionDeniedDialog() {
        AlertDialog.Builder(requireContext())
            .setMessage("Location permission denied.")
            .setPositiveButton("OK", null)
            .show()
    }

    private fun showNotification(message: String) {
        val notification = NotificationCompat.Builder(requireContext(), CHANNEL_ID)
            .setSmallIcon(R.drawable.icon_notifications)
            .setContentTitle("Location Notification")
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    companion object {
        private const val CHANNEL_ID = "location_notification_channel"
        private const val NOTIFICATION_ID = 0
        private const val LOCATION_PERMISSION_REQUEST_CODE = 123
    }
}

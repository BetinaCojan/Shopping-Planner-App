package com.example.myapplication.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.location.LocationManager

class GpsBroadcastReceiver : BroadcastReceiver() {
    interface GpsStatusListener {
        fun onGpsStatusChanged(gpsEnabled: Boolean)
    }

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == LocationManager.PROVIDERS_CHANGED_ACTION) {
            val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            notifyListener(context, gpsEnabled)
        }
    }

    private fun notifyListener(context: Context, gpsEnabled: Boolean) {
        val listener: GpsStatusListener? = getListener(context)
        listener?.onGpsStatusChanged(gpsEnabled)
    }

    companion object {
        private var listener: GpsStatusListener? = null
        private var receiver: GpsBroadcastReceiver? = null

        fun setListener(context: Context, listener: GpsStatusListener) {
            this.listener = listener
            registerReceiver(context)
        }

        fun removeListener(context: Context) {
            this.listener = null
            unregisterReceiver(context)
        }

        private fun getListener(context: Context): GpsStatusListener? {
            return listener
        }

        private fun registerReceiver(context: Context) {
            receiver?.let {
                // Receiver-ul este deja înregistrat
                return
            }
            receiver = GpsBroadcastReceiver()
            val filter = IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION)
            context.registerReceiver(receiver, filter)
        }

        private fun unregisterReceiver(context: Context) {
            receiver?.let {
                context.unregisterReceiver(it)
                receiver = null
            }
        }
    }
}


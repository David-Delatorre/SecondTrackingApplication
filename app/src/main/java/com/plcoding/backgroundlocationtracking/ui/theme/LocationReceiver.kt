package com.plcoding.backgroundlocationtracking.ui.theme

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import com.plcoding.backgroundlocationtracking.R

class LocationReceiver: BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        //Check if the received Intent has the correct action
        if (p1 != null) {
            if (p1.action == "com.example.ACTION_SEND_LOCATION") {
                //Extract latitude and longitude from the Intent
                val latitude = p1.getDoubleExtra("latitude", 0.0)
                val longitude = p1.getDoubleExtra("longitude", 0.0)


                Toast.makeText(
                    p0,
                    "Lat $latitude long $longitude",
                    Toast.LENGTH_SHORT
                ).show()

                // Send a notification to the phone on successful receive
                val notification = p0?.let {
                    NotificationCompat.Builder(it, "Location")
                        .setContentTitle("Tracking location...")
                        .setContentText("Location: null")
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setOngoing(true)
                }
                val updatedNotification = notification?.setContentText(
                    "Location Received: ($latitude, $longitude)"
                )

                // Do something with the data
                // Use the received latitude and longitude
                Log.d("LocationReceiver", "Received location: Lat=$latitude, Lon=$longitude")
            }
        }
    }
}
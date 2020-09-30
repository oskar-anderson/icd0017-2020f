package com.example.servicedemo

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    companion object {
        private val TAG = BackgroundService::class.java.simpleName
    }

    var backgroundServiceIsRunning = false
    var localBroadcastReceiverIndentFilter = IntentFilter()
    private val localBroadcastReceiver = LocalBroadcastReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        localBroadcastReceiverIndentFilter.addAction(C.intentBackgroundServiceTime)
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
        LocalBroadcastManager
            .getInstance(applicationContext)
            .registerReceiver(localBroadcastReceiver, localBroadcastReceiverIndentFilter)
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")
        LocalBroadcastManager
            .getInstance(applicationContext)
            .unregisterReceiver(localBroadcastReceiver)
    }

    fun buttonServiceOnClick(view: View) {
        val intent = Intent(this, BackgroundService::class.java)
        if (backgroundServiceIsRunning) {
            stopService(intent)
            buttonService.text = "RUN SERVICE"
        } else {
            startService(intent)
            buttonService.text = "STOP SERVICE"
        }
        backgroundServiceIsRunning = !backgroundServiceIsRunning

    }

    private inner class LocalBroadcastReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            Log.d(TAG, "onReceive")
            if (intent != null) {
                val s = intent.getStringExtra(C.intentBackgroundServiceTimePayload)
                Log.d(TAG, s.toString())
                textViewInfoLabel.text = s
            }
        }
    }
}
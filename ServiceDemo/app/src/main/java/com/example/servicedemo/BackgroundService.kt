package com.example.servicedemo

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class BackgroundService : Service() {
    companion object {
        private val TAG = BackgroundService::class.java.simpleName
    }

    private val scheduledExecutorService = Executors.newScheduledThreadPool(1)

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        //return super.onStartCommand(intent, flags, startId)
        Log.d(TAG, "onStartCommand")
        startTimeService()

        return START_STICKY;
    }

    private fun startTimeService(){
        scheduledExecutorService.scheduleAtFixedRate(
            Runnable {
                Log.d(TAG, "scheduledExecutorService running")
                val intent = Intent(C.intentBackgroundServiceTime)
                intent.putExtra(C.intentBackgroundServiceTimePayload, Date().toString())
                LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(intent)
            },
            0,
            5,
            TimeUnit.SECONDS
        );
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy")
        super.onDestroy()
        scheduledExecutorService.shutdown()
    }

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }
}

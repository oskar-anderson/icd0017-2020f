package com.example.sensordemo

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), SensorEventListener {

    lateinit var sensorManager: SensorManager
    lateinit var accSensor: Sensor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        var sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL)

        textViewSensorList.text = ""

        var i = 0
        sensorList.forEach { sensor ->

            i++
            textViewSensorList.text = textViewSensorList.text.toString() + i.toString() + " - " + sensor.name + "\n"

        }

        accSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    }


    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this, accSensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        textView1.text = event!!.values[0].toString();
        textView2.text = event!!.values[1].toString();
        textView3.text = event!!.values[2].toString();
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // TODO("Not yet implemented")
    }
}

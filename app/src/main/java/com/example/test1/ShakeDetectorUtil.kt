package com.example.test1

import android.content.Context
import android.hardware.SensorManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.squareup.seismic.ShakeDetector

class ShakeDetectorUtil(private val context: Context) {

    private var detector: ShakeDetector? = null
    private var sensorManager: SensorManager? = null

    fun init(listener: ShakeDetector.Listener) {
        if (detector == null) {
            detector = ShakeDetector(listener).apply {
                setSensitivity(ShakeDetector.SENSITIVITY_MEDIUM)
            }
            sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as? SensorManager
        }
        detector
    }

    fun start() {
        detector?.start(sensorManager)
    }

    fun stop() {
        detector?.stop()
    }
}

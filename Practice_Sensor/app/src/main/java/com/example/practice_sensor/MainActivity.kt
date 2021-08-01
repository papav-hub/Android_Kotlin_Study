package com.example.practice_sensor

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var sensorManager: SensorManager
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager


        val deviceSensors: List<Sensor> = sensorManager.getSensorList(Sensor.TYPE_ALL)

        var string = "센서 이름 / 센서 전원 / resolution / range"
        var Mid : MutableList<String> = arrayListOf(string)
        for(i in 0..deviceSensors.size-1){
            var s : Sensor = deviceSensors[i]
            var str = s.name + "/" + s.power + "/" + s.resolution + "/" + s.maximumRange
            Mid.add(str)
        }

        var adapter : ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_list_item_1, Mid)

        listView.adapter = adapter


    }
}

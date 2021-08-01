package com.example.titlesensor

import android.content.Context
import android.content.pm.ActivityInfo
import android.graphics.Canvas
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager

class MainActivity : AppCompatActivity(), SensorEventListener{

    private lateinit var tiltView : TiltView // 늦은 초기화 선언

    override fun onCreate(savedInstanceState: Bundle?) {

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON) // 액티피티 상태 지정 / 화면 항상 켜지도록

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE // 기기 방향 가로로 고정

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tiltView = TiltView(this) // TiltView 초기화
        setContentView(tiltView) // activity_main 대신에 tiltView 화면에 보여줌
    }

    private val sensorManager by lazy{ // 사용하여 sensorManager 변수를 처음 사용할 때 객체 얻기
        getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    override fun onResume(){
        super.onResume() // 사용할 센서 등록
        sensorManager.registerListener(this, // activity에서 센서 값을 받음
            sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), // 센서 종류
            SensorManager.SENSOR_DELAY_NORMAL) // 센서 값 감지 속도
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {// 센서 정밀도가 변경되면 호출
    }

    override fun onSensorChanged(event: SensorEvent?) {// 센서 값이 변경되면 호출, 측정 값과 여러가지 호출
        event?.let{
            Log.d("MainActivity", "onSensorChanged : " +
                "x : ${event.values[0]}, y : ${event.values[1]}, z : ${event.values[2]}")
            tiltView.onSensorEvent(event) // tiltView에 센서 값 전달
        }
    }
    
    override fun onPause(){
        super.onPause()
        sensorManager.unregisterListener(this) // 센서 사용 해체
    }
    // 센서 사용 설정 완료

}
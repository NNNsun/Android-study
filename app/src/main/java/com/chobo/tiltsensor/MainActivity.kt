package com.chobo.tiltsensor

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.content.pm.ActivityInfo
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager

class MainActivity : AppCompatActivity(),SensorEventListener {
    private val sensorManager by lazy{          //SensorManager 준비
        getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }
    //tiltView의 늦은 초기화 선언
    private  lateinit var tiltView: TiltView

    override fun onCreate(savedInstanceState: Bundle?) {
        //화면이 꺼지지 않게 하기
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        //화면이 가로 모드로 고정되게 하기
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tiltView = TiltView(this)
        setContentView(tiltView)
    }
    //sensor 등록
    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this, //registerListener():사용할 센서등록 메서드

            //getDefaultSensor(): 사용할 센서 종류 지정 메서드
            sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),//가속도 센서
            SensorManager.SENSOR_DELAY_NORMAL)  //화면 방향이 전환될 때 적합한 정도로 센서값 얻음


    }

    override fun onSensorChanged(event: SensorEvent?) {
        //센서값이 변경되면 호추로딤
        //values[0]:x축 값: 위로 기울이면 -10~0,아래로 기울이면 0~10
        //values[1]:y축 값: 왼쪽으로 기울이면 -10~0, 오른족으로 기울이면 0~10
        //values[2]:z축 값: 미사용
        event?.let{
            //디버그용 로그를 표시할 때 사용
            //Log.d([태그],[메시지])
            // 태그:로그캣에 많은 내용이 표시되므로 필터링할때 사용,
            // 메세지: 출력할 메세지를 작성
            Log.d(TAG, "onSensorChanged x :"+
                "x : ${event.values[0]}, y: ${event.values[1]}, z: ${event.values[2]}")
            tiltView.onSenorEvent(event)
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }
    override fun onPause(){
        super.onPause()
        sensorManager.unregisterListener(this)
    }


}
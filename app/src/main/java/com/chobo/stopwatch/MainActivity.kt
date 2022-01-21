package com.chobo.stopwatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.chobo.stopwatch.databinding.ActivityMainBinding
import java.util.*
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {
    private var time=0
    private var timerTask: Timer?=null     //timer를 취소하려면 timer를 실행하고 반환되는 Timer객체를
                                           //변수에 저장해야한다. 이를위해 null을 허용하는 Timer타입으로 선언
    private var isRunning=false            //false 상태로 초기화
    private  var lap=1
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.Fab.setOnClickListener {
            isRunning=!isRunning    // FAB가 클릭되면 타이머가 동작 중인지를 저장하는
                                    // isRunning 변수(초기값 : false)를 반전시키고
            if(isRunning){          // 상태에 따라 타이머를 시작 또는 일시정지시킨다.
               start()              //if(isRunning==true)
            }else{
                pause()             //if(isRunning==false)
            }

        }
        binding.lapButton.setOnClickListener {
            recordLapTime()
        }
        binding.resetFab.setOnClickListener {
            reset()
        }
    }
    private fun pause(){
        binding.Fab.setImageResource(R.drawable.ic_baseline_play_arrow_24)   //이미지가 교체됨
        timerTask?.cancel()                                             //실행중인 타이머가 있다면 취소함
    }
    private fun start(){
        binding.Fab.setImageResource(R.drawable.ic_baseline_pause_24)
        timerTask=timer(period = 10){               //0.01초마다
            time++                                  //time 증가
            val sec=time/100
            val milli=time%100
            runOnUiThread{                          //UI 갱신 부분
                binding.secTextView.text="$sec"
                binding.milliTextView.text="$milli"
            }
        }
    }

    private fun recordLapTime(){
        val lapTime = this.time
        val textView=TextView(this)
        textView.text="$lap LAP : ${lapTime/100}.${lapTime%100}"

        //맨 위에 랩타임 추가
        binding.lapLayout.addView(textView,0)
        lap++
    }
    private fun reset(){
        timerTask?.cancel()

        //모든 변수 초기화
        time=0
        isRunning=false
        binding.Fab.setImageResource(R.drawable.ic_baseline_play_arrow_24)
        binding.secTextView.text="0"
        binding.milliTextView.text="00"

        //모든 랩타임을 제거
        binding.lapLayout.removeAllViews()
        lap=1
    }
}
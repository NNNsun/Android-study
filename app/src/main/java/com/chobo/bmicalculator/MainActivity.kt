package com.chobo.bmicalculator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.preference.PreferenceManager
import com.chobo.bmicalculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater) //코틀린 코드에서 레이아웃 객체를 조작하기 위함
    }

    private fun saveData(height: Float, weight: Float) {
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = pref.edit()        //.edit(): 프리퍼먼스에 데이터를 담게 해줌

        editor.putFloat("KEY_HEIGHT", height)
            .putFloat("KEY_WEIGHT", weight)
            .apply()                            //설정한 내용을 반영
    }
    private fun loadData(){
        val pref=PreferenceManager.getDefaultSharedPreferences(this)//프리퍼먼스 객체를 얻는다
        val height=pref.getFloat("KEY_HEIGHT",0f)   //getFloat()->키,몸무게에 저장된 값을 load한다.
        val weight=pref.getFloat("KEY_WEIGHT",0f)

        if(height!=0f&&weight!=0f){//모두 0f인 경우, 즉 저장값이 없는경우 아무것도 하지않는다.

            //저장값이 있다면 키와 몸무게를 입력하는 에디터텍스트에 마지막에 저장된 값을 표시한다.
            binding.heightEditText.setText(height.toString())
            binding.weightEditText.setText(weight.toString())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)                //코틀린 코드에서 레이아웃 객체를 조작하기 위함
        loadData()


        binding.resultButton.setOnClickListener {
            //결과 버튼이 클릭되면 할 일을 작성하는 부분

            //아무 입력 없을 때 에러처리
            val intent = Intent(this, ResultActivity::class.java).apply {
                putExtra("weight", binding.weightEditText.text.toString().toFloat())
                putExtra("height", binding.weightEditText.text.toString().toFloat())
            }
            startActivity(intent)

            //data가 담긴 인텐트에서 data꺼내기
            // (getFloatExtra()사용,문자열꺼냄->나중에 toInt()로  변환해서 계산함)
            val height = intent.getFloatExtra("height", 0f)
            val weight = intent.getFloatExtra("weight", 0f)

        }
        binding.resultButton.setOnClickListener {
            //몸무게와 키를 모두 입력했을 경우 실행
            if(binding.weightEditText.text.isNotBlank()&&binding.heightEditText.text.isNotBlank()){
                //마지막에 입력한 내용 저장
                saveData(
                    binding.heightEditText.text.toString().toFloat(),
                    binding.weightEditText.text.toString().toFloat(),
                )

                val intent=Intent(this,ResultActivity::class.java).apply{
                    putExtra("weight",binding.weightEditText.text.toString().toFloat())
                    putExtra("height",binding.heightEditText.text.toString().toFloat())
                }
                startActivity(intent)


            }
        }
    }
}
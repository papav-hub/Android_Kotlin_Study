package com.example.lottonumgenerator

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_result.*
import java.text.SimpleDateFormat
import java.util.*

class ResultActivity : AppCompatActivity() {

    val lottoImageStartId = R.drawable.ball_01
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        val result = intent.getIntegerArrayListExtra("result")
        val name = intent.getStringExtra("name")
        val constellation = intent.getStringExtra("constellation")
        resultLabel.text = "랜덤으로 생성된\n로또번호입니다"
        if(!TextUtils.isEmpty(name)){
            resultLabel.text = "${name} 님의\n${SimpleDateFormat("yyyy년 MM월 dd일").format(Date())}\n로또번호입니다"
        }
        if(!TextUtils.isEmpty(constellation)){
            resultLabel.text = "${constellation} 의\n${SimpleDateFormat("yyyy년 MM월 dd일").format(Date())}\n로또 번호입니다"
                }
            result?.let {
                updateLottoBallImage(result.sortedBy { it })
            }
    }


    fun updateLottoBallImage(result: List<Int>){
// 결과의 사이즈가 6개 미만인경우 에러가 발생할 수 있으므로 바로 리턴한다.
        if(result.size < 6) return
// ball_01 이미지 부터 순서대로 이미지 아이디가 있기 때문에
// ball_01 아이디에 결과값 -1 을 하면 목표하는 이미지가 된다
// ex) result[0] 이 2번 공인 경우 ball_01 에서 하나뒤에 이미지가 된다.
        imageView01.setImageResource(lottoImageStartId + (result[0] - 1))
        imageView02.setImageResource(lottoImageStartId + (result[1] - 1))
        imageView03.setImageResource(lottoImageStartId + (result[2] - 1))
        imageView04.setImageResource(lottoImageStartId + (result[3] - 1))
        imageView05.setImageResource(lottoImageStartId + (result[4] - 1))
        imageView06.setImageResource(lottoImageStartId + (result[5] - 1))
    }
}
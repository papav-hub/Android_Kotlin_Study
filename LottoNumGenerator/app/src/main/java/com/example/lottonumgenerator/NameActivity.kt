package com.example.lottonumgenerator

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_constellation.*
import kotlinx.android.synthetic.main.activity_name.*
import java.text.SimpleDateFormat
import java.util.*

class NameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_name)
        goButton.setOnClickListener {
            if(TextUtils.isEmpty(editText.text.toString())) {
                Toast.makeText(applicationContext, "이름을 입력하세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val intent = Intent(this, ResultActivity::class.java)
            intent.putIntegerArrayListExtra("result",
                ArrayList(LottoNumberMaker.getLottoNumbersFromHash(editText.text.toString())))
            intent.putExtra("name", editText.text.toString())
            startActivity(intent)
        }
        backButton.setOnClickListener {
            finish()
        }
    }
}
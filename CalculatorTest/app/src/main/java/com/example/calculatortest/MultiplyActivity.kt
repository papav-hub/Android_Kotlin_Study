package com.example.calculatortest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_multiply.*
import org.jetbrains.anko.toast

class MultiplyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_multiply)

        val Text1 = intent.getStringExtra("Text1")?.toInt()
        val Text2 = intent.getStringExtra("Text2")?.toInt()

        var result = Text1!! * Text2!!

        ResultViewMul.setText(result.toString())

        toast("$result")
    }
}
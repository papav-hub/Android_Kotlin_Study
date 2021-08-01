package com.example.a1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Boolean.TRUE

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var Array = arrayOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9")
        var ButtonArray = arrayOf(button00, button11, button22, button33, button44, button55, button66, button77, button88, button99)

        for(i in 0..9){
            ButtonArray[i].setOnClickListener{
                if(edit1.isFocused== true){
                    var a = edit1.text.toString()
                    a += Array[i].toString()
                    edit1.setText(a)
                }else if(edit2.isFocused== true){
                    var b = edit2.text.toString()
                    b += Array[i].toString()
                    edit2.setText(b)
                }
            }
        }

        number1.setOnClickListener(){
            edit1.setText("")
        }
        number2.setOnClickListener(){
            edit2.setText("")
        }
        numberAll.setOnClickListener(){
            edit1.setText("")
            edit2.setText("")
            textView2.setText("")
        }

        PLUS.setOnClickListener{
            var CHECK = check()
            if(CHECK){
                var a = edit1.text.toString().toDouble()
                var b = edit2.text.toString().toDouble()
                loadData()
                saveData(a.toInt(), b.toInt())
                var result = a + b
                textView2.setText("계산 결과 + ${result.toString()}")
            }
        }
        MINUS.setOnClickListener{
            var CHECK = check()
            if(CHECK){
                var a = edit1.text.toString().toDouble()
                var b = edit2.text.toString().toDouble()
                loadData()
                saveData(a.toInt(), b.toInt())
                var result = a - b
                textView2.setText("계산 결과 + ${result.toString()}")
            }
        }
        MULTIPLY.setOnClickListener{
            var CHECK = check()
            if(CHECK){
                var a = edit1.text.toString().toDouble()
                var b = edit2.text.toString().toDouble()
                loadData()
                saveData(a.toInt(), b.toInt())
                var result = a * b
                textView2.setText("계산 결과 + ${result.toString()}")
            }
        }
        DIVIDE.setOnClickListener{
            var CHECK = check()
            if(CHECK){
                var a = edit1.text.toString().toDouble()
                var b = edit2.text.toString().toDouble()
                if(b==0.0){
                    var t1 = Toast.makeText(this, "0으로는 나눌 수 없습니다.", Toast.LENGTH_SHORT).show()
                }else {
                    loadData()
                    saveData(a.toInt(), b.toInt())
                    var result = a / b
                    textView2.setText("계산 결과 + ${result.toString()}")
                }
            }
        }

    }

    private fun loadData(){
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        val Text1 = pref.getInt("KEY_Text1", 1)
        val Text2 = pref.getInt("KEY_Text2", 1)

        if(Text1!=0 && Text2!=0){
            edit1.setText(Text1.toString())
            edit2.setText(Text2.toString())
        }
    }


    private fun saveData(Text1 : Int, Text2 : Int) {
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = pref.edit()

        editor.putInt("KEY_Text1", Text1)
                .putInt("KEY_Text2", Text2)
                .apply()
    }

    fun check() : Boolean{
        var a = edit1.text.toString()
        var b = edit2.text.toString()

        if(a=="" || b==""){
            var t1 = Toast.makeText(this, "숫자를 입력하시오", Toast.LENGTH_SHORT).show()
            return false
        }else{
            return true
        }
    }
}
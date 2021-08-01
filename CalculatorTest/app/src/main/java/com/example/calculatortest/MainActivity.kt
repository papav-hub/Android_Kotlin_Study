package com.example.calculatortest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        PlusButton.setOnClickListener(){
            loadData()
            saveData(Text11.text.toString().toInt(), Text22.text.toString().toInt())

            startActivity<PlusActivity>(
                "Text1" to Text11.text.toString(),
                "Text2" to Text22.text.toString()
            )
        }

        MinusButton.setOnClickListener(){
            loadData()
            saveData(Text11.text.toString().toInt(), Text22.text.toString().toInt())

            startActivity<MinusActivity>(
                "Text1" to Text11.text.toString(),
                "Text2" to Text22.text.toString()
            )
        }

        MulButton.setOnClickListener(){
            loadData()
            saveData(Text11.text.toString().toInt(), Text22.text.toString().toInt())

            startActivity<MultiplyActivity>(
                "Text1" to Text11.text.toString(),
                "Text2" to Text22.text.toString()
            )
        }

        DivideButton.setOnClickListener(){
            loadData()
            saveData(Text11.text.toString().toInt(), Text22.text.toString().toInt())

            startActivity<DivideActivity>(
                "Text1" to Text11.text.toString(),
                "Text2" to Text22.text.toString()
            )
        }
    }

    private fun loadData(){
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        val Text1 = pref.getInt("KEY_Text1", 1)
        val Text2 = pref.getInt("KEY_Text2", 1)

        if(Text1!=0 && Text2!=0){
            Text11.setText(Text1.toString())
            Text22.setText(Text2.toString())
        }
    }


    private fun saveData(Text1 : Int, Text2 : Int) {
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = pref.edit()

        editor.putInt("KEY_Text1", Text1)
            .putInt("KEY_Text2", Text2)
            .apply()
    }
}
package com.example.lottonumgenerator

import android.content.Intent
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        randomCard.setOnClickListener {
            val intent = Intent(this, ResultActivity::class.java)
            intent.putIntegerArrayListExtra("result", ArrayList(LottoNumberMaker.getShuffleLottoNumbers()))
            startActivity(intent)
        }
        constellationCard.setOnClickListener {
            startActivity(Intent(this, ConstellationActivity::class.java))
        }
        nameCard.setOnClickListener {
            startActivity(Intent(this, NameActivity::class.java))
        }
    }

}
package com.example.flashlight

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.intentFor

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val torch = Torch(this) // 작성한 Torch class 인스턴스화

        flashSwitch.setOnCheckedChangeListener{ _, isChecked ->
            if(isChecked){
                //torch.flashOn()
                startService(intentFor<TorchService>().setAction("on"))
            }else{
                //torch.flashOff()
                startService(intentFor<TorchService>().setAction("off"))
            }
        }


    }
}
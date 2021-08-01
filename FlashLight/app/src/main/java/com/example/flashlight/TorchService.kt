package com.example.flashlight

import android.app.Service
import android.content.Intent
import android.os.IBinder

class TorchService : Service() {

    private var isRunning = false // 플래시의 상태가 저장되는 변수

    private val torch : Torch by lazy{
        Torch(this) // Torch class로 플래시 제어
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int { // 외부에서 startService 메서드로 TorchService 호출하면 onStartCommand() 콜백 메서드 호출
        when(intent?.action){
            "on" -> {
                torch.flashOn()
                isRunning = true // 플래시 상태를 켜짐으로 전환
            }
            "off" -> {
                torch.flashOff()
                isRunning = false // 플래시 상태를 꺼짐으로 전환
            }
            else -> {
                isRunning != isRunning
                if(isRunning){
                    torch.flashOn()
                }else{
                    torch.flashOff()
                }
            }
        }
        return super.onStartCommand(intent, flags, startId) // 서비스는 메모리 부족 등의 이유로 강제종료 될 수 있음 // 반환갑에 따라 시스템 강제로 종료한 후에 서비스 다시 시작할 수 있을때 어떻게 할지 결정
    }

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }
}
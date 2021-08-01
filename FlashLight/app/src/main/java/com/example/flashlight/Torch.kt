package com.example.flashlight

import android.content.Context
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager

class Torch(context:Context){
    private var cameraId : String? = null
    private val cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager

    init{
        cameraId = getCameraId()
    }

    private fun getCameraId() : String? {
        val cameraIds = cameraManager.cameraIdList // 기기의 모든 카메라 정보 목록
        for(id in cameraIds){ // 각 ID별로 세부 정보를 가지는 객체를 얻음
            val info = cameraManager.getCameraCharacteristics(id)
            val flashAvailable = info.get(CameraCharacteristics.FLASH_INFO_AVAILABLE) // 플래시 가능 여부
            val LensFacing = info.get(CameraCharacteristics.LENS_FACING) // 렌즈 방향

            if(flashAvailable != null
                    && flashAvailable
                    && LensFacing != null
                    && LensFacing == CameraCharacteristics.LENS_FACING_BACK){
                return id
            }
        }
        return null
    }

    fun flashOn(){
        cameraManager.setTorchMode(cameraId!!, true)
    }
    fun flashOff(){
        cameraManager.setTorchMode(cameraId!!, false)
    }


}
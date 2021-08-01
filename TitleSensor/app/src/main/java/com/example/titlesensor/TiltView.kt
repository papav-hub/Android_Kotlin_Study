package com.example.titlesensor

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.hardware.SensorEvent
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.View
import androidx.core.content.ContextCompat.getSystemService


class TiltView(context: Context?) : View(context) {

    private val greenPaint : Paint = Paint()
    private val blackPaint : Paint = Paint()

    init{
        greenPaint.color = Color.GREEN // 프로퍼티 기본색 초록색으로 지정 (default = black)
        blackPaint.style = Paint.Style.STROKE // style 프로퍼티 속성 "외곽선"으로 설정
    }

    override fun onDraw(canvas: Canvas){
        //canvas?.drawCircle(cX, cY, 100f, blackPaint) // 바깥 원
        var rect = RectF(cX - 50, cY - 50, cX + 50, cY+ 50)
        canvas.drawRect(rect, blackPaint) // 바깥 정 사각형

        //canvas?.drawCircle(cX + xCoord, cY + yCoord, 100f, greenPaint) // 녹색 원
        var rect1 = RectF(cX - 50 + xCoord, cY - 50+ yCoord, cX + 50 + xCoord, cY + 50 + yCoord)
        canvas.drawRect(rect1, greenPaint) // 녹색 원

        canvas?.drawLine(cX-20, cY, cX + 20, cY, blackPaint) // 가운데 십자가
        canvas?.drawLine(cX, cY-20, cX, cY+20, blackPaint) // 가로, 세로 색상


        if(cY + 50 + yCoord < cY - 50){
            val vibrator : Vibrator
            vibrator = this.context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            vibrator.vibrate(1000);
        }else if(cY - 50+ yCoord > cY+ 50){
            val vibrator : Vibrator
            vibrator = this.context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            vibrator.vibrate(1000);
        }else if(cY + 50 + yCoord < cY - 50){
            val vibrator : Vibrator
            vibrator = this.context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            vibrator.vibrate(1000);
        }else if(cX - 50 + xCoord > cX + 50){
            val vibrator : Vibrator
            vibrator = this.context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            vibrator.vibrate(1000);
        }

    }

    private var cX : Float = 100f
    private var cY : Float = 100f

    private var xCoord : Float = 0f
    private var yCoord : Float = 0f

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        cX = w / 2f
        cY = h / 2f
    }

    fun onSensorEvent(event : SensorEvent){ // 화면을 가로로 돌렸으므로 X축과 Y축을 바꿈
        yCoord = event.values[0]*20 // x, y값을 바꿔야 확인하기 편리
        xCoord = event.values[1]*20 // 이벤트 값이 작기 때문에 원의 움직임을 증가시키려 20 곱함



        invalidate() // onDraw를 다시 호출하는 메서드 // 즉, 뷰를 다시 그림
    }


}
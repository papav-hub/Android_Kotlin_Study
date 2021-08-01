package com.example.a2

import android.content.Context
import android.content.pm.ActivityInfo
import android.graphics.*
import android.graphics.drawable.GradientDrawable.LINE
import android.icu.lang.UCharacter.DecompositionType.CIRCLE
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View

class MainActivity : AppCompatActivity() {

    companion object {
        private val RedPaint : Paint = Paint()
        private val GreenPaint : Paint = Paint()
        private val BluePaint : Paint = Paint()
        private val BlackPaint : Paint = Paint()

        var curShape = 1
        var paint = BlackPaint
    }

    init{
        RedPaint.color = Color.RED
        GreenPaint.color = Color.GREEN
        BluePaint.color = Color.BLUE
        BlackPaint.color = Color.BLACK

        RedPaint.style = Paint.Style.STROKE
        GreenPaint.style = Paint.Style.STROKE
        BluePaint.style = Paint.Style.STROKE
        BlackPaint.style = Paint.Style.STROKE

        RedPaint.strokeWidth = 50f
        GreenPaint.strokeWidth = 50f
        BluePaint.strokeWidth = 50f
    }

    private lateinit var tiltView : TiltView // 늦은 초기화 선언

    private class TiltView(context: Context?) : View(context) {

        override fun onDraw(canvas : Canvas){
            super.onDraw(canvas)

            when (curShape) {
                1 -> {
                    canvas?.drawLine(100f, 100f, 200f, 200f, paint)
                }
                2 -> {
                    canvas?.drawCircle(100f, 100f, 100f, paint)
                }
                3 -> {
                    var rect = RectF(100f, 100f, 200f, 200f)
                    canvas.drawRect(rect, paint)
                }
                4 -> {
                    paint = RedPaint
                }
                5 -> {
                    paint = BluePaint
                }
                6 -> {
                    paint = GreenPaint
                }

            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(TiltView(this))

        tiltView = TiltView(this) // TiltView 초기화
        setContentView(tiltView) // activity_main 대신에 tiltView 화면에 보여줌
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
            R.id.Line -> {
                curShape = 1
                tiltView = TiltView(this)
                setContentView(tiltView)
                return true
            }
            R.id.Circle -> {
                curShape = 2
                tiltView = TiltView(this)
                setContentView(tiltView)
                return true
            }
            R.id.Rec -> {
                curShape = 3
                tiltView = TiltView(this)
                setContentView(tiltView)
                return true
            }

            R.id.Red -> {
                curShape = 4
                tiltView = TiltView(this)
                setContentView(tiltView)
                return true
            }
            R.id.Blue -> {
                curShape = 5
                tiltView = TiltView(this)
                setContentView(tiltView)
                return true
            }
            R.id.Green -> {
                curShape = 6
                tiltView = TiltView(this)
                setContentView(tiltView)
                return true
            }

        }
        return super.onOptionsItemSelected(item) // when 문에서 분기하지 않은 예외의 경우에는 super 메서드를 호출하는 것이 안드로이드 시스템의 규칙
    }
}


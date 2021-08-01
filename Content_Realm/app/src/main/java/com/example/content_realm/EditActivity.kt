package com.example.content_realm


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_edit.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.yesButton
import java.util.*

class EditActivity : AppCompatActivity() {

    val realm = Realm.getDefaultInstance()
    val calendar : Calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        val id = intent.getLongExtra("id", -1L)//1:Int, 1L:Long, 1.0:Double, 1.0f:Float
        if (id == -1L) { //초기화 값(기본값)인 -1이 그대로 넘어오면 추가모드
            insertMode()
        } else { //중간에 변경되었으면 수정 모드
            updateMode(id)
        }

        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

        }
    }

    private fun insertMode() {
        deleteFab.visibility = View.GONE // 삭제 버튼을 감추기
//오류 발생시 deleteFab.hide()
        doneFab.setOnClickListener { // 완료 버튼을 클릭하면 추가
            insertTodo()
        }
    }

    private fun updateMode(id: Long) {
// id에 해당하는 객체를 화면에 표시
        val todo = realm.where<Content>().equalTo("id", id).findFirst()!!
        todoEditText.setText(todo.name)
        numberEditText.setText(todo.count.toString())
        locationEditText.setText(todo.location)
        calendarView.date = todo.date
// 완료 버튼을 클릭하면 수정
        doneFab.setOnClickListener {
            updateTodo(id)
        }
// 삭제 버튼을 클릭하면 삭제
        deleteFab.setOnClickListener {
            deleteTodo(id)
        }
    }


    private fun insertTodo(){
        realm.beginTransaction() // Realm에서 데이터 추가, 삭제, 업데이트할때 (DB 작업 단위)
        val todo = realm.createObject<Content>(nextId())
        todo.name = todoEditText.text.toString()
        todo.count = numberEditText.text.toString().toInt()
        todo.location = locationEditText.text.toString()
        todo.date = calendar.timeInMillis
        realm.commitTransaction()

        alert("내용이 추가 되었습니다."){
            yesButton{finish()}
        }.show()
    }

    private fun nextId() : Int {
        val maxId = realm.where<Content>().max("id")
        if(maxId != null){
            return maxId.toInt() + 1
        }
        return 0
    }

    private fun updateTodo(id: Long) {
        realm.beginTransaction() //트렌젝션 시작
        val todo = realm.where<Content>().equalTo("id", id).findFirst()!!//!!:todo는 이후부터 null이 아님
        todo.name = todoEditText.text.toString()
        todo.count = numberEditText.text.toString().toInt()
        todo.location = locationEditText.text.toString()
        todo.date = calendar.timeInMillis
        realm.commitTransaction() //트렌젝션 종료 반영
        alert("내용이 변경되었습니다.") { //다이얼로그 표시
            yesButton { finish() }
        }.show()
    }

    private fun deleteTodo(id: Long) {
        realm.beginTransaction()
        val todo = realm.where<Content>().equalTo("id", id).findFirst()!!
        todo.deleteFromRealm() // deleteFromRealm 메서드로 삭제
        realm.commitTransaction()
        alert("내용이 삭제되었습니다.") {
            yesButton { finish() }
        }.show()
    }


    override fun onDestroy() { // 액티비티가 소멸되는 생명주기에서 렘 데이터베이스 인스턴스 해제
        super.onDestroy()
        realm.close()
    }
}
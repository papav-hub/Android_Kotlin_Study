package com.example.content_realm


import android.os.Bundle
import android.widget.Button
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import io.realm.OrderedRealmCollection
import io.realm.Realm
import io.realm.RealmBaseAdapter
import io.realm.Sort
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.yesButton

class MainActivity : AppCompatActivity() {

    val realm = Realm.getDefaultInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val realmResult = realm.where<Content>().findAll().sort("date", Sort.DESCENDING)

        findViewById<Button>(R.id.addFab).setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        addFab.setOnClickListener {
            startActivity<EditActivity>()
        }


        val adapter = TodoListAdapter(realmResult)
        listView.adapter = adapter
// 데이터가 변경되면 어댑터에 적용
        realmResult.addChangeListener { _ -> adapter.notifyDataSetChanged() }
        listView.setOnItemClickListener { parent, view, position, id ->
            delete.setOnClickListener(){//////////////////
                realm.beginTransaction()
                val todo = realm.where<Content>().equalTo("id", id).findFirst()!!
                todo.deleteFromRealm() // deleteFromRealm 메서드로 삭제
                realm.commitTransaction()
                alert("내용이 삭제되었습니다.") {
                    yesButton {}
                }.show()
            }

            addFab.setOnClickListener(){
                startActivity<EditActivity>("id" to id)
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }

}
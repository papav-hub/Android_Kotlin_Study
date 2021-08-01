package com.example.content_realm


import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/*open class Todo(
    @PrimaryKey var id : Long = 0,
    var title : String = "",
    var date : Long = 0
    ) : RealmObject(){
}*/


open class Content(
    @PrimaryKey var id : Long = 0,
    var date : Long = 0,
    var name : String = "",
    var count : Int = 0,
    var location : String = ""
) : RealmObject(){

}
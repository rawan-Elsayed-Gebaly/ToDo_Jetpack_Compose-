package com.example.data.room_database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task" )
data class Task(
    @PrimaryKey(autoGenerate = true)
     val id :Int= 0 ,
     var title: String? = null  ,
     var description : String?=null  ,
     var date : Long?=null ,
    var isDone :Boolean= false
)

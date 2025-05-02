package com.example.domain.model

data class Tasks(
    val id :Int= 0 ,
    var title: String? = null  ,
    var description : String?=null  ,
    var date : Long?=null ,
    var isDone :Boolean= false
)



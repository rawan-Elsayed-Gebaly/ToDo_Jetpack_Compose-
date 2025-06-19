package com.example.domain.model

data class Tasks(
    val id: Int = 0,
    var title: String,
    var description: String? = null,
    var date: Long? = null,
//    var startTime :Long?=null ,
//    var endDate:Long?=null ,
    var isDone: Boolean = false,
    var priority: String? =null ,

    )



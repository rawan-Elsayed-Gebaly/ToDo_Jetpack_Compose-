package com.example.data.local.database.tasksdb

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.model.Tasks

@Entity(tableName = "task" )
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var title: String,
    var description: String? = null,
    var date: Long? = null,
    var isDone: Boolean = false,
    val priority: String? =null ,
){
    // From Data (Room Entity) to Domain
    fun TaskEntity.toDomain(): Tasks =
        Tasks(
            id,
            title,
            description,
            date,
            isDone,
            priority
        )


    // From Domain to Data
    fun Tasks.toEntity(): TaskEntity =
        TaskEntity(
            id,
            title,
            description,
            date,
            isDone,
            priority
        )

}

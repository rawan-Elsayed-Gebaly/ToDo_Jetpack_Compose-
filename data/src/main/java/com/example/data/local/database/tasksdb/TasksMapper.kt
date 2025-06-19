package com.example.data.local.database.tasksdb

import androidx.core.os.persistableBundleOf
import com.example.domain.model.Tasks



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


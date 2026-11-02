package com.edusutra.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "revisions")
data class Revision(

    @PrimaryKey(autoGenerate = true)
    val revisionId: Int = 0,

    val userId: Int,

    val courseId: Int,

    val topicName: String,

    val reason: String,

    val priority: String = "Medium",

    val isCompleted: Boolean = false,

    val scheduledDate: Long? = null,

    val createdAt: Long = System.currentTimeMillis()
)
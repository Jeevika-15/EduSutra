package com.edusutra.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(

    @PrimaryKey(autoGenerate = true)
    val userId: Int = 0,

    val fullName: String,

    val email: String,

    val passwordHash: String,

    val university: String,

    val course: String,

    val profileImage: String? = null,

    val createdAt: Long = System.currentTimeMillis(),
)

package com.edusutra.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chat_messages")
data class ChatMessage(
    @PrimaryKey(autoGenerate = true)
    val messageId: Int = 0,
    val userId: Int = 1,
    val courseContext: String = "Data Structures · Pro",
    val messageText: String,
    val isFromUser: Boolean,
    val sourceCitation: String? = null,
    val timestamp: Long = System.currentTimeMillis()
)


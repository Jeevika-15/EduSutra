package com.edusutra.app.data.repository

import com.edusutra.app.data.local.dao.ChatMessageDao
import com.edusutra.app.data.local.entity.ChatMessage
import kotlinx.coroutines.flow.Flow

class ChatMessageRepository(
    private val chatMessageDao: ChatMessageDao
) {
    fun getMessages(userId: Int = 1): Flow<List<ChatMessage>> =
        chatMessageDao.getMessages(userId)

    suspend fun saveMessage(message: ChatMessage): Long =
        chatMessageDao.insertMessage(message)

    suspend fun clearChat(userId: Int = 1) =
        chatMessageDao.clearChat(userId)
}


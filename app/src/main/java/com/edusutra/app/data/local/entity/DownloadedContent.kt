package com.edusutra.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "downloaded_contents")
data class DownloadedContent(
    @PrimaryKey(autoGenerate = true)
    val downloadId: Int = 0,
    val lessonId: Int,
    val title: String,
    val fileSize: String,
    val filePath: String,
    val downloadedAt: Long = System.currentTimeMillis()
)


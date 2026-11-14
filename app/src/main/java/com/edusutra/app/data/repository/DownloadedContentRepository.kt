package com.edusutra.app.data.repository

import com.edusutra.app.data.local.dao.DownloadedContentDao
import com.edusutra.app.data.local.entity.DownloadedContent
import kotlinx.coroutines.flow.Flow

class DownloadedContentRepository(
    private val downloadedContentDao: DownloadedContentDao
) {
    fun getAllDownloads(): Flow<List<DownloadedContent>> =
        downloadedContentDao.getAllDownloads()

    suspend fun saveDownload(content: DownloadedContent): Long =
        downloadedContentDao.insertDownload(content)

    suspend fun deleteDownload(downloadId: Int) =
        downloadedContentDao.deleteDownload(downloadId)
}


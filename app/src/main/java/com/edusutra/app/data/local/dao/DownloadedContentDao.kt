package com.edusutra.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.edusutra.app.data.local.entity.DownloadedContent
import kotlinx.coroutines.flow.Flow

@Dao
interface DownloadedContentDao {

    @Query("SELECT * FROM downloaded_contents ORDER BY downloadedAt DESC")
    fun getAllDownloads(): Flow<List<DownloadedContent>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDownload(content: DownloadedContent): Long

    @Query("DELETE FROM downloaded_contents WHERE downloadId = :downloadId")
    suspend fun deleteDownload(downloadId: Int)
}


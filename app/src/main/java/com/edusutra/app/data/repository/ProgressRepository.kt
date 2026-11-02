package com.edusutra.app.data.repository

import com.edusutra.app.data.local.dao.ProgressDao
import com.edusutra.app.data.local.dao.MasteryDao
import com.edusutra.app.data.local.dao.RevisionDao
import com.edusutra.app.data.local.entity.Progress
import com.edusutra.app.data.local.entity.Mastery
import com.edusutra.app.data.local.entity.Revision

class ProgressRepository(
    private val progressDao: ProgressDao,
    private val masteryDao: MasteryDao,
    private val revisionDao: RevisionDao
) {

    fun getProgress(userId: Int) =
        progressDao.getProgressByUser(userId)

    suspend fun saveProgress(progress: Progress) =
        progressDao.insertProgress(progress)

    suspend fun updateProgress(progress: Progress) =
        progressDao.updateProgress(progress)

    fun getMastery(userId: Int) =
        masteryDao.getMasteryByUser(userId)

    fun getWeakTopics() =
        masteryDao.getWeakTopics()

    suspend fun saveMastery(mastery: Mastery) =
        masteryDao.insertMastery(mastery)

    fun getRevisions(userId: Int) =
        revisionDao.getRevisionsByUser(userId)

    suspend fun saveRevision(revision: Revision) =
        revisionDao.insertRevision(revision)

    suspend fun completeRevision(revisionId: Int) =
        revisionDao.completeRevision(revisionId)
}

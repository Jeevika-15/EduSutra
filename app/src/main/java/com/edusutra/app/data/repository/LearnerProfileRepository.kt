package com.edusutra.app.data.repository

import com.edusutra.app.data.local.dao.LearnerProfileDao
import com.edusutra.app.data.local.entity.LearnerProfile
import kotlinx.coroutines.flow.Flow

class LearnerProfileRepository(
    private val learnerProfileDao: LearnerProfileDao
) {
    fun getProfile(userId: Int = 1): Flow<LearnerProfile?> =
        learnerProfileDao.getProfile(userId)

    suspend fun saveProfile(profile: LearnerProfile) =
        learnerProfileDao.insertOrUpdate(profile)

    suspend fun updateProfile(profile: LearnerProfile) =
        learnerProfileDao.update(profile)
}


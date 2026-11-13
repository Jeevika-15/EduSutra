
package com.edusutra.app.data.repository

import com.edusutra.app.data.local.dao.LearningPathDao
import com.edusutra.app.data.local.entity.LearningPath

class LearningPathRepository(
    private val learningPathDao: LearningPathDao
) {
    fun getLearningPath(userId: Int, courseId: Int) =
        learningPathDao.getLearningPath(userId, courseId)

    suspend fun insertStep(step: LearningPath) {
        learningPathDao.insertStep(step)
    }

    suspend fun insertSteps(steps: List<LearningPath>) {
        learningPathDao.insertSteps(steps)
    }

    suspend fun updateStep(step: LearningPath) {
        learningPathDao.updateStep(step)
    }

    suspend fun deleteStep(pathId: Int) {
        learningPathDao.deleteStep(pathId)
    }
}

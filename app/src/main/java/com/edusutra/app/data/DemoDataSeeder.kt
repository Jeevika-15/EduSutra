
package com.edusutra.app.data

import com.edusutra.app.data.repository.ProgressRepository
import com.edusutra.app.data.local.entity.Progress
import com.edusutra.app.data.local.entity.Mastery
import com.edusutra.app.data.local.entity.Revision
import kotlinx.coroutines.flow.first

object DemoDataSeeder {

    suspend fun seedIfEmpty(repository: ProgressRepository) {

        val userId = 1

        val existingProgress = repository.getProgress(userId).first()
        val existingMastery = repository.getMastery(userId).first()
        val existingRevisions = repository.getRevisions(userId).first()

        if (
            existingProgress.isNotEmpty() ||
            existingMastery.isNotEmpty() ||
            existingRevisions.isNotEmpty()
        ) {
            return
        }

        // Sample course progress
        repository.saveProgress(
            Progress(
                userId = userId,
                courseId = 1,
                completedLessons = 8,
                totalLessons = 12,
                completionPercentage = 66.7f,
                totalStudyMinutes = 240
            )
        )

        repository.saveProgress(
            Progress(
                userId = userId,
                courseId = 2,
                completedLessons = 5,
                totalLessons = 10,
                completionPercentage = 50f,
                totalStudyMinutes = 180
            )
        )

        // Sample topic mastery
        repository.saveMastery(
            Mastery(
                userId = userId,
                courseId = 1,
                topicName = "Introduction to Programming",
                masteryScore = 85f,
                questionsAttempted = 20,
                questionsCorrect = 17,
                masteryLevel = "Advanced"
            )
        )

        repository.saveMastery(
            Mastery(
                userId = userId,
                courseId = 1,
                topicName = "Object-Oriented Programming",
                masteryScore = 40f,
                questionsAttempted = 20,
                questionsCorrect = 8,
                masteryLevel = "Beginner"
            )
        )

        // Sample revision tasks
        repository.saveRevision(
            Revision(
                userId = userId,
                courseId = 1,
                topicName = "Object-Oriented Programming",
                reason = "Low mastery score",
                priority = "High"
            )
        )

        repository.saveRevision(
            Revision(
                userId = userId,
                courseId = 2,
                topicName = "Database Normalization",
                reason = "Revision recommended",
                priority = "Medium"
            )
        )
    }
}

package com.edusutra.app.data

import com.edusutra.app.data.local.entity.Progress
import com.edusutra.app.data.local.entity.Mastery
import com.edusutra.app.data.local.entity.Revision
import com.edusutra.app.data.local.entity.Course
import com.edusutra.app.data.local.entity.LearningPath

import com.edusutra.app.data.repository.ProgressRepository
import com.edusutra.app.data.repository.CourseRepository
import com.edusutra.app.data.repository.LearningPathRepository

import kotlinx.coroutines.flow.first

object DemoDataSeeder {

    // Seed Progress, Mastery and Revision data
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

    // Seed sample courses
    suspend fun seedCoursesIfEmpty(repository: CourseRepository) {

        val existingCourses = repository.allCourses.first()

        if (existingCourses.isNotEmpty()) return

        val courses = listOf(

            Course(
                title = "Introduction to Programming",
                description = "Learn programming fundamentals and problem solving.",
                category = "Programming",
                duration = "6 Weeks",
                difficulty = "Beginner",
                instructor = "John Smith"
            ),

            Course(
                title = "Data Structures and Algorithms",
                description = "Understand arrays, linked lists, trees and algorithms.",
                category = "Computer Science",
                duration = "8 Weeks",
                difficulty = "Intermediate",
                instructor = "David Kumar"
            ),

            Course(
                title = "Database Management Systems",
                description = "Learn SQL, database design and normalization.",
                category = "Database",
                duration = "5 Weeks",
                difficulty = "Intermediate",
                instructor = "Sarah Wilson"
            ),

            Course(
                title = "Artificial Intelligence",
                description = "Explore AI concepts and intelligent systems.",
                category = "Artificial Intelligence",
                duration = "7 Weeks",
                difficulty = "Advanced",
                instructor = "Michael Lee"
            ),

            Course(
                title = "Web Development",
                description = "Learn HTML, CSS and JavaScript fundamentals.",
                category = "Web Development",
                duration = "6 Weeks",
                difficulty = "Beginner",
                instructor = "Emily Brown"
            )
        )

        repository.insertCourses(courses)
    }

    // Seed learning path steps for every course
    suspend fun seedLearningPathIfEmpty(
        courseRepository: CourseRepository,
        learningPathRepository: LearningPathRepository
    ) {

        val courses = courseRepository.allCourses.first()
        val userId = 1

        for (course in courses) {

            val existingSteps = learningPathRepository
                .getLearningPath(userId, course.courseId)
                .first()

            if (existingSteps.isNotEmpty()) continue

            val steps = listOf(

                LearningPath(
                    userId = userId,
                    courseId = course.courseId,
                    stepTitle = "Step 1: Introduction",
                    description = "Understand the basics of ${course.title}.",
                    stepOrder = 1
                ),

                LearningPath(
                    userId = userId,
                    courseId = course.courseId,
                    stepTitle = "Step 2: Core Concepts",
                    description = "Learn the important concepts and fundamentals.",
                    stepOrder = 2
                ),

                LearningPath(
                    userId = userId,
                    courseId = course.courseId,
                    stepTitle = "Step 3: Practical Learning",
                    description = "Practice what you have learned with examples.",
                    stepOrder = 3
                ),

                LearningPath(
                    userId = userId,
                    courseId = course.courseId,
                    stepTitle = "Step 4: Final Assessment",
                    description = "Review your knowledge and test your understanding.",
                    stepOrder = 4
                )
            )

            learningPathRepository.insertSteps(steps)
        }
    }
}

package com.edusutra.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "learner_profiles")
data class LearnerProfile(
    @PrimaryKey
    val userId: Int = 1,
    val university: String = "JNTU University - B.Tech CSE",
    val semester: String = "Sem 4",
    val learningGoal: String = "Clear weak concepts",
    val dailyStudyTime: String = "2 hours",
    val contentLanguage: String = "English",
    val streakDays: Int = 12,
    val isPro: Boolean = true,
    val proPlanName: String = "Full Access",
    val renewalDate: String = "12 Nov 2026"
)


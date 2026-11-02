
package com.edusutra.app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

import com.edusutra.app.data.local.entity.User
import com.edusutra.app.data.local.entity.Progress
import com.edusutra.app.data.local.entity.Mastery
import com.edusutra.app.data.local.entity.Revision
import com.edusutra.app.data.local.entity.Course
import com.edusutra.app.data.local.entity.Enrollment
import com.edusutra.app.data.local.entity.LearningPath

import com.edusutra.app.data.local.dao.UserDao
import com.edusutra.app.data.local.dao.ProgressDao
import com.edusutra.app.data.local.dao.MasteryDao
import com.edusutra.app.data.local.dao.RevisionDao
import com.edusutra.app.data.local.dao.CourseDao
import com.edusutra.app.data.local.dao.EnrollmentDao
import com.edusutra.app.data.local.dao.LearningPathDao

@Database(
    entities = [
        User::class,
        Progress::class,
        Mastery::class,
        Revision::class,
        Course::class,
        Enrollment::class,
        LearningPath::class
    ],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun progressDao(): ProgressDao
    abstract fun masteryDao(): MasteryDao
    abstract fun revisionDao(): RevisionDao

    abstract fun courseDao(): CourseDao
    abstract fun enrollmentDao(): EnrollmentDao
    abstract fun learningPathDao(): LearningPathDao
}

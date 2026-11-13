
package com.edusutra.app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

import com.edusutra.app.data.local.entity.User
import com.edusutra.app.data.local.entity.Progress
import com.edusutra.app.data.local.entity.Mastery
import com.edusutra.app.data.local.entity.Revision
import com.edusutra.app.data.local.entity.DiagnosticQuestion
import com.edusutra.app.data.local.entity.QuizQuestion
import com.edusutra.app.data.local.entity.QuizAttempt

import com.edusutra.app.data.local.dao.UserDao
import com.edusutra.app.data.local.dao.ProgressDao
import com.edusutra.app.data.local.dao.MasteryDao
import com.edusutra.app.data.local.dao.RevisionDao
import com.edusutra.app.data.local.dao.DiagnosticQuestionDao
import com.edusutra.app.data.local.dao.QuizQuestionDao
import com.edusutra.app.data.local.dao.QuizAttemptDao

@Database(
    entities = [
        User::class,
        Progress::class,
        Mastery::class,
        Revision::class,
        DiagnosticQuestion::class,
        QuizQuestion::class,
        QuizAttempt::class,
    ],
    version = 1,
    exportSchema = false,
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun progressDao(): ProgressDao

    abstract fun masteryDao(): MasteryDao

    abstract fun revisionDao(): RevisionDao

    abstract fun diagnosticQuestionDao(): DiagnosticQuestionDao

    abstract fun quizQuestionDao(): QuizQuestionDao

    abstract fun quizAttemptDao(): QuizAttemptDao
}

package com.edusutra.app.data.local

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object DatabaseProvider {

    @Volatile
    private var INSTANCE: AppDatabase? = null

    private val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(db: SupportSQLiteDatabase) {
            // Courses module (Madhav)
            db.execSQL("""
                CREATE TABLE IF NOT EXISTS courses (
                    courseId INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                    title TEXT NOT NULL,
                    description TEXT NOT NULL,
                    category TEXT NOT NULL,
                    duration TEXT NOT NULL,
                    difficulty TEXT NOT NULL,
                    instructor TEXT NOT NULL,
                    imageUrl TEXT NOT NULL
                )
            """)

            db.execSQL("""
                CREATE TABLE IF NOT EXISTS enrollments (
                    enrollmentId INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                    userId INTEGER NOT NULL,
                    courseId INTEGER NOT NULL,
                    enrollmentDate INTEGER NOT NULL,
                    status TEXT NOT NULL
                )
            """)

            db.execSQL("""
                CREATE TABLE IF NOT EXISTS learning_paths (
                    pathId INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                    userId INTEGER NOT NULL,
                    courseId INTEGER NOT NULL,
                    stepTitle TEXT NOT NULL,
                    description TEXT NOT NULL,
                    stepOrder INTEGER NOT NULL,
                    isCompleted INTEGER NOT NULL
                )
            """)

            // Quiz module (Bijjam) — exact column names match entity classes
            db.execSQL("""
                CREATE TABLE IF NOT EXISTS diagnostic_questions (
                    id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                    topic TEXT NOT NULL,
                    questionText TEXT NOT NULL,
                    optionA TEXT NOT NULL,
                    optionB TEXT NOT NULL,
                    optionC TEXT NOT NULL,
                    optionD TEXT NOT NULL,
                    correctAnswer TEXT NOT NULL,
                    explanation TEXT NOT NULL DEFAULT ''
                )
            """)

            db.execSQL("""
                CREATE TABLE IF NOT EXISTS quiz_questions (
                    id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                    topic TEXT NOT NULL,
                    difficulty TEXT NOT NULL,
                    questionText TEXT NOT NULL,
                    optionA TEXT NOT NULL,
                    optionB TEXT NOT NULL,
                    optionC TEXT NOT NULL,
                    optionD TEXT NOT NULL,
                    correctAnswer TEXT NOT NULL,
                    explanation TEXT NOT NULL DEFAULT ''
                )
            """)

            // QuizAttempt entity: id, quizType, questionId, selectedAnswer, correctAnswer, isCorrect, attemptedAt
            db.execSQL("""
                CREATE TABLE IF NOT EXISTS quiz_attempts (
                    id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                    quizType TEXT NOT NULL,
                    questionId INTEGER NOT NULL,
                    selectedAnswer TEXT NOT NULL DEFAULT '',
                    correctAnswer TEXT NOT NULL DEFAULT '',
                    isCorrect INTEGER NOT NULL DEFAULT 0,
                    attemptedAt INTEGER NOT NULL
                )
            """)

            // Progress module (Jeevika) — also added in v1→v2
            db.execSQL("""
                CREATE TABLE IF NOT EXISTS progress (
                    progressId INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                    userId INTEGER NOT NULL,
                    courseId INTEGER NOT NULL,
                    completedLessons INTEGER NOT NULL DEFAULT 0,
                    totalLessons INTEGER NOT NULL DEFAULT 0,
                    completionPercentage REAL NOT NULL DEFAULT 0,
                    totalStudyMinutes INTEGER NOT NULL DEFAULT 0,
                    lastUpdated INTEGER NOT NULL
                )
            """)

            db.execSQL("""
                CREATE TABLE IF NOT EXISTS mastery (
                    masteryId INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                    userId INTEGER NOT NULL,
                    courseId INTEGER NOT NULL,
                    topicName TEXT NOT NULL,
                    masteryScore REAL NOT NULL DEFAULT 0,
                    questionsAttempted INTEGER NOT NULL DEFAULT 0,
                    questionsCorrect INTEGER NOT NULL DEFAULT 0,
                    masteryLevel TEXT NOT NULL DEFAULT 'Beginner',
                    lastUpdated INTEGER NOT NULL
                )
            """)

            db.execSQL("""
                CREATE TABLE IF NOT EXISTS revisions (
                    revisionId INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                    userId INTEGER NOT NULL,
                    courseId INTEGER NOT NULL,
                    topicName TEXT NOT NULL,
                    reason TEXT NOT NULL,
                    priority TEXT NOT NULL DEFAULT 'Medium',
                    isCompleted INTEGER NOT NULL DEFAULT 0,
                    scheduledDate INTEGER,
                    createdAt INTEGER NOT NULL
                )
            """)
        }
    }

    private val MIGRATION_2_3 = object : Migration(2, 3) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL("""
                CREATE TABLE IF NOT EXISTS learner_profiles (
                    userId INTEGER PRIMARY KEY NOT NULL,
                    university TEXT NOT NULL,
                    semester TEXT NOT NULL,
                    learningGoal TEXT NOT NULL,
                    dailyStudyTime TEXT NOT NULL,
                    contentLanguage TEXT NOT NULL,
                    streakDays INTEGER NOT NULL,
                    isPro INTEGER NOT NULL,
                    proPlanName TEXT NOT NULL,
                    renewalDate TEXT NOT NULL
                )
            """)

            db.execSQL("""
                CREATE TABLE IF NOT EXISTS syllabus_subjects (
                    subjectId INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                    userId INTEGER NOT NULL,
                    semester TEXT NOT NULL,
                    subjectName TEXT NOT NULL,
                    status TEXT NOT NULL,
                    isIncluded INTEGER NOT NULL
                )
            """)

            db.execSQL("""
                CREATE TABLE IF NOT EXISTS chat_messages (
                    messageId INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                    userId INTEGER NOT NULL,
                    courseContext TEXT NOT NULL,
                    messageText TEXT NOT NULL,
                    isFromUser INTEGER NOT NULL,
                    sourceCitation TEXT,
                    timestamp INTEGER NOT NULL
                )
            """)

            db.execSQL("""
                CREATE TABLE IF NOT EXISTS downloaded_contents (
                    downloadId INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                    lessonId INTEGER NOT NULL,
                    title TEXT NOT NULL,
                    fileSize TEXT NOT NULL,
                    filePath TEXT NOT NULL,
                    downloadedAt INTEGER NOT NULL
                )
            """)
        }
    }

    fun getDatabase(context: Context): AppDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "edusutra_database"
            )
                .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                .fallbackToDestructiveMigration()
                .build()

            INSTANCE = instance
            instance
        }
    }
}

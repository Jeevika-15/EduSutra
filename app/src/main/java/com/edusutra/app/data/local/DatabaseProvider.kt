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
        }
    }

    fun getDatabase(context: Context): AppDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "edusutra_database"
            )
                .addMigrations(MIGRATION_1_2)
                .fallbackToDestructiveMigration()
                .build()

            INSTANCE = instance
            instance
        }
    }
}


package com.edusutra.app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

import com.edusutra.app.data.local.entity.User
import com.edusutra.app.data.local.entity.Progress
import com.edusutra.app.data.local.entity.Mastery
import com.edusutra.app.data.local.entity.Revision

import com.edusutra.app.data.local.dao.UserDao
import com.edusutra.app.data.local.dao.ProgressDao
import com.edusutra.app.data.local.dao.MasteryDao
import com.edusutra.app.data.local.dao.RevisionDao

@Database(
    entities = [
        User::class,
        Progress::class,
        Mastery::class,
        Revision::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun progressDao(): ProgressDao

    abstract fun masteryDao(): MasteryDao

    abstract fun revisionDao(): RevisionDao
}

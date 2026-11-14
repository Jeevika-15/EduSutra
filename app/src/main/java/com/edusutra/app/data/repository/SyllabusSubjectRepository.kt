package com.edusutra.app.data.repository

import com.edusutra.app.data.local.dao.SyllabusSubjectDao
import com.edusutra.app.data.local.entity.SyllabusSubject
import kotlinx.coroutines.flow.Flow

class SyllabusSubjectRepository(
    private val syllabusSubjectDao: SyllabusSubjectDao
) {
    fun getSubjects(userId: Int = 1): Flow<List<SyllabusSubject>> =
        syllabusSubjectDao.getSubjects(userId)

    suspend fun saveSubjects(subjects: List<SyllabusSubject>) =
        syllabusSubjectDao.insertSubjects(subjects)

    suspend fun toggleInclusion(subjectId: Int, isIncluded: Boolean) =
        syllabusSubjectDao.toggleInclusion(subjectId, isIncluded)

    suspend fun updateSubject(subject: SyllabusSubject) =
        syllabusSubjectDao.updateSubject(subject)
}


package com.edusutra.app.data.repository

import com.edusutra.app.data.local.dao.UserDao
import com.edusutra.app.data.local.entity.User

class UserRepository(
    private val userDao: UserDao,
) {

    suspend fun registerUser(user: User): Long {
        return userDao.insertUser(user)
    }

    suspend fun updateUser(user: User) {
        userDao.updateUser(user)
    }

    suspend fun deleteUser(user: User) {
        userDao.deleteUser(user)
    }

    suspend fun loginUser(email: String): User? {
        return userDao.getUserByEmail(email)
    }

    suspend fun getUserById(id: Int): User? {
        return userDao.getUserById(id)
    }

    fun getAllUsers() = userDao.getAllUsers()
}

package com.example.login_page_mvvm.data

class UserRepository(private val userDao: UserDao) {

    suspend fun checkInitialData() {
        if (userDao.getUserCount() == 0) {
            userDao.insertUser(User(username = "admin", password = "12345"))
        }
    }

    suspend fun login(username: String, password: String): User? {
        checkInitialData()
        return userDao.login(username, password)
    }

    suspend fun isUsernameExists(username: String): Boolean {
        checkInitialData()
        return userDao.getUserByUsername(username) != null
    }
}

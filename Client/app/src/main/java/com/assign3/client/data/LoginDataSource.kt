package com.assign3.client.data

import com.assign3.client.data.model.LoggedInUser
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

    fun login(username: String, password: String): Result<LoggedInUser> {
        try {
            val user = LoggedInUser(username, username)
            return Result.Success(user)
        } catch (e: Throwable) {
            return Result.Error(IOException("Error logging in", e))
        }
    }

    fun signup(username: String, password: String): Result<LoggedInUser> {
        try {
            val user = LoggedInUser(username, username)
            return Result.Success(user)
        } catch (e: Throwable) {
            return Result.Error(IOException("Error signing up", e))
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }
}
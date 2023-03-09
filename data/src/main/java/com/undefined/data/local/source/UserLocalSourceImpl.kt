/**
 * Created by Anaskhan on 08/03/23.
 **/

package com.undefined.data.local.source

import android.content.SharedPreferences
import com.undefined.data.local.EntityUser
import javax.inject.Inject

// There may be database.dao or smth else, instead of preference

class UserLocalSourceImpl @Inject constructor(private val preferences: SharedPreferences):
    UserLocalSource {
    override suspend fun saveUser(user: EntityUser) {
        preferences.edit().apply {
            putString("_id", user.id)
            putString("name", user.name)
            putString("email", user.email)
            putString("token", user.token)
            apply()
        }
    }

    override suspend fun getUser(): EntityUser? {
        val id = preferences.getString("_id", null) ?: return null
        return EntityUser(
            id,
            preferences.getString("name", "")!!,
            preferences.getString("email", "")!!,
            preferences.getString("token", "")!!
        )
    }

    override fun isUserExists(): Boolean {
        return preferences.getString("_id", null) != null
    }

    override suspend fun deleteUser() {
        preferences.edit().clear().apply()
    }
}
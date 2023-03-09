/**
 * Created by Anaskhan on 09/03/23.
 **/

package com.undefined.data.sources

import android.content.SharedPreferences
import com.undefined.data.SharedPreferencesMock
import com.undefined.data.local.EntityUser
import com.undefined.data.local.source.UserLocalSource
import com.undefined.data.local.source.UserLocalSourceImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class UserLocalSourceTest {

    private lateinit var preferences: SharedPreferences
    private lateinit var userLocalSource: UserLocalSource

    @BeforeEach
    fun init(){
        preferences = SharedPreferencesMock()
        userLocalSource = UserLocalSourceImpl(preferences)
    }

    @Test
    fun saveUser_Unit_Unit(){
        runTest {
            userLocalSource.saveUser(
                EntityUser(
                    "id",
                    "name",
                    "email",
                    "token"
                )
            )
            assert(preferences.getString("_id", null) == "id"){"id didn't match"}
            assert(preferences.getString("name", null) == "name"){"name didn't match"}
            assert(preferences.getString("email", null) == "email"){"email didn't match"}
            assert(preferences.getString("token", null) == "token"){"token didn't match"}
        }
    }

    @Test
    fun getUser_Unit_EntityUser(){
        runTest {
            userLocalSource.saveUser(
                EntityUser(
                    "id",
                    "name",
                    "email",
                    "token"
                )
            )
            val user = userLocalSource.getUser()
            assert(user != null){"User not found"}
            assert(user!!.id == "id"){"id didn't match"}
            assert(user.name == "name"){"name didn't match"}
            assert(user.email == "email"){"email didn't match"}
            assert(user.token == "token"){"token didn't match"}
        }
    }

    @Test
    fun isExists_Unit_Boolean(){
        runTest {
            userLocalSource.saveUser(
                EntityUser(
                    "id",
                    "name",
                    "email",
                    "token"
                )
            )
            assert(userLocalSource.isUserExists())
        }
    }

    @Test
    fun deleteUser_Unit_Unit(){
        runTest {
            userLocalSource.saveUser(
                EntityUser(
                    "id",
                    "name",
                    "email",
                    "token"
                )
            )
            userLocalSource.deleteUser()
            assert(userLocalSource.getUser() == null)
        }
    }



}
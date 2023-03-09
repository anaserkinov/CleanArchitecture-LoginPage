/**
 * Created by Anaskhan on 09/03/23.
 **/

package com.undefined.data.repositories

import com.undefined.data.SharedPreferencesMock
import com.undefined.data.local.EntityUser
import com.undefined.data.local.source.UserLocalSource
import com.undefined.data.local.source.UserLocalSourceImpl
import com.undefined.data.repository.ActivityRepositoryImpl
import com.undefined.domain.repositories.ActivityRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ActivityRepositoryTest {

    private lateinit var userLocalSource: UserLocalSource
    private lateinit var activityRepository: ActivityRepository

    @BeforeEach
    fun init(){
        userLocalSource = UserLocalSourceImpl(SharedPreferencesMock())
        activityRepository = ActivityRepositoryImpl(userLocalSource)
    }

    @Test
    fun isLoggedIn_UserExists_True(){
        runTest {
            userLocalSource.saveUser(
                EntityUser("id", "name", "email", "token")
            )
            assert(activityRepository.isLoggedIn())
        }
    }

    @Test fun isLoggedIn_UserNotExists_False(){
        runTest {
            userLocalSource.deleteUser()
            assert(!activityRepository.isLoggedIn())
        }
    }


}
/**
 * Created by Anaskhan on 09/03/23.
 **/

package com.undefined.domain

import com.undefined.domain.models.UIUser
import com.undefined.domain.repositories.TestAuthRepository
import com.undefined.domain.repositories.UserLocalSource
import com.undefined.domain.usecases.LogoutUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LogoutUserCaseTest {

    private lateinit var localSource: UserLocalSource
    private lateinit var logoutUseCase: LogoutUseCase

    @Before
    fun init(){
        localSource = UserLocalSource()
        localSource.user = UIUser("id", "name", "email")
        logoutUseCase = LogoutUseCase(TestAuthRepository(localSource))
    }

    @Test
    fun logout_Unit_Success(){
        runTest {
            val state = logoutUseCase.invoke()
            assert(state is UIState.Success){"Logout failed"}
            assert(localSource.user == null){"Logout successfully, but user's credentials wasn't cleared"}
        }
    }

}
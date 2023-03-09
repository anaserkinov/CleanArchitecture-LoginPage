/**
 * Created by Anaskhan on 09/03/23.
 **/

package com.undefined.domain

import com.undefined.domain.repositories.TestAuthRepository
import com.undefined.domain.repositories.UserLocalSource
import com.undefined.domain.usecases.LoginUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LoginUserCaseTest {

    private lateinit var localSource: UserLocalSource
    private lateinit var loginUseCase: LoginUseCase

    @Before
    fun init(){
        localSource = UserLocalSource()
        loginUseCase = LoginUseCase(TestAuthRepository(localSource))
    }

    @Test
    fun login_ValidData_Success(){
        runTest {
            loginUseCase.invoke(
                "email",
                "password"
            )
            assert(localSource.user != null)
            assert(localSource.user?.email == "email")
        }
    }

}
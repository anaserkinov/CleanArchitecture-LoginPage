/**
 * Created by Anaskhan on 09/03/23.
 **/

package com.undefined.domain

import com.undefined.domain.models.UIUser
import com.undefined.domain.repositories.TestAuthRepository
import com.undefined.domain.repositories.UserLocalSource
import com.undefined.domain.usecases.GetUserUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetUserUseCaseTest {

    private lateinit var localSource: UserLocalSource
    private lateinit var getUserUseCase: GetUserUseCase

    @Before
    fun init(){
        localSource = UserLocalSource()
        getUserUseCase = GetUserUseCase(TestAuthRepository(localSource))
    }

    @Test
    fun getUser_isNotLoggedIn_GenericError(){
        runTest {
            localSource.user = null
            val state = getUserUseCase.invoke()
            assert(state is UIState.GenericError)
        }
    }

    @Test
    fun getUser_isNotLoggedIn_Success(){
        runTest {
            localSource.user = UIUser("id", "name", "email")
            val state = getUserUseCase.invoke()
            assert(state is UIState.Success)
        }
    }

}
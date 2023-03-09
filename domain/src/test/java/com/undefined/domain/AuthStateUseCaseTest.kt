/**
 * Created by Anaskhan on 09/03/23.
 **/

package com.undefined.domain

import com.undefined.domain.models.UIUser
import com.undefined.domain.repositories.TestActivityRepository
import com.undefined.domain.repositories.UserLocalSource
import com.undefined.domain.usecases.AuthStateUseCase
import org.junit.Before
import org.junit.Test

class AuthStateUseCaseTest {

    private lateinit var userLocalSource: UserLocalSource
    private lateinit var authStateUseCase: AuthStateUseCase

    @Before
    fun init(){
        userLocalSource = UserLocalSource()
        authStateUseCase = AuthStateUseCase(TestActivityRepository(userLocalSource))
    }

    @Test
    fun isLoggedIn_NotLoggedIn_False(){
        userLocalSource.user = null
        assert(!authStateUseCase.invoke())
    }

    @Test
    fun isLoggedIn_LoggedIn_True(){
        userLocalSource.user = UIUser("id", "name", "email")
        assert(authStateUseCase.invoke())
    }

}
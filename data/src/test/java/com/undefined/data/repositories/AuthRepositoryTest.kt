/**
 * Created by Anaskhan on 09/03/23.
 **/

package com.undefined.data.repositories

import com.undefined.data.SharedPreferencesMock
import com.undefined.data.di.NetworkModule
import com.undefined.data.local.source.UserLocalSource
import com.undefined.data.local.source.UserLocalSourceImpl
import com.undefined.data.mappers.UserMapper
import com.undefined.data.network.api.AuthService
import com.undefined.data.network.source.AuthNetworkSourceImpl
import com.undefined.data.repository.AuthRepositoryImpl
import com.undefined.domain.UIState
import com.undefined.domain.repositories.AuthRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AuthRepositoryTest {

    private lateinit var userLocalSource: UserLocalSource
    private lateinit var authRepository: AuthRepository

    @BeforeEach
    fun init() {
        val preference = SharedPreferencesMock()
        userLocalSource = UserLocalSourceImpl(preference)
        authRepository = AuthRepositoryImpl(
            AuthNetworkSourceImpl(
                NetworkModule().provideRetrofit(preference).create(AuthService::class.java)
            ),
            userLocalSource,
            UserMapper()
        )
    }

    @Test
    fun login_EmptyEmail_ValidationError() {
        runTest {
            val state = authRepository.login("", "12345678")
            assert(state is UIState.ValidationError)
        }
    }

    @Test
    fun login_InvalidEmail_ValidationError() {
        runTest {
            assert(
                authRepository.login("email", "12345678")
                        is UIState.ValidationError
            ){ "email is invalid" }
            assert(
                authRepository.login("email@", "12345678")
                        is UIState.ValidationError
            ){ "email@ is invalid" }
            assert(
                authRepository.login("email@mail", "12345678")
                        is UIState.ValidationError
            ){ "email@mail is invalid" }
            assert(
                authRepository.login("email@mail.", "12345678")
                        is UIState.ValidationError
            ){ "email@mail. is invalid" }
        }
    }

    @Test
    fun login_WrongEmail_GenericError() {
        runTest {
            assert(
                authRepository.login("exae@mail.com", "12345678")
                        is UIState.GenericError
            )
        }
    }

    @Test
    fun login_ValidEmail_Success() {
        runTest {
            assert(
                authRepository.login("example@mail.com", "12345678")
                        is UIState.Success
            )
        }
    }

    @Test
    fun login_EmptyPassword_ValidationError() {
        runTest {
            assert(
                authRepository.login("example@mail.com", "")
                        is UIState.ValidationError
            )
        }
    }

    @Test
    fun login_InvalidPassword_ValidationError() {
        runTest {
            assert(
                authRepository.login("example@mail.com", "1234")
                        is UIState.ValidationError
            ) { "min length should be 8" }
        }

        runTest {
            assert(
                authRepository.login("example@mail.com", "123 45678")
                        is UIState.ValidationError
            ) { "123 45678 is invalid password" }
        }
    }


    @Test
    fun login_WrongPassword_GenericError() {
        runTest {
            assert(
                authRepository.login("examplee@mail.com", "123947265")
                        is UIState.GenericError
            )
        }
    }

    @Test
    fun login_ValidPassword_Success() {
        runTest {
            assert(
                authRepository.login("example@mail.com", "12345678")
                        is UIState.Success
            )
        }
    }

}
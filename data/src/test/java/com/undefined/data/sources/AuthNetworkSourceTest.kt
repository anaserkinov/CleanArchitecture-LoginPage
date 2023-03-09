/**
 * Created by Anaskhan on 09/03/23.
 **/

package com.undefined.data.sources

import com.undefined.data.SharedPreferencesMock
import com.undefined.data.di.NetworkModule
import com.undefined.data.network.api.AuthService
import com.undefined.data.network.requests.RequestLogin
import com.undefined.data.network.source.AuthNetworkSource
import com.undefined.data.network.source.AuthNetworkSourceImpl
import com.undefined.data.utils.error
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import retrofit2.HttpException

@OptIn(ExperimentalCoroutinesApi::class)
class AuthNetworkSourceTest {

    private lateinit var networkSource: AuthNetworkSource

    @BeforeEach
    fun init(){
        networkSource = AuthNetworkSourceImpl(
            NetworkModule().provideRetrofit(SharedPreferencesMock()).create(AuthService::class.java)
        )
    }

    @Test
    fun login_EmptyEmail_Fail(){
        runTest {
            assertThrows<HttpException>{
                networkSource.login(
                    RequestLogin("", "12345678")
                )
            }.let {
                val error = it.error()
                assert(error.fieldName == "email"){"wrong field name"}
            }
        }
    }

    @Test
    fun login_InvalidEmail_Fail(){
        runTest {
            assertThrows<HttpException>(
                { "email is invalid" },
                {
                    networkSource.login(RequestLogin("email", "12345678"))
                }
            ).catchException("email")

            assertThrows<HttpException>(
                { "email@ is invalid" },
                {
                    networkSource.login(RequestLogin("email@", "12345678"))
                }
            ).catchException("email")

            assertThrows<HttpException>(
                { "email@mail is invalid" },
                {
                    networkSource.login(RequestLogin("email@mail", "12345678"))
                }
            ).catchException("email")

            assertThrows<HttpException>(
                { "email@mail. is invalid" },
                {
                    networkSource.login(RequestLogin("email@mail.", "12345678"))
                }
            ).catchException("email")
        }
    }

    @Test
    fun login_WrongEmail_Success(){
        runTest {
            assertThrows<HttpException> {
                networkSource.login(RequestLogin("exae@mail.com", "12345678"))
            }
        }
    }

    @Test
    fun login_ValidEmail_Success(){
        runTest {
            assertDoesNotThrow {
                this.launch {
                    assert(
                        networkSource.login(RequestLogin("example@mail.com", "12345678")).email == "example@mail.com"
                    )
                }
            }
        }
    }

    @Test
    fun login_EmptyPassword_Fail(){
        runTest {
            assertThrows<HttpException> {
                networkSource.login(RequestLogin("example@mail.com", ""))
            }.catchException("password")
        }
    }

    @Test
    fun login_InvalidPassword_Fail(){
        runTest {
            assertThrows<HttpException> (
                { "min length should be 8" },
                {
                    networkSource.login(RequestLogin("example@mail.com", "1234"))
                }
                    ).catchException("password")
        }

        runTest {
            assertThrows<HttpException> (
                { "123 45678 is invalid password" },
                {
                    networkSource.login(RequestLogin("example@mail.com", "123 45678"))
                }
            ).catchException("password")
        }
    }


    @Test
    fun login_WrongPassword_Fail(){
        runTest {
            assertThrows<HttpException> {
                networkSource.login(RequestLogin("examplee@mail.com", "123947265"))
            }
        }
    }

    @Test
    fun login_ValidPassword_Success(){
        runTest {
            assertDoesNotThrow {
                this.launch {
                    assert(
                        networkSource.login(RequestLogin("example@mail.com", "12345678")).email == "example@mail.com"
                    )
                }
            }
        }
    }


    private fun HttpException.catchException(field: String){
        val error = error()
        assert(error.fieldName == field){"wrong field name"}
    }

}
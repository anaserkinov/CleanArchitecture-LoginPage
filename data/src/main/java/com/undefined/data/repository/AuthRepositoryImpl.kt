/**
 * Created by Anaskhan on 08/03/23.
 **/

package com.undefined.data.repository

import com.undefined.data.local.source.UserLocalSource
import com.undefined.data.mappers.UserMapper
import com.undefined.data.network.requests.RequestLogin
import com.undefined.data.network.source.AuthNetworkSource
import com.undefined.data.utils.error
import com.undefined.data.utils.validateEmail
import com.undefined.data.utils.validatePassword
import com.undefined.domain.UIState
import com.undefined.domain.models.UIUser
import com.undefined.domain.repositories.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authNetworkSource: AuthNetworkSource,
    private val userLocalSource: UserLocalSource,
    private val userMapper: UserMapper
) : AuthRepository {

    override suspend fun login(email: String?, password: String?): UIState<Nothing> {
        return try {
            (email.validateEmail() ?: password.validatePassword())?.let { return it }

            val response = authNetworkSource.login(
                RequestLogin(
                    email!!,
                    password!!
                )
            )
            val entityUser = userMapper.mapToEntity(response)
            userLocalSource.saveUser(entityUser)
            UIState.Success()
        } catch (e: Exception) {
            e.error()
        }
    }

    override suspend fun getUser(): UIState<UIUser>{
        return try {
            val entityUser = userLocalSource.getUser()
                ?: return UIState.GenericError(null, "User not found")
            UIState.Success(
                userMapper.mapToUI(entityUser)
            )
        } catch (e: Exception) {
            e.error()
        }
    }

    override suspend fun logout(): UIState<Nothing> {
        return try {
            authNetworkSource.logout()
            userLocalSource.deleteUser()

            UIState.Success()
        } catch (e: Exception) {
            e.error()
        }
    }


}
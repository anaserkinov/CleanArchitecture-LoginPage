/**
 * Created by Anaskhan on 08/03/23.
 **/

package com.undefined.data.network.source

import com.undefined.data.network.api.AuthService
import com.undefined.data.network.requests.RequestLogin
import com.undefined.data.network.responses.UserResponse
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import okio.Buffer
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject

class AuthNetworkSourceImpl @Inject constructor(private val service: AuthService) :
    AuthNetworkSource {

    override suspend fun login(requestLogin: RequestLogin): UserResponse {
        return service.login(requestLogin)
    }

    override suspend fun logout() {
        service.logout()
    }

}
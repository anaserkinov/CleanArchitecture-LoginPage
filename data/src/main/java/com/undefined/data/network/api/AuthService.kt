/**
 * Created by Anaskhan on 08/03/23.
 **/

package com.undefined.data.network.api

import com.undefined.data.network.requests.RequestLogin
import com.undefined.data.network.responses.UserResponse
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * this class do nothing, actual response is generated in [com.undefined.data.utils.Server]
 */

interface AuthService {

    @POST("login")
    suspend fun login(@Body request: RequestLogin): UserResponse

    @POST("login")
    suspend fun logout(@Body request: RequestLogin = RequestLogin("example@mail.com", "12345678"))
}
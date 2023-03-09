/**
 * Created by Anaskhan on 08/03/23.
 **/

package com.undefined.data.network.source

import com.undefined.data.network.requests.RequestLogin
import com.undefined.data.network.responses.UserResponse

interface AuthNetworkSource {
    suspend fun login(requestLogin: RequestLogin): UserResponse
    suspend fun logout()
}
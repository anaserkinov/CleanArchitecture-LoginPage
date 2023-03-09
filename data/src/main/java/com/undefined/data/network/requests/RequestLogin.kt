/**
 * Created by Anaskhan on 08/03/23.
 **/

package com.undefined.data.network.requests

import com.squareup.moshi.Json

class RequestLogin(
    @Json(name = "username") val email: String,
    @Json(name = "password") val password: String
)
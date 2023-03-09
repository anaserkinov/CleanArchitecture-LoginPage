/**
 * Created by Anaskhan on 08/03/23.
 **/

package com.undefined.data.network.responses

import com.squareup.moshi.Json

class UserResponse(
    @Json(name = "_id") val id: String,
    @Json(name = "name") val name: String,
    @Json(name = "email") val email: String,
    @Json(name = "token") val token: String
)
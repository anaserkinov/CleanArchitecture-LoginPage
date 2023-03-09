/**
 * Created by Anaskhan on 08/03/23.
 **/

package com.undefined.data.utils

import com.squareup.moshi.JsonReader
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import okio.Buffer
import java.util.regex.Pattern

// test-case
object Server {

    fun makeResponse(request: Request, response: Response): Response {
        var email = ""
        var password = ""

        val buffer = Buffer()
        request.body!!.writeTo(buffer)
        val model = JsonReader.of(buffer)
        model.beginObject()
        while (model.hasNext()) {
            when(model.nextName()){
                "username" ->
                    email = model.nextString()
                "password" ->
                        password = model.nextString()
                else ->
                    model.skipName()
            }
        }
        model.endObject()
        buffer.clear()

        val builder = response.newBuilder()
            .protocol(Protocol.HTTP_2)

        var code = 400

        val responseString: String = if (email.isBlank()) {
            "{" +
                    "\"data\": \"email\"," +
                    "\"message\": \"Email is required !\"" +
                    "}"
        } else if (!Pattern.compile(
                "[a-zA-Z\\d+._%\\-]{1,256}" +
                        "@" +
                        "[a-zA-Z\\d][a-zA-Z\\d\\-]{0,64}" +
                        "(" +
                        "\\." +
                        "[a-zA-Z\\d][a-zA-Z\\d\\-]{0,25}" +
                        ")+"
            ).matcher(email).matches()
        ) {
            "{" +
                    "\"data\": \"email\"," +
                    "\"message\": \"Email is invalid!\"" +
                    "}"
        } else if (password.isBlank()) {
            "{" +
                    "\"data\": \"password\"," +
                    "\"message\": \"Password is required !\"" +
                    "}"
        } else if (password.contains(' ')) {
            "{" +
                    "\"data\": \"password\"," +
                    "\"message\": \"Password is invalid !\"" +
                    "}"
        } else if (password.length < 8) {
            "{" +
                    "\"data\": \"password\"," +
                    "\"message\": \"Min password length is 8!\"" +
                    "}"
        } else if (email == "example@mail.com" && password == "12345678") {
            code = 200
            "{" +
                    "\"_id\": \"12345\"," +
                    "\"name\": \"UserName\"," +
                    "\"email\": \"example@mail.com\"," +
                    "\"token\": \"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJgaG9uZV9hdW1iZXIiOiIrOTk4OTTyNzAwNTPwIiwiYXBwX3R5cGUiOiJtb2JpbGUiLCJ1c2VybmFtZSI6ImRldiIsImlhdCI6MTY3NzU2ODkyOCwiZXhwIjoxNjc4MTczNzI4fQ.AvrYnBX5Gbj6jANY7iQ-egxTQeTQiqZ1x6R2baQ97DM\"" +
                    "}"
        } else {
            "{\"message\": \"User is not found\"}"
        }



        return builder
            .code(code)
            .message(responseString)
            .body(
                responseString
                    .toResponseBody("application/json".toMediaTypeOrNull())
            )
            .addHeader("content-type", "application/json")
            .build()
    }

}
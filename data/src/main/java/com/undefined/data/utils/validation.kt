/**
 * Created by Anaskhan on 08/03/23.
 **/

package com.undefined.data.utils

import com.undefined.data.R
import com.undefined.domain.UIState
import java.util.regex.Pattern

fun String?.validateEmail(): UIState.ValidationError? {
    return if (isNullOrBlank())
        UIState.ValidationError(R.string.tag_email, R.string.required_field)
    else if (
        !Pattern.compile(
            "[a-zA-Z\\d+._%\\-]{1,256}" +
                    "@" +
                    "[a-zA-Z\\d][a-zA-Z\\d\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z\\d][a-zA-Z\\d\\-]{0,25}" +
                    ")+"
        ).matcher(this).matches()
    )
        UIState.ValidationError(R.string.tag_email, R.string.invalid_input)
    else
        null
}

fun String?.validatePassword(minL: Int = 8): UIState.ValidationError?{
    return if (isNullOrBlank())
        UIState.ValidationError(R.string.tag_password, R.string.required_field)
    else if (contains(' '))
        UIState.ValidationError(R.string.tag_password, R.string.invalid_input)
    else if (length < 6)
        UIState.ValidationError(R.string.tag_password, R.string.min_length_error, minL)
    else
        null
}
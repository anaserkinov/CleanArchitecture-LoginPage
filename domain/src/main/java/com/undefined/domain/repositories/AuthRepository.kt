/**
 * Created by Anaskhan on 08/03/23.
 **/

package com.undefined.domain.repositories

import com.undefined.domain.UIState
import com.undefined.domain.models.UIUser

interface AuthRepository {
    suspend fun login(
        email: String?,
        password: String?
    ): UIState<Nothing>

    suspend fun getUser(): UIState<UIUser>

    suspend fun logout(): UIState<Nothing>
}
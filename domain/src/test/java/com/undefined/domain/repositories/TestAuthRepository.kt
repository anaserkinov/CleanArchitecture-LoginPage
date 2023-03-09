/**
 * Created by Anaskhan on 09/03/23.
 **/

package com.undefined.domain.repositories

import com.undefined.domain.UIState
import com.undefined.domain.models.UIUser

class TestAuthRepository(private val localSource: UserLocalSource): AuthRepository{

    override suspend fun login(email: String?, password: String?): UIState<Nothing> {
        localSource.user = UIUser(
            "id",
            "name",
            email?:""
        )
        return UIState.Success()
    }

    override suspend fun getUser(): UIState<UIUser> {
        return if (localSource.user == null)
            UIState.GenericError(null, "User not found !")
        else
            UIState.Success(localSource.user)
    }

    override suspend fun logout(): UIState<Nothing> {
        localSource.user = null
        return UIState.Success()
    }
}
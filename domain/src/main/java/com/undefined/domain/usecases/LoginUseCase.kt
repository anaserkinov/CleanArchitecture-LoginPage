/**
 * Created by Anaskhan on 08/03/23.
 **/

package com.undefined.domain.usecases

import com.undefined.domain.repositories.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val repository: AuthRepository){
    suspend fun invoke(
        email: String?,
        password: String?
    ) = repository.login(email, password)
}
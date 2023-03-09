/**
 * Created by Anaskhan on 08/03/23.
 **/

package com.undefined.domain.usecases

import com.undefined.domain.repositories.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val repository: AuthRepository){
    suspend fun invoke(
        email: String?,
        password: String?
    ) = withContext(Dispatchers.IO){
        repository.login(email, password)
    }
}
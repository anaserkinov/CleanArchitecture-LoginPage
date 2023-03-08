/**
 * Created by Anaskhan on 09/03/23.
 **/

package com.undefined.domain.usecases

import com.undefined.domain.repositories.AuthRepository
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    suspend fun invoke() = authRepository.getUser()

}
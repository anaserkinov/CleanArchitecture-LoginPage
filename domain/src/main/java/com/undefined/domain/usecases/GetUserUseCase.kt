/**
 * Created by Anaskhan on 09/03/23.
 **/

package com.undefined.domain.usecases

import com.undefined.domain.UIState
import com.undefined.domain.models.UIUser
import com.undefined.domain.repositories.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    suspend fun invoke(): UIState<UIUser> = withContext(Dispatchers.IO){
        authRepository.getUser()
    }

}
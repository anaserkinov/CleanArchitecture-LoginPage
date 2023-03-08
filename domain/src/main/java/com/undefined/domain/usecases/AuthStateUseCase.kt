/**
 * Created by Anaskhan on 08/03/23.
 **/

package com.undefined.domain.usecases

import com.undefined.domain.repositories.ActivityRepository
import javax.inject.Inject

class AuthStateUseCase @Inject constructor(
    private val repository: ActivityRepository
){

    fun invoke() = repository.isLoggedIn()

}
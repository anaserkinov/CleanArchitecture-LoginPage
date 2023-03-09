/**
 * Created by Anaskhan on 08/03/23.
 **/

package com.undefined.data.repository

import com.undefined.data.local.source.UserLocalSource
import com.undefined.domain.repositories.ActivityRepository
import javax.inject.Inject

class ActivityRepositoryImpl @Inject constructor(
    private val userLocalSource: UserLocalSource
): ActivityRepository{
    override fun isLoggedIn(): Boolean {
        return userLocalSource.isUserExists()
    }
}
/**
 * Created by Anaskhan on 09/03/23.
 **/

package com.undefined.domain.repositories

class TestActivityRepository(private val localSource: UserLocalSource): ActivityRepository{

    override fun isLoggedIn(): Boolean {
        return localSource.user != null
    }

}
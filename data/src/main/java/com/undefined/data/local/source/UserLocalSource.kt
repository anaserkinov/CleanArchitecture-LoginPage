/**
 * Created by Anaskhan on 08/03/23.
 **/

package com.undefined.data.local.source

import com.undefined.data.local.EntityUser

interface UserLocalSource {

    suspend fun saveUser(user: EntityUser)
    suspend fun getUser(): EntityUser?
    fun isUserExists(): Boolean
    suspend fun deleteUser()

}
/**
 * Created by Anaskhan on 08/03/23.
 **/

package com.undefined.data.mappers

import com.undefined.data.local.EntityUser
import com.undefined.data.network.responses.UserResponse
import com.undefined.domain.models.UIUser
import javax.inject.Inject

class UserMapper @Inject constructor(){

    fun mapToUI(entityUser: EntityUser) = UIUser(
        entityUser.id,
        entityUser.name,
        entityUser.email
    )

    fun mapToEntity(userResponse: UserResponse) = EntityUser(
        userResponse.id,
        userResponse.name,
        userResponse.email,
        userResponse.token
    )

}
/**
 * Created by Anaskhan on 09/03/23.
 **/

package com.undefined.data

import com.undefined.data.local.EntityUser
import com.undefined.data.mappers.UserMapper
import com.undefined.data.network.responses.UserResponse
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class UserMapperTest {

    private lateinit var userMapper: UserMapper

    @BeforeEach
    fun init(){
        userMapper = UserMapper()
    }

    @Test
    fun mapToUI_Entity_UIModel(){
        val entity = EntityUser(
            "id",
            "name",
            "email",
            "token"
        )
        val uiModel = userMapper.mapToUI(entity)
        assert(entity.id == uiModel.id){"id didn't match"}
        assert(entity.name == uiModel.name){"name didn't match"}
        assert(entity.email == uiModel.email){"email didn't match"}
    }

    @Test
    fun mapToEntity_Response_Entity(){
        val response = UserResponse(
            "id",
            "name",
            "email",
            "token"
        )
        val entity = userMapper.mapToEntity(response)
        assert(response.id == entity.id){"id didn't match"}
        assert(response.name == entity.name){"name didn't match"}
        assert(response.email == entity.email){"email didn't match"}
    }

}
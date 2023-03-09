/**
 * Created by Anaskhan on 08/03/23.
 **/

package com.undefined.appname.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.undefined.appname.core.DisposableLiveData
import com.undefined.domain.UIState
import com.undefined.domain.models.UIUser
import com.undefined.domain.usecases.GetUserUseCase
import com.undefined.domain.usecases.LogoutUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val logoutUseCase: LogoutUseCase
): ViewModel() {

    private val _user = DisposableLiveData<UIState<UIUser>>()
    val user: LiveData<UIState<UIUser>> = _user

    private val _logoutState = DisposableLiveData<UIState<Nothing>>()
    val logoutState: LiveData<UIState<Nothing>> = _logoutState

    fun loadUser(){
        viewModelScope.launch{
            _user.postValue(getUserUseCase.invoke())
        }
    }

    fun logout(){
        viewModelScope.launch{
            _logoutState.postValue(logoutUseCase.invoke())
        }
    }

}
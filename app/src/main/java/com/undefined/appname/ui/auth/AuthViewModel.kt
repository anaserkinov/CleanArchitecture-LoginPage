/**
 * Created by Anaskhan on 08/03/23.
 **/

package com.undefined.appname.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.undefined.appname.core.DisposableLiveData
import com.undefined.domain.UIState
import com.undefined.domain.usecases.LoginUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
): ViewModel(){

    private val _loginState = DisposableLiveData<UIState<Nothing>>()
    val loginState: LiveData<UIState<Nothing>> = _loginState


    fun login(email: String?, password: String?){
        viewModelScope.launch {
            _loginState.postValue(loginUseCase.invoke(email, password))
        }
    }

}
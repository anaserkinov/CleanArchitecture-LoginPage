/**
 * Created by Anaskhan on 08/03/23.
 **/

package com.undefined.appname.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.undefined.appname.core.DisposableLiveData
import com.undefined.domain.usecases.AuthStateUseCase
import javax.inject.Inject

class ActivityViewModel @Inject constructor(
    private val appStateUseCase: AuthStateUseCase
): ViewModel(){

    private val _userLoggedIn = DisposableLiveData<Boolean>()
    val userLoggedIn: LiveData<Boolean> = _userLoggedIn

    fun loadAppState(){
        _userLoggedIn.setValue(appStateUseCase.invoke())
    }

}
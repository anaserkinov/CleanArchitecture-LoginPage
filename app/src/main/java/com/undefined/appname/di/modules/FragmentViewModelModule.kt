/**
 * Created by Anaskhan on 08/03/23.
 **/

package com.undefined.appname.di.modules

import androidx.lifecycle.ViewModel
import com.undefined.appname.di.ViewModelKey
import com.undefined.appname.ui.auth.AuthViewModel
import com.undefined.appname.ui.main.MainViewModel
import com.undefined.data.di.LocalSourceModule
import com.undefined.data.di.NetworkSourceModule
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [NetworkSourceModule::class])
abstract class FragmentViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel::class)
    abstract fun authViewModel(authViewModel: AuthViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun mainViewModel(mainViewModel: MainViewModel) : ViewModel
}
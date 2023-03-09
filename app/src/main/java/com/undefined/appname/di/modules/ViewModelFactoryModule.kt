/**
 * Created by Anaskhan on 08/03/23.
 **/

package com.undefined.appname.di.modules

import androidx.lifecycle.ViewModelProvider
import com.undefined.appname.utils.ViewModelFactory
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class ViewModelFactoryModule {
    @Singleton
    @Binds
    internal abstract fun viewModelFactory(viewModelFactory: ViewModelFactory) : ViewModelProvider.Factory
}
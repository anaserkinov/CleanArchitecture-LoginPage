/**
 * Created by Anaskhan on 08/03/23.
 **/

package com.undefined.appname.di.components

import com.undefined.appname.di.FragmentScope
import com.undefined.appname.di.modules.FragmentViewModelModule
import com.undefined.appname.di.modules.ViewModelFactoryModule
import com.undefined.appname.ui.auth.AuthFragment
import com.undefined.appname.ui.main.MainFragment
import com.undefined.data.di.RepositoryModule
import dagger.Subcomponent

@FragmentScope
@Subcomponent(modules = [FragmentViewModelModule::class, RepositoryModule::class, ViewModelFactoryModule::class])
interface FragmentComponent{
    fun inject(authFragment: AuthFragment)
    fun inject(mainFragment: MainFragment)
}
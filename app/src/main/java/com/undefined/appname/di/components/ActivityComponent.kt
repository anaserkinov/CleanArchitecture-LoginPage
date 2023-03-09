/**
 * Created by Anaskhan on 08/03/23.
 **/

package com.undefined.appname.di.components

import com.undefined.appname.di.ActivityScope
import com.undefined.appname.di.modules.ActivityViewModelModule
import com.undefined.appname.di.modules.ViewModelFactoryModule
import com.undefined.appname.ui.MainActivity
import com.undefined.data.di.RepositoryModule
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [ActivityViewModelModule::class,RepositoryModule::class, ViewModelFactoryModule::class])
interface ActivityComponent {
    fun inject(mainActivity: MainActivity)
}
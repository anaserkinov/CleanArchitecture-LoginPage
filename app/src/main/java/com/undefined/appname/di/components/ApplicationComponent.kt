/**
 * Created by Anaskhan on 08/03/23.
 **/

package com.undefined.appname.di.components

import com.undefined.appname.di.ApplicationScope
import com.undefined.appname.di.modules.ApplicationModule
import com.undefined.data.di.LocalSourceModule
import com.undefined.data.di.NetworkModule
import dagger.Component

@ApplicationScope
@Component(modules = [ApplicationModule::class, LocalSourceModule::class, NetworkModule::class])
interface ApplicationComponent {
    fun activityComponent(): ActivityComponent
    fun fragmentComponent(): FragmentComponent
}
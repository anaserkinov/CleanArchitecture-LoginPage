/**
 * Created by Anaskhan on 08/03/23.
 **/

package com.undefined.appname.di.modules

import androidx.lifecycle.ViewModel
import com.undefined.appname.di.ViewModelKey
import com.undefined.appname.ui.ActivityViewModel
import com.undefined.data.di.LocalSourceModule
import com.undefined.data.di.NetworkSourceModule
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ActivityViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(ActivityViewModel::class)
    internal abstract fun activityViewModel(activityViewModel: ActivityViewModel) : ViewModel
}
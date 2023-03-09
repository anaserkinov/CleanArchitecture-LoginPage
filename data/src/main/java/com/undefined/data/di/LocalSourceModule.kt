/**
 * Created by Anaskhan on 08/03/23.
 **/

package com.undefined.data.di

import com.undefined.data.local.source.UserLocalSource
import com.undefined.data.local.source.UserLocalSourceImpl
import dagger.Binds
import dagger.Module

@Module(includes = [LocalModule::class])
abstract class LocalSourceModule {

    @Binds
    abstract fun provideUserSource(userLocalSourceImpl: UserLocalSourceImpl): UserLocalSource

}
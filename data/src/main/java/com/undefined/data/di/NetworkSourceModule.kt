/**
 * Created by Anaskhan on 08/03/23.
 **/

package com.undefined.data.di

import com.undefined.data.network.source.AuthNetworkSource
import com.undefined.data.network.source.AuthNetworkSourceImpl
import dagger.Binds
import dagger.Module

@Module
abstract class NetworkSourceModule{

    @Binds
    abstract fun provideAuthSource(authNetworkSourceImpl: AuthNetworkSourceImpl): AuthNetworkSource

}
/**
 * Created by Anaskhan on 08/03/23.
 **/

package com.undefined.data.di

import com.undefined.data.repository.ActivityRepositoryImpl
import com.undefined.data.repository.AuthRepositoryImpl
import com.undefined.domain.repositories.ActivityRepository
import com.undefined.domain.repositories.AuthRepository
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {

    @Binds
    abstract fun provideAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Binds
    abstract fun provideActivityRepository(activityRepositoryImpl: ActivityRepositoryImpl): ActivityRepository

}
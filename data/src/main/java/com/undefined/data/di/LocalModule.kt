/**
 * Created by Anaskhan on 08/03/23.
 **/

package com.undefined.data.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides

import javax.inject.Singleton


@Module
class LocalModule {

    @Provides
    fun provideSharedPreference(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
    }

}
/**
 * Created by Anaskhan on 08/03/23.
 **/

package com.undefined.appname.di.modules

import android.content.Context
import com.undefined.appname.Application
import com.undefined.appname.di.ApplicationScope
import com.undefined.data.di.ApplicationContext
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule(private val application: Application){

        @Provides
        @ApplicationScope
        @ApplicationContext
        fun applicationContext(): Context {
            return application.applicationContext
        }
}
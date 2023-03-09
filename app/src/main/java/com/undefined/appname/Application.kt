/**
 * Created by Anaskhan on 08/03/23.
 **/

package com.undefined.appname

import com.undefined.appname.di.components.ApplicationComponent
import com.undefined.appname.di.components.DaggerApplicationComponent
import com.undefined.appname.di.modules.ApplicationModule
import com.undefined.appname.utils.AndroidUtilities

class Application: android.app.Application() {

    lateinit var applicationComponent: ApplicationComponent
        private set

    override fun onCreate() {
        super.onCreate()

        applicationComponent = DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this))
            .build()

        AndroidUtilities.checkDisplaySize(this, null)
    }

}
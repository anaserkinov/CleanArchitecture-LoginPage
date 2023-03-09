/**
 * Created by Anaskhan on 09/03/23.
 **/

package com.undefined.appname.utils

import android.content.Context
import android.content.res.Configuration

object AndroidUtilities {

    var density = 1f

    fun checkDisplaySize(context: Context, newConfiguration: Configuration?) {
        try {
//            val oldDensity: Float = density
            density = context.resources.displayMetrics.density
//            val newDensity: Float = density
//            if (firstConfigurationWas && abs(oldDensity - newDensity) > 0.001) {
//                Theme.reloadAllResources(context)
//            }
//            firstConfigurationWas = true
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }



}
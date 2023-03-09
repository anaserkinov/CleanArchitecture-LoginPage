/**
 * Created by Anaskhan on 09/03/23.
 **/

package com.undefined.appname.core

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity: AppCompatActivity(){

    private lateinit var loadingDialog: LoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadingDialog = LoadingDialog(this)
    }

    fun showLoading(force: Boolean){
        loadingDialog.showDialog(force)
    }

    fun hideLoading(force: Boolean){
        loadingDialog.hideDialog(force)
    }

}
package com.undefined.appname.ui

import android.os.Bundle
import android.widget.FrameLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.undefined.appname.Application
import com.undefined.appname.R
import com.undefined.appname.core.BaseActivity
import com.undefined.appname.ui.auth.AuthFragment
import com.undefined.appname.ui.main.MainFragment
import com.undefined.appname.utils.ViewModelFactory
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel by viewModels<ActivityViewModel>(factoryProducer = {
        viewModelFactory
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (applicationContext as Application).applicationComponent
            .activityComponent().inject(this)

        val frameLayout = FrameLayout(this)
        frameLayout.id = R.id.frame_layout
        setContentView(frameLayout)

        viewModel.userLoggedIn.observe(this) {
            supportFragmentManager
                .beginTransaction()
                .add(
                    R.id.frame_layout,
                    if (it)
                        MainFragment()
                    else
                        AuthFragment()
                )
                .commit()
        }

        viewModel.loadAppState()
    }

}
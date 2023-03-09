/**
 * Created by Anaskhan on 08/03/23.
 **/

package com.undefined.appname.ui.main

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.button.MaterialButton
import com.undefined.appname.Application
import com.undefined.appname.R
import com.undefined.appname.core.BaseFragment
import com.undefined.appname.ui.auth.AuthFragment
import com.undefined.appname.utils.MATCH_PARENT
import com.undefined.appname.utils.ViewModelFactory
import com.undefined.appname.utils.dp
import com.undefined.appname.utils.frameLayoutParams
import com.undefined.domain.UIState
import com.undefined.domain.models.UIUser
import javax.inject.Inject

class MainFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel by viewModels<MainViewModel>(factoryProducer = {
        viewModelFactory
    })

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as Application).applicationComponent.fragmentComponent().inject(this)
    }

    private lateinit var detailText: TextView
    private lateinit var button: MaterialButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val frameLayout = FrameLayout(requireContext())
        frameLayout.setBackgroundColor(Color.WHITE)

        detailText = TextView(requireContext())
        detailText.id = R.id.detail_text
        detailText.setTypeface(null, Typeface.BOLD)
        detailText.setTextColor(Color.BLACK)
        frameLayout.addView(detailText, frameLayoutParams(gravity = Gravity.CENTER))

        button = MaterialButton(requireContext())
        button.id = R.id.logout_button
        button.setText(R.string.logout)
        frameLayout.addView(button, frameLayoutParams(MATCH_PARENT, gravity = Gravity.BOTTOM, marginLeft = dp(16), marginRight = dp(16)))

        return frameLayout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        button.setOnClickListener {
            it.isEnabled = false
            showLoading(false)

            viewModel.logout()
        }

        viewModel.loadUser()
    }

    override fun onResume() {
        super.onResume()

        viewModel.user.observe(viewLifecycleOwner, userObserver)
        viewModel.logoutState.observe(viewLifecycleOwner, logoutStateObserver)
    }

    private val userObserver = Observer<UIState<UIUser>> {
        if (it is UIState.Success) {
            val user = it.data!!
            detailText.setText(
                "${user.id} \n ${user.name} \n ${user.email}"
            )
        } else
            snackBar(it)
    }

    private val logoutStateObserver = Observer<UIState<Nothing>> {
        button.isEnabled = true
        hideLoading(true)
        if (it is UIState.Success) {
            parentFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, AuthFragment())
                .commit()
        } else
            snackBar(it)
    }


}
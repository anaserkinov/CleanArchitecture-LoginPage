/**
 * Created by Anaskhan on 08/03/23.
 **/

package com.undefined.appname.ui.auth

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.InputType
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.button.MaterialButton
import com.undefined.appname.Application
import com.undefined.appname.R
import com.undefined.appname.core.BaseFragment
import com.undefined.appname.core.OutlineEditText
import com.undefined.appname.ui.main.MainFragment
import com.undefined.appname.utils.*
import com.undefined.domain.UIState
import javax.inject.Inject

class AuthFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel by viewModels<AuthViewModel>(factoryProducer = {
        viewModelFactory
    })

    private lateinit var emailText: OutlineEditText
    private lateinit var passwordText: OutlineEditText
    private lateinit var button: MaterialButton


    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as Application).applicationComponent.fragmentComponent().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val frameLayout = FrameLayout(requireContext())
        frameLayout.setBackgroundColor(Color.WHITE)

        val linearLayout = LinearLayout(requireContext())
        linearLayout.orientation = LinearLayout.VERTICAL
        frameLayout.addView(linearLayout, frameLayoutParams(MATCH_PARENT, gravity = Gravity.CENTER))

        emailText = OutlineEditText(requireContext())
        emailText.id = R.id.email_text
        emailText.tag = getString(com.undefined.data.R.string.tag_email)
        emailText.setHint(R.string.email)
        emailText.editText.inputType =
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        emailText.editText.imeOptions = EditorInfo.IME_ACTION_NEXT
        linearLayout.addView(
            emailText,
            linearLayoutParams(MATCH_PARENT, marginLeft = dp(24), marginRight = dp(24))
        )

        passwordText = OutlineEditText(requireContext())
        passwordText.id = R.id.password_text
        passwordText.setHint(R.string.password)
        passwordText.editText.inputType =
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        passwordText.tag = getString(com.undefined.data.R.string.tag_password)
        emailText.editText.imeOptions = EditorInfo.IME_ACTION_DONE
        linearLayout.addView(
            passwordText,
            linearLayoutParams(MATCH_PARENT, marginTop = dp(8), marginLeft = dp(24), marginRight = dp(24))
        )

        button = MaterialButton(requireContext())
        button.id = R.id.login_button
        button.setText(R.string.login)
        frameLayout.addView(
            button,
            frameLayoutParams(
                MATCH_PARENT,
                gravity = Gravity.BOTTOM,
                marginLeft = dp(16),
                marginRight = dp(16)
            )
        )

        return frameLayout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        emailText.clearErrorOnChange()
        passwordText.clearErrorOnChange()

        button.setOnClickListener {
            button.isEnabled = false
            showLoading(false)
            viewModel.login(
                emailText.editText.text.toString(),
                passwordText.editText.text.toString()
            )
        }
    }

    override fun onResume() {
        super.onResume()

        viewModel.loginState.observe(viewLifecycleOwner, logInStateObserver)
    }

    private val logInStateObserver = Observer<UIState<Nothing>> {
        hideLoading(true)
        button.isEnabled = true
        if (it is UIState.Success) {
            parentFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, MainFragment())
                .commit()
        } else
            inError(it)
    }

}
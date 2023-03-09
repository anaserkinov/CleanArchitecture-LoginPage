/**
 * Created by Anaskhan on 08/03/23.
 **/

package com.undefined.appname.core

import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.undefined.domain.UIState

open class BaseFragment : Fragment() {

    protected fun showLoading(force: Boolean) {
        (activity as? BaseActivity)?.showLoading(force)
    }

    protected fun hideLoading(force: Boolean) {
        (activity as? BaseActivity)?.hideLoading(force)
    }

    protected fun <T> inError(error: UIState<T>) {
        if (error is UIState.GenericError) {
            if (error.message == null)
                return
            view?.findViewWithTag<OutlineEditText>(error.fieldName)?.showError(error.message)
                ?: Snackbar.make(requireView(), error.message!!, Snackbar.LENGTH_SHORT).show()
        } else if (error is UIState.ValidationError) {
            view?.findViewWithTag<OutlineEditText>(getString(error.fieldName))
                ?.showError(error.message, error.value)
                ?: Snackbar.make(requireView(), error.message, Snackbar.LENGTH_SHORT).show()
        }
    }

    protected fun <T> snackBar(error: UIState<T>) {
        if (error is UIState.GenericError) {
            if (error.message == null)
                return
            Snackbar.make(requireView(), error.message!!, Snackbar.LENGTH_SHORT).show()
        } else if (error is UIState.ValidationError) {
            Snackbar.make(requireView(), error.message, Snackbar.LENGTH_SHORT).show()
        }
    }

}
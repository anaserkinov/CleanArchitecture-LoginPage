package com.undefined.appname.core

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper


// Created by Anaskhan on 6/28/2021.

class LoadingDialog(context: Context) : Dialog(context, false, null) {

    private val MIN_SHOW_TIME = 500 // ms

    private val MIN_DELAY = 500 // ms

    var mStartTime: Long = -1
    var mPostedHide = false
    var mPostedShow = false
    var mDismissed = false

    private val handler = Handler(Looper.getMainLooper())

    private val mDelayedHide = Runnable {
        mPostedHide = false
        mStartTime = -1
        dismiss()
    }

    private val mDelayedShow = Runnable {
        mPostedShow = false
        if (!mDismissed) {
            mStartTime = System.currentTimeMillis()
            show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        setContentView(LoadingView(context))
    }


    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        removeCallbacks()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        removeCallbacks()
    }

    private fun removeCallbacks() {
        handler.removeCallbacks(mDelayedHide)
        handler.removeCallbacks(mDelayedShow)
    }

    fun hideDialog(force: Boolean) {
        synchronized(this){
            mDismissed = true
            handler.removeCallbacks(mDelayedShow)
            mPostedShow = false
            val diff = System.currentTimeMillis() - mStartTime
            if (diff >= MIN_SHOW_TIME || mStartTime == -1L) {
                dismiss()
            } else {
                if (mPostedHide) {
                    if (force) {
                        handler.removeCallbacks(mDelayedHide)
                        mPostedHide = false
                        hide()
                    }
                } else if (force)
                    hide()
                else {
                    handler.postDelayed(mDelayedHide, MIN_SHOW_TIME - diff)
                    mPostedHide = true
                }
            }
        }
    }


    fun showDialog(force: Boolean) {
        synchronized(this) {
            mStartTime = -1
            mDismissed = false
            handler.removeCallbacks(mDelayedHide)
            mPostedHide = false
            if (mPostedShow) {
                if (force) {
                    handler.removeCallbacks(mDelayedShow)
                    mPostedShow = false
                    show()
                }
            } else if (force)
                show()
            else {
                handler.postDelayed(mDelayedShow, MIN_DELAY.toLong())
                mPostedShow = true
            }
        }
    }

}

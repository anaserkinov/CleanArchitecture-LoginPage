package com.undefined.appname.core

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.widget.FrameLayout
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import com.undefined.appname.R
import com.undefined.appname.utils.dp
import com.undefined.appname.utils.frameLayoutParams
import com.undefined.appname.utils.linearLayoutParams


// Created by Anaskhan on 7/2/2021.

class LoadingView(context: Context) : FrameLayout(context) {

    init {

        background = GradientDrawable(
            GradientDrawable.Orientation.BOTTOM_TOP,
            intArrayOf(Color.argb(180, 255, 255, 255), Color.argb(180, 255, 255, 255))
        ).also {
            it.cornerRadius = dp(24f)
        }

        val frameLayout = FrameLayout(context)
        frameLayout.layoutParams = linearLayoutParams(dp(70), dp(70), gravity = Gravity.CENTER)

        val lottieView = LottieAnimationView(context)
        lottieView.setAnimation(R.raw.loading)
        lottieView.repeatMode = LottieDrawable.RESTART
        lottieView.repeatCount = LottieDrawable.INFINITE
        lottieView.setColorFilter(R.color.colorPrimary)
        lottieView.layoutParams = frameLayoutParams(dp(42), dp(42), Gravity.CENTER)
        lottieView.playAnimation()
        frameLayout.addView(lottieView)

//        val progressBar = ProgressBar(context)
//        progressBar.layoutParams = LayoutParams(dp(70),dp(70),Gravity.CENTER)
//        progressBar.setPadding(size_medium)

        addView(frameLayout)
    }

}
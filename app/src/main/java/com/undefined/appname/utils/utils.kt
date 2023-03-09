/**
 * Created by Anaskhan on 09/03/23.
 **/

package com.undefined.appname.utils

import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.core.view.setMargins
import kotlin.math.ceil

const val MATCH_PARENT = -1
const val WRAP_CONTENT = -2

fun dp(value: Int) =
    if (value == 0)
        0
    else
        ceil(AndroidUtilities.density * value).toInt()

fun dp(value: Float) =
    if (value == 0F)
        0F
    else
        ceil(AndroidUtilities.density * value)

val measureSpec_unspecified = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
fun measureSpec_at_most(atMost: Int) =
    View.MeasureSpec.makeMeasureSpec(atMost, View.MeasureSpec.AT_MOST)

fun measureSpec_exactly(exactly: Int) =
    View.MeasureSpec.makeMeasureSpec(exactly, View.MeasureSpec.EXACTLY)


fun frameLayoutParams(
    width: Int = WRAP_CONTENT,
    height: Int = WRAP_CONTENT
) = FrameLayout.LayoutParams(width, height)

fun frameLayoutParams(
    width: Int = WRAP_CONTENT,
    height: Int = WRAP_CONTENT,
    gravity: Int
) = FrameLayout.LayoutParams(width, height, gravity)

fun frameLayoutParams(
    width: Int = WRAP_CONTENT,
    height: Int = WRAP_CONTENT,
    gravity: Int = Gravity.CENTER,
    margin: Int = 0
): FrameLayout.LayoutParams {
    val frameLayoutParams = FrameLayout.LayoutParams(width, height, gravity)
    frameLayoutParams.setMargins(margin, margin, margin, margin)
    return frameLayoutParams
}

fun frameLayoutParams(
    width: Int = WRAP_CONTENT,
    height: Int = WRAP_CONTENT,
    gravity: Int = Gravity.CENTER,
    marginLeft: Int = 0,
    marginTop: Int = 0,
    marginRight: Int = 0,
    marginBottom: Int = 0,
): FrameLayout.LayoutParams {
    val frameLayoutParams = FrameLayout.LayoutParams(width, height, gravity)
    frameLayoutParams.setMargins(marginLeft, marginTop, marginRight, marginBottom)
    return frameLayoutParams
}

fun linearLayoutParams(
    width: Int = WRAP_CONTENT,
    height: Int = WRAP_CONTENT,
    weight: Float = 0F,
): LinearLayout.LayoutParams = LinearLayout.LayoutParams(width, height, weight)

fun linearLayoutParams(
    width: Int = WRAP_CONTENT,
    height: Int = WRAP_CONTENT,
    weight: Float = 0F,
    gravity: Int = Gravity.START,
    margin: Int = 0
): LinearLayout.LayoutParams {
    val layoutParams = LinearLayout.LayoutParams(width, height, weight)
    layoutParams.gravity = gravity
    layoutParams.setMargins(margin)

    return layoutParams
}

fun linearLayoutParams(
    width: Int = WRAP_CONTENT,
    height: Int = WRAP_CONTENT,
    weight: Float = 0F,
    marginLeft: Int = 0,
    marginTop: Int = 0,
    marginRight: Int = 0,
    marginBottom: Int = 0,
    gravity: Int = Gravity.START
): LinearLayout.LayoutParams {
    val layoutParams = LinearLayout.LayoutParams(width, height, weight)
    layoutParams.setMargins(
        marginLeft,
        marginTop,
        marginRight,
        marginBottom
    )
    layoutParams.gravity = gravity

    return layoutParams
}
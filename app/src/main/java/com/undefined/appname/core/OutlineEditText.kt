package com.undefined.appname.core

import android.content.Context
import android.graphics.*
import android.os.Build
import android.text.*
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import com.undefined.appname.R
import com.undefined.appname.utils.dp
import com.undefined.appname.utils.measureSpec_at_most
import com.undefined.appname.utils.measureSpec_exactly
import com.undefined.appname.utils.measureSpec_unspecified

class OutlineEditText(context: Context) : ViewGroup(context) {

    companion object {
        const val PADDING_LEFT = 14
        const val PADDING_TEXT = 4
        const val SPRING_MULTIPLIER = 100f

        private val SELECTION_PROGRESS_PROPERTY = SimpleFloatPropertyCompat(
            "selectionProgress",
            { obj: OutlineEditText -> obj.selectionProgress }) { obj: OutlineEditText, value: Float ->
            obj.selectionProgress = value
            if (!obj.forceUseCenter) {
                obj.outlinePaint.strokeWidth =
                    obj.strokeWidthRegular + (obj.strokeWidthSelected - obj.strokeWidthRegular) * obj.selectionProgress
                obj.updateColor()
            }
            obj.invalidate()
        }.setMultiplier(SPRING_MULTIPLIER)
//        private val ERROR_PROGRESS_PROPERTY = SimpleFloatPropertyCompat(
//            "errorProgress",
//            { obj: OutlineEditText -> obj.errorProgress }) { obj: OutlineEditText, value: Float ->
//            obj.errorProgress = value
//            obj.updateColor()
//        }.setMultiplier(SPRING_MULTIPLIER)

        const val FLAG_STATIC = 0b1
        const val FLAG_MONEY = 0b10
        const val FLAG_PHONE = 0b100
    }

    private val rect = RectF()
    private var mText = ""
    private val outlinePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val textPaint = TextPaint(Paint.ANTI_ALIAS_FLAG)
    private val selectionSpring = SpringAnimation(this, SELECTION_PROGRESS_PROPERTY)
    private var selectionProgress = 0f

    //    private val errorSpring = SpringAnimation(this, ERROR_PROGRESS_PROPERTY)
    private var errorProgress = 0f
    private val strokeWidthRegular = 2f.coerceAtLeast(dp(0.5f))
    private val strokeWidthSelected = dp(1.5f)
    val editText = EditText(context)

    private var errorText: TextView? = null
    private var mEndIcon: ImageView? = null

    val endIcon: ImageView
        get() = mEndIcon!!

    private var clearErrorOnChange = false
    private var listeningTextChanges = false


    val text: String = editText.text.toString()

    init {
        setWillNotDraw(false)

        editText.background = null
        editText.setTextColor(Color.BLACK)
        editText.setOnFocusChangeListener { v, hasFocus -> animateSelection(if (hasFocus) 1f else 0f) }
        addView(editText)

        textPaint.textSize = dp(16).toFloat()
        outlinePaint.style = Paint.Style.STROKE
        outlinePaint.strokeCap = Paint.Cap.ROUND
        outlinePaint.strokeWidth = strokeWidthRegular
        updateColor()

        setPadding(0, dp(6), 0, 0)
    }

    private var forceUseCenter = false

    fun setForceUseCenter(forceUseCenter: Boolean) {
        this.forceUseCenter = forceUseCenter
        invalidate()
    }

    fun setHint(text: Int) = setHint(context.getString(text))

    fun setHint(text: String) {
        mText = text
        invalidate()
    }

    fun setText(text: String?, animate: Boolean = true) {
        editText.setText(text)
        animateSelection(if (editText.hasFocus()) 1f else 0f, animate)
    }

    fun setText(text: Int, animate: Boolean = true) {
        editText.setText(text)
        animateSelection(if (editText.hasFocus()) 1f else 0f, animate)
    }

    private fun setColor(color: Int) {
        outlinePaint.color = color
        invalidate()
    }

    fun updateColor() {
        val color = if (isEnabled)
            -0x79797a
        else
            -0x3C3C3D
        val textSelectionColor =
            ColorUtils.blendARGB(color, ContextCompat.getColor(context, R.color.colorPrimary), selectionProgress)
        textPaint.color = ColorUtils.blendARGB(
            textSelectionColor,
            Color.RED,
            errorProgress
        )
        val selectionColor =
            ColorUtils.blendARGB(color, ContextCompat.getColor(context, R.color.colorPrimary), selectionProgress)
        setColor(ColorUtils.blendARGB(selectionColor, Color.RED, errorProgress))
    }

    @JvmOverloads
    fun animateSelection(newValue: Float, animate: Boolean = true) {
        if (!animate) {
            selectionProgress = newValue
            if (!forceUseCenter) {
                outlinePaint.strokeWidth =
                    strokeWidthRegular + (strokeWidthSelected - strokeWidthRegular) * selectionProgress
            }
            updateColor()
            return
        }
        animateSpring(selectionSpring, newValue)
    }

//    fun animateError(newValue: Float) {
//        animateSpring(errorSpring, newValue)
//    }


    private fun animateSpring(spring: SpringAnimation, newValue: Float) {
        var newValue = newValue
        newValue *= SPRING_MULTIPLIER
        if (spring.spring != null && newValue == spring.spring
                .finalPosition
        ) return
        spring.cancel()
        spring.setSpring(
            SpringForce(newValue)
                .setStiffness(500f)
                .setDampingRatio(SpringForce.DAMPING_RATIO_NO_BOUNCY)
                .setFinalPosition(newValue)
        )
            .start()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = MeasureSpec.getSize(widthMeasureSpec)

        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)

        mEndIcon?.measure(
            measureSpec_unspecified,
            measureSpec_unspecified
        )

        editText.measure(
            measureSpec_exactly(width - dp(8) * 3 - (mEndIcon?.measuredWidth ?: 0)),
            if (heightMode == MeasureSpec.EXACTLY)
                measureSpec_at_most(height)
            else
                measureSpec_unspecified
        )

        if (errorText?.visibility == View.VISIBLE)
            errorText!!.measure(
                measureSpec_exactly(width),
                measureSpec_unspecified
            )

        setMeasuredDimension(
            width,
            if (heightMode == MeasureSpec.EXACTLY)
                height + dp(9) + paddingTop + paddingBottom
            else if (errorText?.visibility == View.VISIBLE)
                getNormalHeight() + errorText!!.measuredHeight + dp(8)
            else
                getNormalHeight()
        )
    }

    private fun getNormalHeight() = editText.measuredHeight + dp(9) + paddingTop + paddingBottom

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        editText.isEnabled = enabled
        updateColor()
        mEndIcon?.setColorFilter(
            if (isEnabled)
                -0x79797a
            else
                -0x3C3C3D
        )
    }


    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
//        editText.layout(
//            paddingStart + size_normal * 3 / 2,
//            paddingTop + dp(3),
//            measuredWidth - paddingRight - size_normal * 3 / 2 - (mEndIcon?.measuredWidth ?: 0),
//            paddingTop + dp(3) + editText.measuredHeight
//        )
        editText.layout(
            paddingStart + dp(8) * 3 / 2,
            (paddingTop + outlinePaint.strokeWidth).toInt() + dp(3),
            measuredWidth - paddingRight - dp(8) * 3 / 2 - (mEndIcon?.measuredWidth ?: 0),
            (getNormalHeight() - paddingBottom - outlinePaint.strokeWidth).toInt()
        )
        mEndIcon?.layout(
            measuredWidth - dp(8) * 3 / 2 - mEndIcon!!.measuredWidth - paddingRight,
            editText.top + (editText.measuredHeight - mEndIcon!!.measuredHeight) / 2,
            measuredWidth - dp(8) * 3 / 2 - paddingRight,
            editText.top + (editText.measuredHeight + mEndIcon!!.measuredHeight) / 2
        )
        if (errorText?.visibility == View.VISIBLE)
            errorText!!.layout(
                paddingLeft + dp(16),
                editText.bottom + dp(8),
                measuredWidth - dp(16) - paddingRight,
                editText.bottom + dp(8) + errorText!!.measuredWidth
            )
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val textOffset = textPaint.textSize / 2f - dp(1.75f)
        val topY = paddingTop + textOffset
        val centerY =
            (getNormalHeight() + paddingTop - paddingBottom - dp(3)) / 2f + textPaint.textSize / 2f
        val useCenter =
            editText.length() == 0 || forceUseCenter
        val textY = if (useCenter) topY + (centerY - topY) * (1f - selectionProgress) else topY
        val stroke = outlinePaint.strokeWidth
        val scaleX = if (useCenter) 0.75f + 0.25f * (1f - selectionProgress) else 0.75f
        val textWidth = textPaint.measureText(mText) * scaleX
        canvas.save()
        rect[(paddingLeft + dp(PADDING_LEFT - PADDING_TEXT)).toFloat(), paddingTop.toFloat(), (width - dp(
            PADDING_LEFT + PADDING_TEXT
        ) - paddingRight).toFloat()] = paddingTop + stroke * 2
        canvas.clipRect(rect, Region.Op.DIFFERENCE)
        rect[paddingLeft + stroke, paddingTop + stroke, width - stroke - paddingRight] =
            getNormalHeight() - stroke - paddingBottom
        canvas.drawRoundRect(rect, dp(6).toFloat(), dp(6).toFloat(), outlinePaint)
        canvas.restore()
        val left =
            (paddingLeft + dp(PADDING_LEFT - PADDING_TEXT)).toFloat()
        val lineY = paddingTop + stroke
        val right = width - stroke - paddingRight - dp(6)
        val activeLeft =
            left + textWidth + dp(PADDING_LEFT - PADDING_TEXT)
        val fromLeft = left + textWidth / 2f
        canvas.drawLine(
            fromLeft + (activeLeft - fromLeft) * if (useCenter) selectionProgress else 1f,
            lineY,
            right,
            lineY,
            outlinePaint
        )
        val fromRight = left + textWidth / 2f + dp(PADDING_TEXT)
        canvas.drawLine(
            left,
            lineY,
            fromRight + (left - fromRight) * if (useCenter) selectionProgress else 1f,
            lineY,
            outlinePaint
        )
        val sLayout = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            StaticLayout.Builder.obtain(
                mText, 0, mText.length, textPaint, width - paddingLeft - paddingRight - dp(16)
            ).setMaxLines(1)
                .setEllipsize(TextUtils.TruncateAt.END)
                .setIncludePad(false)
                .build()
        else
            StaticLayout(
                mText,
                0,
                mText.length,
                textPaint,
                width - paddingLeft - paddingRight - dp(16),
                Layout.Alignment.ALIGN_NORMAL,
                1f,
                0f,
                false,
                TextUtils.TruncateAt.END,
                width - paddingLeft - paddingRight - dp(16)
            )
        canvas.save()
        canvas.scale(
            scaleX,
            scaleX,
            (paddingLeft + dp(PADDING_LEFT + PADDING_TEXT)).toFloat(),
            textY - sLayout.height + dp(16)
        )
        canvas.translate(
            (paddingLeft + dp(PADDING_LEFT)).toFloat(),
            textY - sLayout.height + dp(3)
        )
        sLayout.draw(canvas)
        canvas.restore()
    }

    fun showError(@StringRes errorMessage: Int, formatArg: Any? = null) {
        if (errorMessage != -1) {
            showError(context.getString(errorMessage, formatArg))
            return
        } else
            errorText?.visibility = View.GONE
        updateColor()
        if (clearErrorOnChange)
            listeningTextChanges = true
    }

    fun showError(errorMessage: String?) {
        errorProgress = 1f
        if (errorMessage != null) {
            if (errorText == null) {
                errorText = TextView(context)
                errorText!!.setTextColor(Color.RED)
                addView(errorText)
            } else
                errorText!!.visibility = View.VISIBLE
            errorText!!.text = errorMessage
        } else
            errorText?.visibility = View.GONE
        updateColor()
        if (clearErrorOnChange)
            listeningTextChanges = true
    }

    fun hideError() {
        errorProgress = 0f
        errorText?.visibility = View.GONE
        updateColor()
    }

    fun clearErrorOnChange() {
        clearErrorOnChange = true
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (listeningTextChanges) {
                    listeningTextChanges = false
                    hideError()
                }
            }
        })
    }

}
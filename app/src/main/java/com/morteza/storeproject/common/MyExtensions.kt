package com.morteza.storeproject.common

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StrikethroughSpan
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import timber.log.Timber

///////////////////////////////////////////////////////////////////////////
// Extension Metrics
///////////////////////////////////////////////////////////////////////////

val Int.toDp: Int get() = (this / Resources.getSystem().displayMetrics.density).toInt()

val Int.toPx: Int get() = (this * Resources.getSystem().displayMetrics.density).toInt()

///////////////////////////////////////////////////////////////////////////
// Extension View
///////////////////////////////////////////////////////////////////////////

fun View.visible(isVisible: Boolean) = if (isVisible) this.visibility = View.VISIBLE else this.visibility = View.GONE

internal fun RecyclerView.linearLayout(
	context: Context,
	@RecyclerView.Orientation orientation: Int? = RecyclerView.VERTICAL,
	reverseLayout: Boolean? = false,
	stackFromEnd: Boolean? = false
) {
	val lm = LinearLayoutManager(context, orientation!!, reverseLayout!!)
	lm.stackFromEnd = stackFromEnd!!
	layoutManager = lm
	setHasFixedSize(true)
}

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View =
	LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)


fun View.showKeyboard(show: Boolean) {
	val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
	if (show) {
		if (requestFocus()) imm.showSoftInput(this, 0)
	} else {
		imm.hideSoftInputFromWindow(windowToken, 0)
	}
}

///////////////////////////////////////////////////////////////////////////
// add dependency dynamicAnimation lib
///////////////////////////////////////////////////////////////////////////

@SuppressLint("ClickableViewAccessibility")
fun View.implementSpringAnimationTrait() {
	val scaleXAnim = SpringAnimation(this, DynamicAnimation.SCALE_X, 0.90f)
	val scaleYAnim = SpringAnimation(this, DynamicAnimation.SCALE_Y, 0.90f)

	setOnTouchListener { _, event ->
		Timber.i(event.action.toString())
		when (event.action) {
			MotionEvent.ACTION_DOWN -> {
				scaleXAnim.spring.stiffness = SpringForce.STIFFNESS_LOW
				scaleXAnim.spring.dampingRatio = SpringForce.DAMPING_RATIO_LOW_BOUNCY
				scaleXAnim.start()

				scaleYAnim.spring.stiffness = SpringForce.STIFFNESS_LOW
				scaleYAnim.spring.dampingRatio = SpringForce.DAMPING_RATIO_LOW_BOUNCY
				scaleYAnim.start()
			}
			MotionEvent.ACTION_UP,
			MotionEvent.ACTION_CANCEL -> {
				scaleXAnim.cancel()
				scaleYAnim.cancel()
				val reverseScaleXAnim = SpringAnimation(this, DynamicAnimation.SCALE_X, 1f)
				reverseScaleXAnim.spring.stiffness = SpringForce.STIFFNESS_LOW
				reverseScaleXAnim.spring.dampingRatio = SpringForce.DAMPING_RATIO_LOW_BOUNCY
				reverseScaleXAnim.start()

				val reverseScaleYAnim = SpringAnimation(this, DynamicAnimation.SCALE_Y, 1f)
				reverseScaleYAnim.spring.stiffness = SpringForce.STIFFNESS_LOW
				reverseScaleYAnim.spring.dampingRatio = SpringForce.DAMPING_RATIO_LOW_BOUNCY
				reverseScaleYAnim.start()
			}
		}
		false
	}
}

///////////////////////////////////////////////////////////////////////////
// Extension String
///////////////////////////////////////////////////////////////////////////

fun String.onlyDigits(): String = replace(Regex("\\D*"), "")

///////////////////////////////////////////////////////////////////////////
// Extension SpannableString
///////////////////////////////////////////////////////////////////////////

fun SpannableString.color(color: String, start: Int, end: Int): SpannableString {
	this.setSpan(ForegroundColorSpan(Color.parseColor(color)), start, end, 0)
	return this
}

fun SpannableString.bold(start: Int, end: Int): SpannableString {
	this.setSpan(StyleSpan(Typeface.BOLD), start, end, 0)
	return this
}

fun SpannableString.underline(start: Int, end: Int): SpannableString {
	this.setSpan(UnderlineSpan(), start, end, 0)
	return this
}

fun SpannableString.italic(start: Int, end: Int): SpannableString {
	this.setSpan(StyleSpan(Typeface.ITALIC), start, end, 0)
	return this
}

fun SpannableString.strike(start: Int, end: Int): SpannableString {
	this.setSpan(StrikethroughSpan(), start, end, 0)
	return this
}

///////////////////////////////////////////////////////////////////////////
// Adds flags to make window fullscreen
///////////////////////////////////////////////////////////////////////////

fun Activity.setFullscreenLayoutFlags() {
	if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
		window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
				View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
	}
}



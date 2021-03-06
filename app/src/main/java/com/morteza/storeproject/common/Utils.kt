package com.morteza.storeproject.common

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import android.util.DisplayMetrics
import androidx.browser.customtabs.CustomTabsIntent
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber


object MetricsUtil {

	/**
	 * This method converts dp unit to equivalent pixels, depending on device density.
	 * @param dp A value in dp (density independent pixels) unit. Which we need to convert into pixels
	 * @param context Context to get resources and device specific display metrics. If you don't have
	 * access to Context, just pass null.
	 * @return A float value to represent px equivalent to dp depending on device density
	 */
	fun convertDpToPixel(dp: Float, context: Context?): Float {
		return if (context != null) {
			val resources = context.resources
			val metrics = resources.displayMetrics
			dp * (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
		} else {
			val metrics = Resources.getSystem().displayMetrics
			dp * (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
		}
	}

	/**
	 * This method converts device specific pixels to density independent pixels.
	 * @param px A value in px (pixels) unit. Which we need to convert into db
	 * @param context Context to get resources and device specific display metrics. If you don't have
	 * access to Context, just pass null.
	 * @return A float value to represent dp equivalent to px value
	 */
	fun convertPixelsToDp(px: Float, context: Context?): Float {
		return if (context != null) {
			val resources = context.resources
			val metrics = resources.displayMetrics
			px / (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
		} else {
			val metrics = Resources.getSystem().displayMetrics
			px / (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
		}
	}
}

fun formatPriceToman(price: Number, unitRelativeSizeFactor: Float = 0.7f): SpannableString {
	val currencyLabel = "??????????"
	val spannableString = SpannableString("$price $currencyLabel")
	spannableString.setSpan(
		RelativeSizeSpan(unitRelativeSizeFactor),
		spannableString.indexOf(currencyLabel),
		spannableString.length,
		SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
	)
	return spannableString
}

fun <T> Single<T>.asyncNetworkRequest(): Single<T> {
	return subscribeOn(Schedulers.io())
		.observeOn(AndroidSchedulers.mainThread())
}

fun Completable.asyncNetworkResponse(): Completable {
	return subscribeOn(Schedulers.io())
		.observeOn(AndroidSchedulers.mainThread())
}

fun openUrlInCustomTab(context: Context, url: String) {
	try {
		val uri = Uri.parse(url)
		val intentBuilder = CustomTabsIntent.Builder()
		intentBuilder.setStartAnimations(context, android.R.anim.fade_in, android.R.anim.fade_out)
		intentBuilder.setExitAnimations(context, android.R.anim.fade_in, android.R.anim.fade_out)
		val customTabsIntent = intentBuilder.build()
		customTabsIntent.intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
		customTabsIntent.launchUrl(context, uri)

	} catch (e: Exception) {
		Timber.e(e)
	}
}
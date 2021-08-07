package com.morteza.storeproject.common

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.snackbar.Snackbar
import com.morteza.storeproject.R
import com.morteza.storeproject.feature.auth.AuthActivity
import io.reactivex.disposables.CompositeDisposable
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

abstract class NikeFragment : Fragment(), NikeView {

	val compositeDisposable = CompositeDisposable()
	override val rootView: CoordinatorLayout?
		get() = view as CoordinatorLayout
	override val viewContext: Context?
		get() = context

	override fun onStart() {
		super.onStart()
		EventBus.getDefault().register(this)
	}

	override fun onStop() {
		EventBus.getDefault().unregister(this)
		compositeDisposable.clear()
		super.onStop()
	}
}

abstract class NikeActivity : AppCompatActivity(), NikeView {
	override val rootView: CoordinatorLayout?
		get() {
			val viewGroup = window.decorView.findViewById(android.R.id.content) as ViewGroup
			if (viewGroup !is CoordinatorLayout) {
				viewGroup.children.forEach {
					if (it is CoordinatorLayout)
						return it
				}
				throw IllegalStateException("RootView must be instance of CoordinatorLayout")
			} else
				return viewGroup
		}
	override val viewContext: Context?
		get() = this

	override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
		super.onCreate(savedInstanceState, persistentState)
		EventBus.getDefault().register(this)
	}

	override fun onDestroy() {
		EventBus.getDefault().unregister(this)
		super.onDestroy()
	}
}

interface NikeView {
	val rootView: CoordinatorLayout?
	val viewContext: Context?

	fun setProgressIndicator(isShow: Boolean) {
		rootView?.let {
			viewContext?.let { context ->
				var loadingView = it.findViewById<View>(R.id.loadingView)
				if (loadingView == null && isShow) {
					loadingView =
						LayoutInflater.from(context).inflate(R.layout.view_loading, it, false)
					it.addView(loadingView)
				}
				loadingView?.visibility = if (isShow) View.VISIBLE else View.GONE
			}
		}
	}

	fun showEmptyState(layoutResId: Int): View? {
		rootView?.let {
			viewContext?.let { context ->
				var emptyState = it.findViewById<View>(R.id.emptyStateRootView)
				if (emptyState == null) {
					emptyState = LayoutInflater.from(context).inflate(layoutResId, it, false)
					it.addView(emptyState)
				}
				emptyState.visibility = View.VISIBLE
				return emptyState
			}
		}
		return null
	}

	@Subscribe(threadMode = ThreadMode.MAIN)
	fun showError(nikeException: NikeException) {
		viewContext?.let {
			when (nikeException.type) {
				NikeException.Type.SIMPLE ->
					showSnackBar(nikeException.serverMessage ?: it.getString(nikeException.userFriendlyMessage))
				NikeException.Type.AUTH   -> {
					it.startActivity(Intent(it, AuthActivity::class.java))
					showToast(nikeException.serverMessage ?: it.getString(nikeException.userFriendlyMessage))
				}
			}
		}
	}

	fun showSnackBar(message: String, duration: Int = Snackbar.LENGTH_SHORT) {
		rootView?.let {
			Snackbar.make(it, message, duration).show()
		}
	}

	fun showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
		rootView?.let {
			Toast.makeText(it.context, message, duration).show()
		}
	}
}

abstract class NikeViewModel : ViewModel() {
	val compositeDisposable = CompositeDisposable()
	val progressBarLiveData = MutableLiveData<Boolean>()

	override fun onCleared() {
		compositeDisposable.clear()
		super.onCleared()
	}

	fun showProgressbar(isShow: Boolean) {
		progressBarLiveData.value = isShow
	}

}
package com.morteza.storeproject.feature.main

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.navigation.NavController

import com.example.android.navigationadvancedsample.setupWithNavController
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.color.MaterialColors
import com.morteza.storeproject.R
import com.morteza.storeproject.common.NikeActivity
import com.morteza.storeproject.common.toPx
import com.morteza.storeproject.data.model.CartItemCount
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class MainActivity : NikeActivity() {
	val viewModel: MainViewModel by viewModel()
	private var currentNavController: LiveData<NavController>? = null

	override fun onStart() {
		super.onStart()
		EventBus.getDefault().register(this);
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		if (savedInstanceState == null) {
			setupBottomNavigationBar()
		} // Else, need to wait for onRestoreInstanceState
	}

	override fun onRestoreInstanceState(savedInstanceState: Bundle) {
		super.onRestoreInstanceState(savedInstanceState)
		// Now that BottomNavigationBar has restored its instance state
		// and its selectedItemId, we can proceed with setting up the
		// BottomNavigationBar with Navigation
		setupBottomNavigationBar()
	}

	/**
	 * Called on first creation and when restoring state.
	 */
	private fun setupBottomNavigationBar() {
		val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationMain)

		val navGraphIds = listOf(R.navigation.home, R.navigation.cart, R.navigation.profile)

		// Setup the bottom navigation view with a list of navigation graphs
		val controller = bottomNavigationView.setupWithNavController(
			navGraphIds = navGraphIds,
			fragmentManager = supportFragmentManager,
			containerId = R.id.nav_host_container,
			intent = intent
		)

		/*  // Whenever the selected controller changes, setup the action bar.
		  controller.observe(this, Observer { navController ->
			  setupActionBarWithNavController(navController)
		  })*/
		currentNavController = controller
	}

	override fun onSupportNavigateUp(): Boolean {
		return currentNavController?.value?.navigateUp() ?: false
	}

	@Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
	fun onCartItemsCountChangeEvent(cartItemCount: CartItemCount) {
		Timber.i("-------- >>> change event badge ${cartItemCount.count}")
		val badge = bottomNavigationMain.getOrCreateBadge(R.id.cart)
		badge.badgeGravity = BadgeDrawable.BOTTOM_START
		badge.backgroundColor = MaterialColors.getColor(bottomNavigationMain, R.attr.colorPrimary)
		badge.number = cartItemCount.count
		badge.verticalOffset = 10.toPx
		badge.isVisible = cartItemCount.count > 0
	}

	override fun onResume() {
		super.onResume()
		Timber.i(" >>> on get count")
		viewModel.getCartItemsCount()
	}

	override fun onStop() {
		EventBus.getDefault().unregister(this);
		super.onStop()
	}
}
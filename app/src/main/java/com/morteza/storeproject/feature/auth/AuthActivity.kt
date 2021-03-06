package com.morteza.storeproject.feature.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.morteza.storeproject.R

class AuthActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_auth)

		supportFragmentManager.beginTransaction().apply {
			replace(R.id.fragmentContainer, LoginFragment())
		}.commit()
	}
}
package com.morteza.storeproject.data.repo.source.user

import android.content.SharedPreferences
import com.morteza.storeproject.data.TokenContainer
import com.morteza.storeproject.data.responce.MessageResponse
import com.morteza.storeproject.data.responce.TokenResponse
import io.reactivex.Single

private const val KEY_ACCESS_TOKEN = "ACCESS_TOKEN"
private const val KEY_REFRESH_TOKEN = "REFRESH_TOKEN"

class UserLocalDataSource(private val sharedPreferences: SharedPreferences) : UserDataSource {
	override fun login(username: String, password: String): Single<TokenResponse> {
		TODO("remote")
	}

	override fun signUp(username: String, password: String): Single<MessageResponse> {
		TODO("remote")
	}

	override fun loadToken() {
		TokenContainer.update(
			sharedPreferences.getString(KEY_ACCESS_TOKEN, null),
			sharedPreferences.getString(KEY_REFRESH_TOKEN, null)
		)
	}

	override fun saveToken(token: String, refreshToken: String) {
		sharedPreferences.edit().apply {
			putString(KEY_ACCESS_TOKEN, token)
			putString(KEY_REFRESH_TOKEN, refreshToken)
		}.apply()
	}

	override fun saveUsername(username: String) {
		TODO("Not yet implemented")
	}

	override fun getUsername(): String {
		TODO("Not yet implemented")
	}

	override fun signOut() {
		TODO("Not yet implemented")
	}
}
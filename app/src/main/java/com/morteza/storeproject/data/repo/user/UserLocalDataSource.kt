package com.morteza.storeproject.data.repo.user

import android.content.SharedPreferences
import com.morteza.storeproject.data.TokenContainer
import com.morteza.storeproject.data.responce.MessageResponse
import com.morteza.storeproject.data.responce.TokenResponse
import io.reactivex.Single
import timber.log.Timber

private const val KEY_ACCESS_TOKEN = "ACCESS_TOKEN"
private const val KEY_REFRESH_TOKEN = "REFRESH_TOKEN"
private const val KEY_USERNAME = "USERNAME"

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
		Timber.i(">> saveToke=${token.substring(0, 10)},Refresh Token=${refreshToken.substring(0, 10)}")
	}

	override fun saveUsername(username: String) =
		sharedPreferences.edit().apply { putString(KEY_USERNAME, username) }.apply()

	override fun getUsername(): String =
		sharedPreferences.getString(KEY_USERNAME, "") ?: ""

	override fun signOut() =
		sharedPreferences.edit().apply { clear() }.apply()
}
package com.morteza.storeproject.feature.auth

import com.morteza.storeproject.common.NikeViewModel
import com.morteza.storeproject.data.repo.user.UserRepository
import io.reactivex.Completable
import timber.log.Timber

class AuthViewModel(private val userRepository: UserRepository) : NikeViewModel() {

	fun login(email: String, password: String): Completable {
		progressBarLiveData.value = true
		return userRepository.login(email, password).doFinally {
			progressBarLiveData.postValue(false)
		}
	}

	fun signUp(email: String, password: String): Completable {
		progressBarLiveData.value = true
		return userRepository.signUp(email, password)
			.doOnComplete { Timber.i("user=$email and pass=$password") }
			.doFinally { progressBarLiveData.postValue(false) }
	}
}
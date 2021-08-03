package com.morteza.storeproject.feature.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.morteza.storeproject.R
import com.morteza.storeproject.common.NikeCompletableObserver
import com.morteza.storeproject.common.NikeFragment
import com.morteza.storeproject.common.asyncNetworkResponse
import kotlinx.android.synthetic.main.fragment_sign_up.*
import org.koin.android.ext.android.inject

class SignUpFragment : NikeFragment() {
	private val viewModel: AuthViewModel by inject()

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		return inflater.inflate(R.layout.fragment_sign_up, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		signUpBtn.setOnClickListener {
			viewModel.signUp(emailEt.text.toString(), passwordEt.text.toString())
				//				.subscribeOn(Schedulers.io())
				//				.observeOn(AndroidSchedulers.mainThread())
				.asyncNetworkResponse()
				.subscribe(object : NikeCompletableObserver(compositeDisposable) {
					override fun onComplete() {
						requireActivity().finish()
					}
				})
		}

		loginLinkBtn.setOnClickListener {
			requireActivity().supportFragmentManager.beginTransaction().apply {
				replace(R.id.fragmentContainer, LoginFragment())
			}.commit()
		}
	}
}
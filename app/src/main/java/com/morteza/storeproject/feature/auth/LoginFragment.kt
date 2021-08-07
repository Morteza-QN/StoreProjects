package com.morteza.storeproject.feature.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.morteza.storeproject.R
import com.morteza.storeproject.common.NikeCompletableObserver
import com.morteza.storeproject.common.NikeFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_login.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : NikeFragment() {
	private val compositeDisposable2 = CompositeDisposable()
	val viewModel: AuthViewModel by viewModel()

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		return inflater.inflate(R.layout.fragment_login, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		loginBtn.setOnClickListener {
			viewModel.login(emailEt.text.toString(), passwordEt.text.toString())
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(object : NikeCompletableObserver(compositeDisposable2) {
					override fun onComplete() {
						requireActivity().finish()
					}
				})
		}
		signUpLinkBtn.setOnClickListener {
			requireActivity().supportFragmentManager.beginTransaction().apply {
				replace(R.id.fragmentContainer, SignUpFragment())
			}.commit()
		}
	}

	override fun onStop() {
		super.onStop()
		compositeDisposable.clear()
	}
}
package com.morteza.storeproject.feature.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.morteza.storeproject.R
import com.morteza.storeproject.common.NikeCompletableObserver
import com.morteza.storeproject.common.NikeFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_login.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : NikeFragment() {

	//	private val compositeDisposable = CompositeDisposable()
	val viewModel: AuthViewModel by viewModel()


	companion object {
		fun newInstance() = LoginFragment()
	}


	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		return inflater.inflate(R.layout.login_fragment, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		loginBtn.setOnClickListener {
			viewModel.login(emailEt.text.toString(), passwordEt.text.toString())
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(object : NikeCompletableObserver(compositeDisposable) {
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
}
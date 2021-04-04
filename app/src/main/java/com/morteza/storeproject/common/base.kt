package com.morteza.storeproject.common

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class NikeFragment : Fragment(), NikeView

abstract class NikeActivity : AppCompatActivity(), NikeView

interface NikeView {
    fun setProgressIndicator(isShow: Boolean) {}
}

abstract class NikeViewModel : ViewModel() {
    val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}
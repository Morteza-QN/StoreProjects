package com.morteza.storeproject.feature.product

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.morteza.storeproject.common.KEY_DATA
import com.morteza.storeproject.common.NikeSingleObserver
import com.morteza.storeproject.common.NikeViewModel
import com.morteza.storeproject.data.Comment
import com.morteza.storeproject.data.Product
import com.morteza.storeproject.data.repo.comment.CommentRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ProductDetailsViewModel(
	bundle: Bundle,
	commentRepository: CommentRepository
) : NikeViewModel() {

	val productLiveData = MutableLiveData<Product>()
	val commentsLiveData = MutableLiveData<List<Comment>>()

	init {
		productLiveData.value = bundle.getParcelable(KEY_DATA)
		getComments(commentRepository)
	}

	private fun getComments(commentRepository: CommentRepository) {
		progressBarLiveData.value = true
		commentRepository.getComments(productLiveData.value!!.id)
			.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.doFinally { progressBarLiveData.value = false }
			.subscribe(object : NikeSingleObserver<List<Comment>>(compositeDisposable) {
				override fun onSuccess(t: List<Comment>) {
					commentsLiveData.value = t
				}
			})
	}
}
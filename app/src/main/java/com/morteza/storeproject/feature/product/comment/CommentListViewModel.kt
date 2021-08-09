package com.morteza.storeproject.feature.product.comment

import androidx.lifecycle.MutableLiveData
import com.morteza.storeproject.common.NikeSingleObserver
import com.morteza.storeproject.common.NikeViewModel
import com.morteza.storeproject.common.asyncNetworkRequest
import com.morteza.storeproject.data.model.Comment
import com.morteza.storeproject.data.repo.comment.CommentRepository

class CommentListViewModel(productId: Int, commentRepository: CommentRepository) : NikeViewModel() {

	val commentsLiveData = MutableLiveData<List<Comment>>()


	init {
		getComments(commentRepository, productId)
	}

	private fun getComments(commentRepository: CommentRepository, productId: Int) {
		progressBarLiveData.value = true

		commentRepository.getComments(productId)
			.asyncNetworkRequest()
			.doFinally { progressBarLiveData.value = false }
			.subscribe(object : NikeSingleObserver<List<Comment>>(compositeDisposable) {
				override fun onSuccess(data: List<Comment>) {
					commentsLiveData.value = data
				}
			})
	}
}
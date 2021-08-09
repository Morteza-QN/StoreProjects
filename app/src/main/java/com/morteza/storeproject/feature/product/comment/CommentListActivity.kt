package com.morteza.storeproject.feature.product.comment

import android.os.Bundle
import android.view.View
import com.morteza.storeproject.R
import com.morteza.storeproject.common.KEY_ID
import com.morteza.storeproject.common.NikeActivity
import com.morteza.storeproject.data.model.Comment
import com.morteza.storeproject.feature.product.CommentAdapter
import kotlinx.android.synthetic.main.activity_comment_list.*
import kotlinx.android.synthetic.main.activity_product_details.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class CommentListActivity : NikeActivity() {

	val viewModel: CommentListViewModel by viewModel { parametersOf(intent.extras!!.getInt(KEY_ID)) }
	private val commentAdapter = CommentAdapter(true)

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_comment_list)
		viewModel.progressBarLiveData.observe(this) { setProgressIndicator(it) }

		viewModel.commentsLiveData.observe(this) {
			commentAdapter.comments = it as ArrayList<Comment>
			commentsRv.adapter = commentAdapter
		}
		commentListToolbar.onClickBackBtnListener = View.OnClickListener { finish() }
	}

}
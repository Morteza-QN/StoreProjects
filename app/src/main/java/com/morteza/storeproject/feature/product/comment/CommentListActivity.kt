package com.morteza.storeproject.feature.product.comment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.morteza.storeproject.R
import com.morteza.storeproject.common.KEY_ID
import com.morteza.storeproject.common.NikeActivity
import com.morteza.storeproject.data.Comment
import com.morteza.storeproject.feature.product.CommentAdapter
import kotlinx.android.synthetic.main.activity_comment_list.*
import kotlinx.android.synthetic.main.activity_product_details.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class CommentListActivity : NikeActivity() {

	val viewModel: CommentListViewModel by viewModel { parametersOf(intent.extras!!.getInt(KEY_ID)) }


	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_comment_list)

		myInit()
	}

	private fun myInit() {
		viewModel.commentsLiveData.observe(this) {
			val commentAdapter = CommentAdapter(true)
			commentAdapter.comments = it as ArrayList<Comment>
			commentsRv.apply {
				layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
				adapter = commentAdapter
			}
		}
		commentListToolbar.onClickBackBtnListener = View.OnClickListener { finish() }
		viewModel.progressBarLiveData.observe(this) { setProgressIndicator(it) }
	}
}
package com.morteza.storeproject.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.morteza.storeproject.R
import kotlinx.android.synthetic.main.view_toolbar.view.*

class NikeToolbar(
	context: Context,
	attrs: AttributeSet?
) : FrameLayout(context, attrs) {
	var onClickBackBtnListener: View.OnClickListener? = null
		set(value) {
			field = value
			backBtn.setOnClickListener(onClickBackBtnListener)
		}

	init {
		inflate(context, R.layout.view_toolbar, this)

		attrs.let {
			val allAttrs = context.obtainStyledAttributes(attrs, R.styleable.NikeToolbar)
			val title = allAttrs.getString(R.styleable.NikeToolbar_toolbarTitle)
			if (title != null && title.isNotEmpty()) toolbarTitleTv.text = title
			allAttrs.recycle()
		}
	}
}
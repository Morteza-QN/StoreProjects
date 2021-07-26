package com.morteza.storeproject.feature.product

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.morteza.storeproject.common.KEY_DATA
import com.morteza.storeproject.common.NikeViewModel
import com.morteza.storeproject.data.Product

class ProductDetailsViewModel(bundle: Bundle) : NikeViewModel() {

	val productLiveData = MutableLiveData<Product>()

	init {
		productLiveData.value = bundle.getParcelable(KEY_DATA)
	}
}
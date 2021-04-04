package com.morteza.storeproject.feature.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.morteza.storeproject.R
import com.morteza.storeproject.common.NikeFragment
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber

class MainFragment : NikeFragment() {
    val mainViewModel: MainViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel.productLiveData.observe(this) {
            Timber.i(it.toString())
        }
    }
}
package com.morteza.storeproject.feature.main

import android.os.Bundle
import com.morteza.storeproject.R
import com.morteza.storeproject.common.NikeActivity

class MainActivity : NikeActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
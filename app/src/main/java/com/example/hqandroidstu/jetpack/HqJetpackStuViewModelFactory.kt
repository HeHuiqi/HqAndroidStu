package com.example.hqandroidstu.jetpack

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class HqJetpackStuViewModelFactory(private val initCount:Int) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HqJetpackStuViewModel2(initCount) as T
    }
}
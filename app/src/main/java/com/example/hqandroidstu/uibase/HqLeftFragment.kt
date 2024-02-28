package com.example.hqandroidstu.uibase

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.hqandroidstu.R


class HqLeftFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hq_left, container, false)
    }
    fun hqGetActivity() {
        if (activity != null) {
            Toast.makeText(activity,"HqLeftFragment:$activity",Toast.LENGTH_SHORT).show()
        }
    }
}
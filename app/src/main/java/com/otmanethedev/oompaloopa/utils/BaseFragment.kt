package com.otmanethedev.oompaloopa.utils

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VB : ViewBinding> : Fragment() {

    private var _binding: VB? = null
    protected open val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = setBinding(inflater, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUi()
        setUpObservers()
    }

    abstract fun setBinding(inflater: LayoutInflater, container: ViewGroup?): VB

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    fun navigate(navDirections: NavDirections) {
        findNavController().navigate(navDirections)
    }

    fun navigateUp() {
        findNavController().navigateUp()
    }

    open fun setUpObservers() {}

    open fun setUpUi() {}
}
package com.kazantsev.coder.view.profilefragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.kazantsev.coder.databinding.FragmentDetailsBinding

class ProfileFragment : Fragment() {
    private var _viewBinding: FragmentDetailsBinding? = null
    private val viewBinding get() = checkNotNull(_viewBinding)
    private val navigation by lazy { findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _viewBinding = FragmentDetailsBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
//        loadData()
    }

    private fun setupUI() {
        viewBinding.ivBack.setOnClickListener { navigation.navigateUp() }
    }

    override fun onDestroy() {
        super.onDestroy()
        _viewBinding = null
    }
}
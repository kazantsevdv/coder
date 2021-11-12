package com.kazantsev.coder.view.listfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.kazantsev.coder.databinding.FragmentDetailsBinding
import com.kazantsev.coder.databinding.FragmentListBinding

class ListFragment : Fragment() {
    private var _viewBinding: FragmentListBinding? = null
    private val viewBinding get() = checkNotNull(_viewBinding)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _viewBinding = FragmentListBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
//        loadData()
    }

    private fun setupUI() {

    }

    override fun onDestroy() {
        super.onDestroy()
        _viewBinding = null
    }
}
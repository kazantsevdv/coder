package com.kazantsev.coder.view.mainfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.kazantsev.coder.databinding.FragmentMainBinding

class MainFragment : Fragment() {
    private var _viewBinding: FragmentMainBinding? = null
    private val viewBinding get() = checkNotNull(_viewBinding)
    private val navigation by lazy { findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _viewBinding = FragmentMainBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val direction = MainFragmentDirections.actionMainFragmentToProfileFragment("a2f8e911-67d2-4d1a-8056-b82d45e11a8e")
        navigation.navigate(direction)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        setupUI()
//        loadData()

    }

    override fun onDestroy() {
        super.onDestroy()
        _viewBinding = null
    }

}
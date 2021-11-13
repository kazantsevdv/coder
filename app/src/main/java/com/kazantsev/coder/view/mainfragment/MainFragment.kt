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
        val direction = MainFragmentDirections.actionMainFragmentToProfileFragment("e66c4836-ad5f-4b93-b82a-9251b0f9aca2")
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
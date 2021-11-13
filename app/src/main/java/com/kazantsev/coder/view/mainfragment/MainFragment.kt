package com.kazantsev.coder.view.mainfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kazantsev.coder.App
import com.kazantsev.coder.databinding.FragmentMainBinding
import com.kazantsev.coder.view.mainfragment.MainViewModel
import javax.inject.Inject
import javax.inject.Provider

class MainFragment : Fragment() {
    private var _viewBinding: FragmentMainBinding? = null
    private val viewBinding get() = checkNotNull(_viewBinding)


    @Inject
    lateinit var viewModeProvider: Provider<MainViewModel.Factory>
    private val viewModel: MainViewModel by viewModels { viewModeProvider.get() }

    private val adapter: ViewPagerAdapter by lazy { ViewPagerAdapter(childFragmentManager) }

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
        App.component.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        loadData()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.department.observe(viewLifecycleOwner, {
            adapter.apply {
                setData(it)
                notifyDataSetChanged()
            }
        })
    }

    private fun loadData() {
        viewModel.getDepartment()
    }

    private fun setupUI() {
        viewBinding.viewPager.let {
            it.adapter = adapter
            viewBinding.tabLayout.setupWithViewPager(it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _viewBinding = null
    }

}
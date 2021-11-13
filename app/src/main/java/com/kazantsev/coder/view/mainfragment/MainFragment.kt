package com.kazantsev.coder.view.mainfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.kazantsev.coder.App
import com.kazantsev.coder.databinding.FragmentMainBinding
import com.kazantsev.coder.model.AppState
import com.kazantsev.coder.model.User
import com.kazantsev.coder.view.listfragment.ListViewModel
import javax.inject.Inject
import javax.inject.Provider

class MainFragment : Fragment() {
    private var _viewBinding: FragmentMainBinding? = null
    private val viewBinding get() = checkNotNull(_viewBinding)


    @Inject
    lateinit var viewModeProvider: Provider<MainViewModel.Factory>
    private val viewModel: MainViewModel by viewModels(ownerProducer = { requireActivity()}) { viewModeProvider.get() }

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
        viewModel.loadState.observe(viewLifecycleOwner, {
            it?.let { result ->
                when (result) {
                    is AppState.Success<List<User>> -> {
                        showSuccess()
                    }
                    is AppState.Error -> {
                        showError(result)
                    }
                    is AppState.Loading -> {
                        showLoading()
                    }
                }
            }
        })
    }

    private fun showSuccess() {
        viewBinding.refresh.isRefreshing=false
        viewBinding.refresh.isVisible=true
        viewBinding.loError.isVisible=false
        viewBinding.find.isVisible=true

    }

    private fun showError(result: AppState.Error<List<User>>) {
        viewBinding.refresh.isRefreshing=false
        viewBinding.refresh.isVisible=false
        viewBinding.loError.isVisible=true
        viewBinding.find.isVisible=false
        Snackbar.make(requireView(), result.message, Snackbar.LENGTH_SHORT).show()
    }

    private fun showLoading() {
        viewBinding.refresh.isRefreshing=true
        viewBinding.refresh.isVisible=true
        viewBinding.loError.isVisible=false
        viewBinding.find.isVisible=true
    }

    private fun loadData() {
        viewModel.getDepartment()
    }

    private fun setupUI() {
        viewBinding.viewPager.adapter = adapter
        viewBinding.tabLayout.setupWithViewPager(viewBinding.viewPager)
        viewBinding.refresh.setOnRefreshListener {
            viewModel.loadFromApi()
        }
        viewBinding.btTry.setOnClickListener {
            viewModel.loadFromApi()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _viewBinding = null
    }

}
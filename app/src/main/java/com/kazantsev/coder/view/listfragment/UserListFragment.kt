package com.kazantsev.coder.view.listfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kazantsev.coder.App
import com.kazantsev.coder.databinding.FragmentListBinding
import com.kazantsev.coder.model.AppState
import com.kazantsev.coder.model.User
import com.kazantsev.coder.repo.image.ImageLoader
import com.kazantsev.coder.view.adapter.OnListItemClickListener
import com.kazantsev.coder.view.adapter.RvAdapter
import com.kazantsev.coder.view.adapter.vhitem.RecordDelimiterVHList
import com.kazantsev.coder.view.adapter.vhitem.RecordVHListDate
import com.kazantsev.coder.view.adapter.vhitem.RecordVHListName
import com.kazantsev.coder.view.mainfragment.DataItem
import com.kazantsev.coder.view.mainfragment.MainFragmentDirections
import com.kazantsev.coder.view.mainfragment.MainViewModel
import javax.inject.Inject
import javax.inject.Provider

class UserListFragment : Fragment() {
    private var _viewBinding: FragmentListBinding? = null
    private val viewBinding get() = checkNotNull(_viewBinding)

    @Inject
    lateinit var viewModeProvider: Provider<ListViewModel.Factory>
    private val viewModel: ListViewModel by viewModels() { viewModeProvider.get() }

    @Inject
    lateinit var mainViewModeProvider: Provider<MainViewModel.Factory>
    private val mainViewModel: MainViewModel by viewModels(ownerProducer = { requireActivity() }) { mainViewModeProvider.get() }

    @Inject
    lateinit var imageLoader: ImageLoader<ImageView>
    private val navigation by lazy { findNavController() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.component.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        App.component.inject(this)
        _viewBinding = FragmentListBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupObservers()
        loadData()
    }

    private fun setupObservers() {

        viewModel.item.observe(viewLifecycleOwner, { usersList ->
            if (usersList.isNullOrEmpty()) {
                showError()
            } else {
                showSuccess(usersList)
            }
        })
        mainViewModel.loadState.observe(viewLifecycleOwner, {
            it?.let { result ->

                if (result is AppState.Success<List<User>>) {
                    loadData()
                }


            }
        })
    }

    private fun loadData() {
        viewModel.getUser(arguments?.getString(ID_DEPARTMENT) ?: "")
    }

    private fun showSuccess(usersList: List<DataItem>) {
        viewBinding.data.isVisible = true
        viewBinding.loNoData.isVisible = false
        adapterList.submitList(usersList)
    }

    private fun showError() {
        viewBinding.data.isVisible = false
        viewBinding.loNoData.isVisible = true

    }

    private fun setupUI() {
        viewBinding.data.adapter = adapterList
    }

    private val adapterList by lazy(LazyThreadSafetyMode.NONE) {
        RvAdapter(getVhList(), imageLoader, onListItemClickListener)
    }

    private val onListItemClickListener: OnListItemClickListener =
        object : OnListItemClickListener {
            override fun onItemClick(id: String) {
                val direction =
                    MainFragmentDirections.actionMainFragmentToProfileFragment(id)
                navigation.navigate(direction)
            }
        }


    private fun getVhList() = listOf(
        RecordDelimiterVHList(),
        RecordVHListName(),
        RecordVHListDate()
    )

    override fun onDestroy() {
        super.onDestroy()
        _viewBinding = null
    }

    companion object {

        fun newInstance(department: String) = UserListFragment().apply {
            arguments = bundleOf(ID_DEPARTMENT to department)
        }

        private const val ID_DEPARTMENT = "ID_DEPARTMENT"
    }
}
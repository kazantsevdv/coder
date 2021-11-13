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
import com.kazantsev.coder.App
import com.kazantsev.coder.databinding.FragmentListBinding
import com.kazantsev.coder.repo.image.ImageLoader
import com.kazantsev.coder.view.adapter.OnListItemClickListener
import com.kazantsev.coder.view.adapter.RvAdapter
import com.kazantsev.coder.view.adapter.vhitem.RecordDelimiterVHList
import com.kazantsev.coder.view.adapter.vhitem.RecordVHListDate
import com.kazantsev.coder.view.adapter.vhitem.RecordVHListName
import com.kazantsev.coder.view.mainfragment.DataItem
import javax.inject.Inject
import javax.inject.Provider

class UserListFragment : Fragment() {
    private var _viewBinding: FragmentListBinding? = null
    private val viewBinding get() = checkNotNull(_viewBinding)

    @Inject
    lateinit var viewModeProvider: Provider<UserListViewModel.Factory>
    private val viewModel: UserListViewModel by viewModels { viewModeProvider.get() }

    @Inject
    lateinit var imageLoader: ImageLoader<ImageView>

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
        viewModel.getUser(arguments?.getString(ID_DEPARTMENT) ?: "")
    }

    private fun setupObservers() {

        viewModel.item.observe(viewLifecycleOwner, { usersList ->
            if (usersList.isNullOrEmpty()) {
                showError()
            } else {
                showSuccess(usersList)
            }
        })
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
            override fun onItemClick(data: DataItem) {

            }
        }

    private fun getVhList() = listOf(
        RecordDelimiterVHList(),
        RecordVHListDate(),
        RecordVHListName()
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
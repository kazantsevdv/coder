package com.kazantsev.coder.view.profilefragment

import android.os.Bundle
import android.telephony.PhoneNumberUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.kazantsev.coder.App
import com.kazantsev.coder.R
import com.kazantsev.coder.databinding.FragmentDetailsBinding
import com.kazantsev.coder.model.AppState
import com.kazantsev.coder.model.User
import com.kazantsev.coder.repo.image.ImageLoader
import java.lang.Exception
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject
import javax.inject.Provider

class ProfileFragment : Fragment() {
    private var _viewBinding: FragmentDetailsBinding? = null
    private val viewBinding get() = checkNotNull(_viewBinding)
    private val navigation by lazy { findNavController() }
    private val args: ProfileFragmentArgs by navArgs()

    @Inject
    lateinit var viewModeProvider: Provider<ProfileViewModel.Factory>

    @Inject
    lateinit var imageLoader: ImageLoader<ImageView>

    private val viewModel: ProfileViewModel by viewModels { viewModeProvider.get() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        App.component.inject(this)
        _viewBinding = FragmentDetailsBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupObservers()
        loadData()
    }

    private fun loadData() {
        viewModel.getUser(args.userId)
    }

    private fun setupObservers() {
        viewModel.data.observe(viewLifecycleOwner, {
            it?.let { result ->
                when (result) {
                    is AppState.Success<UserProfile> -> {
                        showSuccess(result.data)
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

    private fun showSuccess(data: UserProfile) {
        viewBinding.progressBar.isVisible=false
        viewBinding.tvName.text = data.name
        viewBinding.tvNik.text = data.userTag
        viewBinding.tvPosition.text = data.position
        viewBinding.tvBirthday.text = data.birthday
        viewBinding.tvPhone.text = data.phone
        viewBinding.tvYears.text =
            this.resources.getQuantityString(R.plurals.plurals_years, data.years, data.years)
        imageLoader.loadInto(data.avatarUrl, viewBinding.ivAvatar)

    }

    private fun showError(result: AppState.Error<UserProfile>) {
        viewBinding.progressBar.isVisible=false
        Snackbar.make(requireView(), result.message, Snackbar.LENGTH_SHORT).show()
    }

    private fun showLoading() {
        viewBinding.progressBar.isVisible=true
    }

    private fun setupUI() {
        viewBinding.ivBack.setOnClickListener { navigation.navigateUp() }
    }

    override fun onDestroy() {
        super.onDestroy()
        _viewBinding = null
    }
}
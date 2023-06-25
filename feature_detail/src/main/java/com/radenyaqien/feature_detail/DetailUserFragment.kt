package com.radenyaqien.feature_detail


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import coil.load
import coil.transform.CircleCropTransformation
import com.google.android.material.snackbar.Snackbar
import com.radenyaqien.core.domain.Repository
import com.radenyaqien.core.domain.model.DetailUser
import com.radenyaqien.core.domain.usecase.GetDetailUserUsecase
import com.radenyaqien.feature_detail.databinding.FragmentDetailUserBinding
import com.radenyaqien.githubuser2023.R
import com.radenyaqien.githubuser2023.di.DfmDependencies
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.launch
import javax.inject.Inject


class DetailUserFragment : Fragment() {

    private var _binding: FragmentDetailUserBinding? = null
    private val binding get() = requireNotNull(_binding)
    private val args by navArgs<DetailUserFragmentArgs>()

    @Inject
    lateinit var repo: Repository
    private val usecase by lazy { GetDetailUserUsecase(repository = repo) }
    private val factory by lazy {
        MyViewModelProvider(
            usecase = usecase,
            args.username
        )
    }
    private val viewModel: DetailViewModel by viewModels {
        factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerDetailComponent.factory()
            .create(
                EntryPointAccessors.fromApplication(
                    requireActivity().applicationContext, DfmDependencies::class.java
                )
            ).inject(this)

        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect {
                    it.data?.let { it1 -> handleUser(it1) }
                    handleLoading(it.isLoading)
                    handleError(it.message)
                }

            }
        }
    }

    private fun handleLoading(loading: Boolean) {
        binding.progressBar.isVisible = loading
    }

    private fun handleError(message: String?) {
        if (message != null) {
            setUserNotFound()
            Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
            viewModel.clearErrorMessage()
        }
    }

    private fun setUserNotFound() {
        binding.txtFollower.text = "-"
        binding.txtFollowing.text = "-"
        binding.txtRepo.text = "-"
        binding.txtUsername.text = getString(com.radenyaqien.feature_detail.R.string.user_not_found)
        binding.txtLocation.text = "-"
        binding.imgUser.load(R.drawable.ic_launcher_foreground) {
            crossfade(true)
            placeholder(R.drawable.ic_launcher_foreground)
            error(R.drawable.ic_launcher_foreground)
            transformations(CircleCropTransformation())
        }
    }

    private fun handleUser(it: DetailUser) {
        binding.txtFollower.text = it.followers.toString()
        binding.txtFollowing.text = it.following.toString()
        binding.txtRepo.text = it.repos.toString()
        binding.txtUsername.text = it.username
        binding.txtLocation.text = it.location
        binding.imgUser.load(it.avatarUrl) {
            crossfade(true)
            placeholder(R.drawable.ic_launcher_foreground)
            error(R.drawable.ic_launcher_foreground)
            transformations(CircleCropTransformation())
        }
    }
}
package com.radenyaqien.githubuser2023.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.radenyaqien.core.domain.model.GithubUser
import com.radenyaqien.githubuser2023.R
import com.radenyaqien.githubuser2023.databinding.FragmentUsersBinding
import com.radenyaqien.githubuser2023.home.adapter.UserListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UsersFragment : Fragment() {

    private val mAdapter by lazy {
        UserListAdapter {
            findNavController().navigate(R.id.detailGraph, bundleOf("username" to it.username))
        }
    }
    private var _binding: FragmentUsersBinding? = null
    private val binding get() = requireNotNull(_binding)
    private val viewModel by viewModels<HomeViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUsersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvMain.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = mAdapter
            setHasFixedSize(true)
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect {
                    handleError(it.message)
                    handleLoading(it.isLoading)
                    handleData(it.data)
                }
            }

        }
    }

    private fun handleData(data: List<GithubUser>) {
        mAdapter.submitList(data)
    }


    private fun handleLoading(loading: Boolean) {
        binding.progressCircular.isVisible = loading
    }

    private fun handleError(message: String?) {
        if (message != null) {
            Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
            viewModel.clearErrorMessage()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
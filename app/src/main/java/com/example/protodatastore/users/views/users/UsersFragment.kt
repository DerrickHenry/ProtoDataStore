package com.example.protodatastore.users.views.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.protodatastore.R
import com.example.protodatastore.databinding.FragmentUsersBinding
import com.example.protodatastore.users.api.Resource
import com.example.protodatastore.users.api.models.User
import com.example.protodatastore.users.views.adapter.UsersAdapter
import com.example.protodatastore.users.views.user.UserFragment
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UsersFragment : Fragment() {

    private var _binding: FragmentUsersBinding? = null
    private val binding get() = _binding!!
    private val viewModel: UsersViewModel by viewModels()

    private lateinit var adapter: UsersAdapter

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUsersBinding.inflate(inflater, container, false)
        adapter = UsersAdapter(
                onUserClicked = {
                    viewModel.saveUserToProtoDataStore(it)
                    navigateToUserFragment()
                }
        )
        binding.usersRecyclerView.adapter = adapter
        subscribeUi()
        return binding.root
    }

    private fun navigateToUserFragment() {
        parentFragmentManager.beginTransaction().also {
            it.replace(R.id.fragment_container, UserFragment())
            it.addToBackStack(null)
            it.commit()
        }
    }

    private fun subscribeUi() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.users.collect { resource ->
                        when (resource) {
                            is Resource.Error -> showErrorUi(resource.message)
                            is Resource.Loading -> showLoadingUi()
                            is Resource.Success -> submitUsersToAdapter(resource.data)
                        }
                    }
                }
            }
        }
    }

    private fun submitUsersToAdapter(users: List<User>?) {
        binding.progressBar.visibility = View.GONE
    }

    private fun showErrorUi(message: String?) {
        Snackbar.make(binding.root, message ?: "something went wrong", Snackbar.LENGTH_SHORT).show()
        binding.progressBar.visibility = View.GONE
    }

    private fun showLoadingUi() {
        binding.progressBar.visibility = View.VISIBLE
    }
}
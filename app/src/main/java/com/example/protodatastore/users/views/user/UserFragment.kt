package com.example.protodatastore.users.views.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.protodatastore.R
import com.example.protodatastore.databinding.FragmentUserBinding
import com.example.protodatastore.users.api.Resource
import com.example.protodatastore.users.api.models.User
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserFragment : Fragment() {

    private var _binding: FragmentUserBinding? = null
    private val binding get() = _binding!!
    private val viewModel: UserViewModel by viewModels()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserBinding.inflate(inflater, container, false)
        subscribeUi()
        return binding.root
    }

    private fun subscribeUi() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.user.collect { resource ->
                        when (resource) {
                            is Resource.Error -> showErrorUi(resource.message)
                            is Resource.Loading -> showLoadingUi()
                            is Resource.Success -> showUserUi(resource.data)
                        }
                    }
                }
            }
        }
    }

    private fun showUserUi(user: User?) {
        if (user != null) {
            Glide.with(binding.profilePic)
                    .load(user.picture!!.large)
                    .placeholder(R.drawable.ic_downloading_black_24dp)
                    .error(R.drawable.ic_error_black_24dp)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .priority(Priority.HIGH)
                    .into(binding.profilePic)
            binding.name.text = user.name!!.last.plus(", ").plus(user.name.first)
            binding.city.text = user.location!!.city
            binding.email.text = user.email
            binding.joined.text = getString(R.string.date_joined, viewModel.formatDate(user.registered?.date))
        } else {
            showErrorUi(getString(R.string.something_went_wrong))
        }
        binding.progressBar.visibility = View.GONE
    }

    private fun showErrorUi(message: String?) {
        Snackbar.make(binding.root, message ?: getString(R.string.something_went_wrong), Snackbar.LENGTH_SHORT).show()
        binding.progressBar.visibility = View.GONE
    }

    private fun showLoadingUi() {
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
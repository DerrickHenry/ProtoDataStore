package com.example.protodatastore.users.views.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
                    viewModel.getUser().collect { user ->
                        Glide
                                .with(binding.profilePic)
                                .load(user.picture.large)
                                .placeholder(R.drawable.ic_downloading_black_24dp)
                                .error(R.drawable.ic_error_black_24dp)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .priority(Priority.HIGH)
                                .into(binding.profilePic)
                        binding.name.text = user.name.last.plus(", ").plus(user.name.first)
                        binding.city.text = user.location.city
                        binding.email.text = user.email
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
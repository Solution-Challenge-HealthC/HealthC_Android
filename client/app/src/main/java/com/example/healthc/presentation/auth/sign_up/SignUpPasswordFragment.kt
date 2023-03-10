package com.example.healthc.presentation.auth.sign_up

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.healthc.R
import com.example.healthc.databinding.FragmentSignUpPasswordBinding
import com.example.healthc.presentation.auth.AuthViewModel
import com.example.healthc.presentation.auth.AuthViewModel.SignUpUiEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class SignUpPasswordFragment : Fragment() {

    private var _binding: FragmentSignUpPasswordBinding? = null
    private val binding get() = checkNotNull(_binding)

    private val viewModel by activityViewModels<AuthViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up_password, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
    }

    private fun observeData(){
        viewModel.signUpUiEvent.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                when (it) {
                    is SignUpUiEvent.Success -> {
                        navigateToInfo()
                        viewModel.initState()
                    }
                    is SignUpUiEvent.Failure -> {
                        binding.signUpPasswordEditView.error = it.message
                    }
                    is SignUpUiEvent.Unit -> {}
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun navigateToInfo() {
        lifecycleScope.launchWhenStarted {
            val direction = SignUpPasswordFragmentDirections.actionSignUpPasswordFragmentToSignUpInfoFragment()
            findNavController().navigate(direction)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
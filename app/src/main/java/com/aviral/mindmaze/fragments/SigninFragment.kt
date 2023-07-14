package com.aviral.mindmaze.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.aviral.mindmaze.R
import com.aviral.mindmaze.databinding.FragmentSigninBinding
import com.aviral.mindmaze.viewModels.AuthViewModel
import com.google.android.material.snackbar.Snackbar


class SigninFragment : Fragment() {

    private lateinit var binding: FragmentSigninBinding

    private lateinit var viewModel: AuthViewModel
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(activity!!.application)
        )[AuthViewModel::class.java]

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSigninBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        binding.signUpText.setOnClickListener {
            navController.navigate(R.id.action_signinFragment_to_signupFragment)
        }

        binding.signInBtn.setOnClickListener {

            val email = binding.emailEditSignIN.text.toString()
            val password = binding.passEditSignIn.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {

                viewModel.signIn(email, password)

                viewModel.getFirebaseUserLiveData().observe(viewLifecycleOwner
                ) { navController.navigate(R.id.action_signinFragment_to_listFragment) }

            } else {
                Snackbar.make(
                    binding.rootLayout,
                    "Please Provide Valid Credential",
                    Snackbar.LENGTH_SHORT
                ).show()
            }

        }
    }

}
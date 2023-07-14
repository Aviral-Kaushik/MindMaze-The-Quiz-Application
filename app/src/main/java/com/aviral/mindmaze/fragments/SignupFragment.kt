package com.aviral.mindmaze.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.aviral.mindmaze.R
import com.aviral.mindmaze.databinding.FragmentSignupBinding
import com.aviral.mindmaze.viewModels.AuthViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseUser


class SignupFragment : Fragment() {

    private lateinit var binding: FragmentSignupBinding

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
        binding = FragmentSignupBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        binding.signInText.setOnClickListener {
            navController.navigate(R.id.action_signupFragment_to_signinFragment)
        }

        binding.signUpBtn.setOnClickListener {

            val email = binding.editEmailSignUp.text.toString()
            val password = binding.editPassSignUp.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {

                viewModel.signUp(email, password)

                viewModel.getFirebaseUserLiveData().observe(viewLifecycleOwner
                ) { navController.navigate(R.id.action_signupFragment_to_listFragment) }

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
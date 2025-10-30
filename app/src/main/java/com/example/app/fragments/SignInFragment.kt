package com.example.app.fragments

import android.app.Activity.RESULT_OK
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialException
import androidx.fragment.app.Fragment
import com.example.app.MainActivity
import com.example.app.R
import com.example.app.databinding.FragmentSignInBinding
import com.example.app.fragments.sign_up.FirstSignUpFragment
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import androidx.lifecycle.lifecycleScope
import com.example.app.fragments.homepage.HomepageFragment
import com.example.app.fragments.settings.SettingsFragment
import com.example.app.servicies.HashPassword
import com.example.app.servicies.SignIn
import kotlinx.coroutines.launch

class SignInFragment : Fragment() {
    private var _binding: FragmentSignInBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for FragmentSignInBinding must not be null")

    val WEB_CLIENT_ID = "1021259571157-mmlhtcof6su4jb68cj11g1h3f8t3akff.apps.googleusercontent.com"
    private val TAG = "GoogleSignIn"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        val activity = requireActivity() as? MainActivity


        val btnSignIn: MaterialButton = binding.btnSignin
        btnSignIn.setOnClickListener {
//            if (checkFields()) {
            lifecycleScope.launch {
                val user = SignIn.newInstance().signIn(
                    binding.etEmail.text.toString(),
                    binding.etPassword.text.toString(),
                            false
                )
                if (user == null) {
                    binding.tvError.setText("Пользователь не найден")
                } else {
                    activity?.setUser(user)

                    binding.tvError.setText("")


                    activity?.changeFragment(
                        HomepageFragment.newInstance(),
                        R.id.cl_sign_in
                    )

                    activity?.signIn(user.email, user.password)
                }
            }
//            }
        }

        val buttonSignUp: MaterialButton = binding.btnSignUp
        buttonSignUp.setOnClickListener {
            activity?.changeFragment(
                FirstSignUpFragment.newInstance(),
                R.id.cl_sign_in, "sign_in"
            )
        }

        val btnSignIngGoogle = binding.btnGoogleSignInButton
        btnSignIngGoogle.setOnClickListener {
            startGoogleSignIn()
        }

        return binding.root
    }

    private fun startGoogleSignIn() {
        val signInWithGoogleOption = GetSignInWithGoogleOption.Builder(WEB_CLIENT_ID)
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(signInWithGoogleOption)
            .build()

        val credentialManager = CredentialManager.create(requireActivity())

        lifecycleScope.launch {
            try {
                val result: GetCredentialResponse = credentialManager.getCredential(
                    request = request,
                    context = requireActivity()
                )
                handleSignInWithGoogleOption(result)
            } catch (e: GetCredentialException) {
                Log.e(TAG, "Ошибка авторизации: ${e.message}", e)
                binding.tvError.setText("Ошибка авторизации")
            }
        }
    }

    fun handleSignInWithGoogleOption(result: GetCredentialResponse) {
        val credential = result.credential

        when (credential) {
            is CustomCredential -> {
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    try {
                        val googleIdTokenCredential = GoogleIdTokenCredential
                            .createFrom(credential.data)
                    } catch (e: GoogleIdTokenParsingException) {
                        Log.e("Received an invalid google id token response", e.toString())
                    }
                } else {
                    Log.e(TAG, "Unexpected type of credential")
                }
            }

            else -> {
                Log.e(TAG, "Unexpected type of credential")
            }
        }
    }

    private fun checkFields(): Boolean {
        return when {
            !validateEmail() -> {
                false
            }

            binding.etPassword.text.toString().isEmpty() -> {
                binding.etPassword.error = "Обязательное поле"
                false
            }

            else -> {
                binding.etPassword.error = null
                true
            }
        }
    }

    fun validateEmail(): Boolean {
        val emailPattern = Regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
        val email: TextInputEditText = binding.etEmail
        if (!emailPattern.matches(email.text.toString())) {
            email.error = "Введите корректный email"
            return false
        }
        email.error = null
        return true

    }


    companion object {
        @JvmStatic
        fun newInstance() = SignInFragment()
    }
}
package com.example.mobileappproject

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import com.example.mobileappproject.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth

class SignInActivity : BaseActivity() {

    private lateinit var binding: ActivitySignInBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()

        binding.tvRegister.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
            finish()
        }
        binding.tvForgotPassword.setOnClickListener {
            startActivity(Intent(this, ForgetPasswordActivity::class.java))
        }
        binding.btnSignIn.setOnClickListener { signInUser() }
    }

    private fun signInUser() {
        val email = binding.etSignInEmail.text.toString()
        val password = binding.etSignInPassword.text.toString()
        if (validateForm(email, password)) {
            showProgressBar()
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this, "Couldn't login at the moment :(", Toast.LENGTH_SHORT).show()
                    }
                    hideProgressBar()
                }
        }
    }

    private fun validateForm(email: String, password: String): Boolean {
        if (TextUtils.isEmpty(email)) {
            binding.tilEmail.error = "Enter email address"
            return false
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.tilEmail.error = "Enter a valid email address"
            return false
        }
        if (TextUtils.isEmpty(password)) {
            binding.tilPassword.error = "Enter password"
            return false
        }
        return true
    }
}

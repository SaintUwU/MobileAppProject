package com.example.mobileappproject

import android.R.attr.name
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.mobileappproject.databinding.ActivityForgetPasswordBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class ForgetPasswordActivity : BaseActivity() {

    private var binding: ActivityForgetPasswordBinding? = null
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgetPasswordBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        auth = Firebase.auth

        binding?.btnForgotPasswordSubmit?.setOnClickListener{ resetPassword() }

    }

    private fun validateForm(email:String): Boolean{
        return when{
            TextUtils.isEmpty(email)->{
                binding?.tilEmailForgetPassword?.error = "Enter name"
                false
            }
            else ->true
        }
    }

    private fun resetPassword()
    {
        val email = binding?.etForgotPasswordEmail?.text.toString()
        if(validateForm(email))
        {
            showProgressBar()
            auth.sendPasswordResetEmail(email).addOnCompleteListener{ task ->
                if(task.isSuccessful)
                {
                    hideProgressBar()
                    binding?.tilEmailForgetPassword?.visibility = View.GONE
                    binding?.tvSubmitMsg?.visibility = View.VISIBLE
                    binding?.btnForgotPasswordSubmit?.visibility = View.GONE
                }
                else
                {
                    hideProgressBar()
                    showToast(this, "Password can not be rest. Try again Later.")
                }
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    }


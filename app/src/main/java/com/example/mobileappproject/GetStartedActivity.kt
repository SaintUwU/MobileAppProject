package com.example.mobileappproject

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mobileappproject.databinding.ActivityGetStartedBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class GetStartedActivity : AppCompatActivity() {
    private var binding: ActivityGetStartedBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGetStartedBinding.inflate(layoutInflater)

        setContentView(binding?.root)
        binding?.cvGetStarted?.setOnClickListener{
            startActivity(Intent(this, SignInActivity::class.java))
            finish()
        }
        val auth = Firebase.auth
        if(auth.currentUser!= null)
        {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

}
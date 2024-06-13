package com.aglia.doctorchatbot.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.aglia.doctorchatbot.databinding.ActivitySignInBinding
import com.aglia.doctorchatbot.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.button4.setOnClickListener {
            val intent = Intent(this , SignUpActivity::class.java)
            startActivity(intent)
        }
        binding.button2.setOnClickListener {
            val email = binding.emailT.text.toString()
            val password = binding.PassT.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        val exception = task.exception
                        if (exception != null) {
                            when {
                                exception.message!!.contains("password") -> {
                                    Toast.makeText(this, "Password errata. Riprova.", Toast.LENGTH_SHORT).show()
                                }
                                exception.message!!.contains("email") -> {
                                    Toast.makeText(this, "Email errata. Riprova.", Toast.LENGTH_SHORT).show()
                                }
                                else -> {
                                    Toast.makeText(this, exception.localizedMessage, Toast.LENGTH_SHORT).show()
                                }
                            }
                        } else {
                            Toast.makeText(this, "Errore di autenticazione. Riprova.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } else {
                Toast.makeText(this, "Almeno uno dei campi è vuoto", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

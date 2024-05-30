package com.aglia.doctorchatbot.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.aglia.doctorchatbot.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth


class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.button5.setOnClickListener {
            val intent = Intent(this , SignInActivity::class.java)
            startActivity(intent)
        }
        binding.button3.setOnClickListener{
            val email = binding.emailT2.text.toString()
            val pass = binding.passT2.text.toString()
            val cpass = binding.cpassT2.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty() && cpass.isNotEmpty()){
                if (pass==cpass){
                    firebaseAuth.createUserWithEmailAndPassword(email , pass).addOnCompleteListener{
                        if (it.isSuccessful){
                            val intent = Intent(this , SignInActivity::class.java)
                            startActivity(intent)
                        }else{
                            Toast.makeText(this,it.exception.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
                }else{
                    Toast.makeText(this,"Le password non coincidono", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this,"Almeno uno dei campi è vuoto", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
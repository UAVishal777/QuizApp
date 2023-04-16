package com.example.quizzed.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.quizzed.R
import com.google.firebase.auth.FirebaseAuth



class SignupActivity : AppCompatActivity() {

    lateinit var firebaseAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        firebaseAuth=FirebaseAuth.getInstance()
        val btnSignup: Button =findViewById(R.id.btnSignup)
        btnSignup.setOnClickListener{
            signUpUser()
        }

        val btnLogin=findViewById<TextView>(R.id.btnSignup)
        btnLogin.setOnClickListener{
            val intent= Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun signUpUser(){
        val etEmailAddress=findViewById<EditText>(R.id.etEmailAddress)
        val etPassword=findViewById<EditText>(R.id.etPassword)
        val etConfirmPassword=findViewById<EditText>(R.id.etConfirmPassword)

        val email=etEmailAddress.text.toString()
        val password=etPassword.text.toString()
        val confirmPassword=etConfirmPassword.text.toString()


        if(email.isBlank() || password.isBlank() || confirmPassword.isBlank()){
            Toast.makeText(this,"Email and Password can't be blank",Toast.LENGTH_SHORT).show()
            return
        }
        if(password!=confirmPassword){
            Toast.makeText(this,"Password and Confirm Password do not match",Toast.LENGTH_SHORT).show()
            return
        }

        firebaseAuth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(this){
                if(it.isSuccessful){
                    Toast.makeText(this,"Login Successful",Toast.LENGTH_SHORT).show()
                    val intent=Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()

                }
                else{
                    Toast.makeText(this,"Error Creating user.",Toast.LENGTH_SHORT).show()
                }
            }

    }
}
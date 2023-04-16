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

class LoginActivity : AppCompatActivity() {

    lateinit var firebaseAuth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        firebaseAuth=FirebaseAuth.getInstance()

        val btnLogin:Button= findViewById(R.id.button)

        btnLogin.setOnClickListener(){
            login()
        }

        val btnSignup=findViewById<TextView>(R.id.btnSignup)

        btnSignup.setOnClickListener{
            val intent=Intent(this, SignupActivity::class.java)
            startActivity(intent)
            finish()
        }


    }
    private fun login(){
        val etEmailAddress=findViewById<EditText>(R.id.etEmailAddress)
        val etPassword=findViewById<EditText>(R.id.etPassword)

        val email=etEmailAddress.text.toString()
        val password=etPassword.text.toString()

        if(email.isBlank() || password.isBlank()){
            Toast.makeText(this,"Email and Password can't be blank",Toast.LENGTH_SHORT).show()
            return
        }

        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this){
            if(it.isSuccessful){
                Toast.makeText(this,"Success",Toast.LENGTH_SHORT).show()
                val intent=Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            else{
                Toast.makeText(this,"Authentication Failed",Toast.LENGTH_SHORT).show()
            }
        }
    }
}
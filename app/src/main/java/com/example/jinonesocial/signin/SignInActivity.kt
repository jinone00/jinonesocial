package com.example.jinonesocial.signin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.jinonesocial.R
import com.example.jinonesocial.UserData
import com.example.jinonesocial.databinding.ActivityLoginBinding
import com.example.jinonesocial.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class SignInActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivitySignInBinding
    private lateinit var firestore : FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        firestore = FirebaseFirestore.getInstance()

        binding.btnSignIn.setOnClickListener {
            var userData = UserData()
            userData.uid = binding.inputId.text.toString()
            userData.password = binding.inputPassword.text.toString()
            userData.nickname = binding.inputNickname.text.toString()
            userData.phoneNumber = binding.inputPhoneNumber.text.toString()

            firestore.collection("userData").document("${userData.uid}").set(userData)
            Toast.makeText(this,"저장완료",Toast.LENGTH_SHORT).show()
        }
    }
}
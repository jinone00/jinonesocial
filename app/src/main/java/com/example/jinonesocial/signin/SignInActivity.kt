package com.example.jinonesocial.signin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.jinonesocial.LoginActivity
import com.example.jinonesocial.MainActivity
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

        var idInputText = binding.inputId.text
        var passText = binding.inputPassword.text
        var passTextConfirm = binding.inputPasswordCofirm.text
        var passCheckText = binding.passCheckText.text


        auth = Firebase.auth
        firestore = FirebaseFirestore.getInstance()

        binding.btnSignIn.setOnClickListener {
            val userData = UserData()
            userData.uid = binding.inputId.text.toString()
            userData.password = binding.inputPassword.text.toString()
            userData.nickname = binding.inputNickname.text.toString()
            userData.phoneNumber = binding.inputPhoneNumber.text.toString()

            firestore.collection("userData").document("${userData.uid}").set(userData)
            Toast.makeText(this,"저장완료",Toast.LENGTH_SHORT).show()
        }

        if (passText == passTextConfirm ) {
            passCheckText = "비밀번호가 일치합니다!"
        } else {
            passCheckText = "비밀번호가 일치하지 않습니다."
        }




        binding.btnSignIn.setOnClickListener {
            if (idInputText.isEmpty() || passText.isEmpty() || passTextConfirm.isEmpty() || passCheckText.isEmpty()){
                Toast.makeText(this,"빈칸을 채워주시오.",Toast.LENGTH_LONG).show()
            }
            else if (passText.toString() == passTextConfirm.toString()){
                Toast.makeText(this,"회원가입 성공!",Toast.LENGTH_LONG).show()
                createAccount(idInputText.toString(), passText.toString())
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            } else{
                Toast.makeText(this,"비밀번호가 일치하지 않습니다",Toast.LENGTH_LONG).show()
            }

        }


    }
    private fun createAccount(email: String, password: String) {

        if (email.isNotEmpty() && password.isNotEmpty()) {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            this, "계정 생성 완료.",
                            Toast.LENGTH_SHORT
                        ).show()

                    } else {
                        Toast.makeText(
                            this, "계정 생성 실패",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }
}
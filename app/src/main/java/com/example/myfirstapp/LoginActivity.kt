package com.example.myfirstapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myfirstapp.CadastroActivity.Users

class LoginActivity : AppCompatActivity() {

    private lateinit var loginBtn : Button
    private lateinit var cadastroBtn : Button
    private lateinit var backArrow : ImageView
    private lateinit var userInput : EditText
    private lateinit var senhaInput : EditText

    private val users = mutableListOf<Users>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginBtn
        cadastroBtn
        backArrow
        userInput
        senhaInput



    }
}
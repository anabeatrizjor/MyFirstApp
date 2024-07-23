package com.example.myfirstapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class CadastroActivity : AppCompatActivity() {

    data class Users (val nome : String , val user : String , val senha : String)

    companion object {
        private val users = mutableListOf<Users>()
    }

    private lateinit var cadastroBtn : Button
    private lateinit var backArrow : ImageView
    private lateinit var nomeInput : EditText
    private lateinit var userInput : EditText
    private lateinit var senhaInput : EditText

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        cadastroBtn = findViewById(R.id.cadastroBtnCadastro)
        backArrow = findViewById(R.id.backArrowCadastro)
        nomeInput = findViewById(R.id.nomeInputCadastro)
        userInput = findViewById(R.id.userInputCadastro)
        senhaInput = findViewById(R.id.senhaInputCadastro)

        backArrow.setOnClickListener {
            val intent = Intent (this@CadastroActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        cadastroBtn.setOnClickListener {
            cadastrarConta()
        }
    }

    private fun cadastrarConta() {

        val nome = nomeInput.text.toString()
        val user = userInput.text.toString()
        val senha = senhaInput.text.toString()

        val senhaRegex = Regex("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%¨&*(){};;~^_]).{6,}\$")

        if (nome.isEmpty() || user.isEmpty() || senha.isEmpty()) {
            exibirDialogo("Todos os campos devem ser preenchidos.")
        }


        if (!senhaRegex.matches(senha)) {
            exibirDialogo("A senha deve conter no mínimo 6 caractéres, contendo pelo menos uma letra maiúscula, uma letra minúscula, um caractére especial e um número.")
        }

        val newUser = Users(nome, user, senha)
        users.add(newUser)

        Toast.makeText(this, "Cadastro realizado com sucesso", Toast.LENGTH_SHORT).show()

        val intent = Intent (this@CadastroActivity, LoginActivity::class.java)
        startActivity(intent)
        finish()

    }

    private fun exibirDialogo (mensagem: String) {
        AlertDialog.Builder(this)
            .setTitle("Erro de Cadastro")
            .setMessage(mensagem)
            .setPositiveButton("OK") { dialog , _ ->
                dialog.dismiss()
            }
            .show()
    }
}
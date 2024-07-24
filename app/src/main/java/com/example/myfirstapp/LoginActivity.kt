package com.example.myfirstapp

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.myfirstapp.CadastroActivity.Users

class LoginActivity : AppCompatActivity() {

    private lateinit var loginBtn: Button
    private lateinit var cadastroBtn: Button
    private lateinit var userInput: EditText
    private lateinit var senhaInput: EditText

    private val users = mutableListOf<Users>()

    private var numTentativas = 0
    private var bloqueioAtivo = false
    private val MAX_TENTATIVAS = 5
    private val NUM_BLOQUEIO_MS = 10000
    private var bloqueioTimer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginBtn = findViewById(R.id.loginBtnLogin)
        cadastroBtn = findViewById(R.id.cadastroBtnLogin)
        userInput = findViewById(R.id.userInputLogin)
        senhaInput = findViewById(R.id.senhaInputLogin)

        cadastroBtn.setOnClickListener {
            val intent = Intent(this@LoginActivity, CadastroActivity::class.java)
            startActivity(intent)
            finish()
        }

        loginBtn.setOnClickListener {
            realizarLogin()
        }

        setupFieldFocusListeners()
    }

    private fun setupFieldFocusListeners() {
        userInput.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                userInput.background = ContextCompat.getDrawable(this, R.drawable.border_selector)
            } else {
                if (userInput.text.toString().isEmpty()) {
                    userInput.background = ContextCompat.getDrawable(this, R.drawable.shape_campo_error)
                }
            }
        }

        senhaInput.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                senhaInput.background = ContextCompat.getDrawable(this, R.drawable.border_selector)
            } else {
                if (senhaInput.text.toString().isEmpty()) {
                    senhaInput.background = ContextCompat.getDrawable(this, R.drawable.shape_campo_error)
                }
            }
        }
    }

    private fun realizarLogin() {
        if (bloqueioAtivo) {
            exibirDialogo("Número máximo de tentativas atingido. Tente novamente em alguns segundos.")
            return
        }

        val user = userInput.text.toString()
        val senha = senhaInput.text.toString()

        var errorUser = false
        var errorSenha = false

        if (user.isEmpty()) {
            userInput.background = ContextCompat.getDrawable(this, R.drawable.shape_campo_error)
            errorUser = true
        }

        if (senha.isEmpty()) {
            senhaInput.background = ContextCompat.getDrawable(this, R.drawable.shape_campo_error)
            errorSenha = true
        }

        if (errorUser || errorSenha) {
            return
        }

        val userFinded = users.find { it.user == user && it.senha == senha }

        if (userFinded != null) {
            Toast.makeText(this, "Usuário encontrado com sucesso", Toast.LENGTH_SHORT).show()
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            exibirDialogo("Usuário não encontrado. Verifique se o usuário e a senha estão corretos.")
            numTentativas++
            if (numTentativas >= MAX_TENTATIVAS) {
                bloquearAppPorUmTempo()
            }
        }
    }

    private fun exibirDialogo(mensagem: String) {
        AlertDialog.Builder(this)
            .setTitle("Erro de Login")
            .setMessage(mensagem)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun bloquearAppPorUmTempo() {
        bloqueioAtivo = true
        numTentativas = 0

        loginBtn.isEnabled = false
        cadastroBtn.isEnabled = false

        bloqueioTimer = object : CountDownTimer(NUM_BLOQUEIO_MS.toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                // Implementar contagem regressiva se necessário
            }

            override fun onFinish() {
                bloqueioAtivo = false
                loginBtn.isEnabled = true
                cadastroBtn.isEnabled = true
                exibirDialogo("Agora você pode realizar o login novamente.")
            }
        }.start()
    }
}

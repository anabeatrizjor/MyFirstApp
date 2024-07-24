package com.example.mysecondapp

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
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class CadastroActivity : AppCompatActivity() {

    data class Users(val nome: String, val user: String, val senha: String)

    companion object {
        val users = mutableListOf<Users>()
    }

    private lateinit var cadastroBtn: Button
    private lateinit var backArrow: ImageView
    private lateinit var nomeInput: EditText
    private lateinit var userInput: EditText
    private lateinit var senhaInput: EditText
    private lateinit var senhaConfirm: EditText

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        cadastroBtn = findViewById(R.id.cadastroBtnCadastro)
        backArrow = findViewById(R.id.backArrowCadastro)
        nomeInput = findViewById(R.id.nomeInputCadastro)
        userInput = findViewById(R.id.userInputCadastro)
        senhaInput = findViewById(R.id.senhaInputCadastro)
        senhaConfirm = findViewById(R.id.senhaConfirmInputCadastro)

        backArrow.setOnClickListener {
            val intent = Intent(this@CadastroActivity, LoginActivity::class.java)
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
        val senhaConfirmation = senhaConfirm.text.toString()

        val senhaRegex = Regex("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#\$%¨&*(){};;~^_]).{6,}\$")

        val senhaConfirmed = (senhaConfirmation == senha)

        var errorAll = false
        var errorSenha = false

        if (nome.isEmpty() || user.isEmpty() || senha.isEmpty()) {
            exibirDialogo("Todos os campos devem ser preenchidos.")
            errorAll = true
        }

        if (!senhaRegex.matches(senha)) {
            exibirDialogo("A senha deve conter no mínimo 6 caracteres, contendo pelo menos uma letra maiúscula, uma letra minúscula, um caractere especial e um número.")
            errorSenha = true
        }

        if (!senhaConfirmed) {
            exibirDialogo("A senha a ser preenchida neste campo deve estar exatamente igual à que foi inserida no campo anterior.")
            errorSenha = true
        }

        if (errorAll) {
            nomeInput.background = ContextCompat.getDrawable(this, R.drawable.shape_campo_error)
            userInput.background = ContextCompat.getDrawable(this, R.drawable.shape_campo_error)
            senhaInput.background = ContextCompat.getDrawable(this, R.drawable.shape_campo_error)
            senhaConfirm.background = ContextCompat.getDrawable(this, R.drawable.shape_campo_error)
            return
        }

        if (errorSenha) {
            senhaInput.background = ContextCompat.getDrawable(this, R.drawable.shape_campo_error)
            senhaConfirm.background = ContextCompat.getDrawable(this, R.drawable.shape_campo_error)
            return
        }

        nomeInput.background = ContextCompat.getDrawable(this, R.drawable.border_selector)
        userInput.background = ContextCompat.getDrawable(this, R.drawable.border_selector)
        senhaInput.background = ContextCompat.getDrawable(this, R.drawable.border_selector)
        senhaConfirm.background = ContextCompat.getDrawable(this, R.drawable.border_selector)

        val acessoAdmin = Users("Rebeka Kariny", "rebekakariny69", "rebek@1969")
        users.add(acessoAdmin)
        val newUser = Users(nome, user, senha)
        users.add(newUser)

        Toast.makeText(this, "Cadastro realizado com sucesso", Toast.LENGTH_SHORT).show()

        val intent = Intent(this@CadastroActivity, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun exibirDialogo(mensagem: String) {
        AlertDialog.Builder(this)
            .setTitle("Erro de Cadastro")
            .setMessage(mensagem)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}
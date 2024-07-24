package com.example.mysecondapp
//teste
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.animation.AnimationUtils
import java.util.logging.Handler

class SplashScreen : AppCompatActivity() {

    private val splashScreenStarted = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val logo = findViewById<ImageView>(R.id.logo)

        android.os.Handler().postDelayed({
            val intent = Intent (this@SplashScreen, LoginActivity::class.java)
            startActivity(intent)
            finish()
        },splashScreenStarted.toLong())
    }
}

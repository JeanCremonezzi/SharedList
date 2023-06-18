package br.edu.ifsp.scl.sharedlist.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.edu.ifsp.scl.sharedlist.R
import br.edu.ifsp.scl.sharedlist.databinding.ActivityLoginBinding
import br.edu.ifsp.scl.sharedlist.databinding.ActivityMainBinding

class LoginActivity : AppCompatActivity() {
    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}
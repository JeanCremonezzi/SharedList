package br.edu.ifsp.scl.sharedlist.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import br.edu.ifsp.scl.sharedlist.R
import br.edu.ifsp.scl.sharedlist.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {
    private val binding: ActivitySignupBinding by lazy {
        ActivitySignupBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        with (binding) {
            signUpBtn.setOnClickListener {
                val email = emailEt.text.toString();
                val password = emailEt.text.toString();

                FirebaseAuth.getInstance()
                    .createUserWithEmailAndPassword(email, password)
                    .addOnSuccessListener {
                        Toast.makeText(this@SignUpActivity, "User registered!", Toast.LENGTH_LONG).show()
                        finish()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this@SignUpActivity, "Failure while registering user!", Toast.LENGTH_LONG).show()
                    }
            }
        }
    }
}
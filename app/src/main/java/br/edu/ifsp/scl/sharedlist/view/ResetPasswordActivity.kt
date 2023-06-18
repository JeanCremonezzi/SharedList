package br.edu.ifsp.scl.sharedlist.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import br.edu.ifsp.scl.sharedlist.databinding.ActivityResetPasswordBinding
import com.google.firebase.auth.FirebaseAuth

class ResetPasswordActivity : AppCompatActivity() {
    private val binding: ActivityResetPasswordBinding by lazy {
        ActivityResetPasswordBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        with (binding) {
            sendEmailBtn.setOnClickListener {
                val email = emailEt.text.toString()

                FirebaseAuth.getInstance()
                    .sendPasswordResetEmail(email)
                    .addOnSuccessListener {
                        Toast.makeText(this@ResetPasswordActivity, "Email sent!", Toast.LENGTH_LONG).show()
                        finish()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this@ResetPasswordActivity, "Failure while sending email!", Toast.LENGTH_LONG).show()
                    }
            }
        }
    }
}
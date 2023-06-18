package br.edu.ifsp.scl.sharedlist.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import br.edu.ifsp.scl.sharedlist.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class LoginActivity : BaseActivity() {
    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    private lateinit var arl: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        with (binding) {
            signUpBtn.setOnClickListener {
                startActivity(Intent(this@LoginActivity, SignUpActivity::class.java))
            }

            googleLoginBtn.setOnClickListener {
                val gsa: GoogleSignInAccount? = GoogleSignIn.getLastSignedInAccount(this@LoginActivity)

                if (gsa == null) {
                    arl.launch(googleSignInClient.signInIntent)
                } else {
                    openMainActivity()
                }
            }
        }

        arl = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                val gsa: GoogleSignInAccount = task.result
                val credential = GoogleAuthProvider.getCredential(gsa.idToken, null)

                FirebaseAuth.getInstance()
                    .signInWithCredential(credential)
                    .addOnSuccessListener {
                        Toast.makeText(
                            this,
                            "User authenticated!",
                            Toast.LENGTH_LONG
                        ).show()
                        openMainActivity()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Failed to authenticate user!", Toast.LENGTH_LONG).show()
                    }
            }
        }
    }
    private fun openMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun onStart() {
        super.onStart()
        if (FirebaseAuth.getInstance().currentUser != null) {
            openMainActivity()
        }
    }
}
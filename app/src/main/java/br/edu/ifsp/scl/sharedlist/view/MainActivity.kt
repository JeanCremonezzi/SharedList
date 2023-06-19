package br.edu.ifsp.scl.sharedlist.view

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import br.edu.ifsp.scl.sharedlist.R
import br.edu.ifsp.scl.sharedlist.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : BaseActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var arl: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        arl = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) { result ->
            if ( result.resultCode == RESULT_OK) {

            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.signOut -> {
                FirebaseAuth.getInstance().signOut()
                googleSignInClient.signOut()

                startActivity(Intent(this, LoginActivity::class.java))
                true
            }

            R.id.addTask -> {
                arl.launch(Intent(this, TaskActivity::class.java))
                true
            }

            else -> false
        }
    }
}
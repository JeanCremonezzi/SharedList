package br.edu.ifsp.scl.sharedlist.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import br.edu.ifsp.scl.sharedlist.R
import br.edu.ifsp.scl.sharedlist.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : BaseActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
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

            else -> false
        }
    }
}
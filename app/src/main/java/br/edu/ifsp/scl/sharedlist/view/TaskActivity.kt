package br.edu.ifsp.scl.sharedlist.view

import android.os.Bundle
import br.edu.ifsp.scl.sharedlist.databinding.ActivityTaskBinding

class TaskActivity : BaseActivity() {
    private val binding: ActivityTaskBinding by lazy {
        ActivityTaskBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}
package br.edu.ifsp.scl.sharedlist.view

import android.os.Bundle
import br.edu.ifsp.scl.sharedlist.databinding.ActivityTaskBinding
import br.edu.ifsp.scl.sharedlist.model.Task
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.Date
import java.util.UUID

class TaskActivity : BaseActivity() {
    private val binding: ActivityTaskBinding by lazy {
        ActivityTaskBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        with (binding) {
            addTaskBtn.setOnClickListener {
                val task = Task(
                    id = generateId(),
                    title = titleEt.text.toString(),
                    description = descriptionEt.text.toString(),
                    createBy = FirebaseAuth.getInstance().currentUser?.email.toString(),
                    createAt = SimpleDateFormat("dd/MM/yyyy").format(Date()),
                    estimatedFinishDate = estimatedFinishDateEt.text.toString()
                )

                val resultIntent = intent
                resultIntent.putExtra(EXTRA_TASK, task)
                setResult(RESULT_OK, resultIntent)

                finish()
            }
        }
    }

    private fun generateId(): String {
        return UUID.randomUUID().toString()
    }
}
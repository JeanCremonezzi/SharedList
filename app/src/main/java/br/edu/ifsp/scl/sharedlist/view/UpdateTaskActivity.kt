package br.edu.ifsp.scl.sharedlist.view

import android.os.Build
import android.os.Bundle
import br.edu.ifsp.scl.sharedlist.databinding.ActivityUpdateTaskBinding
import br.edu.ifsp.scl.sharedlist.model.Task

class UpdateTaskActivity : BaseActivity() {
    private val binding: ActivityUpdateTaskBinding by lazy {
        ActivityUpdateTaskBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val receivedTask = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(EXTRA_TASK, Task::class.java)
        } else {
            intent.getParcelableExtra(EXTRA_TASK)
        }

        receivedTask?.let{_receivedTask ->
            with (binding) {
                descriptionEt.setText(_receivedTask.description)
                estimatedFinishDateEt.setText(_receivedTask.estimatedFinishDate)

                updateTaskBtn.setOnClickListener {
                    val task = receivedTask

                    task.description = descriptionEt.text.toString()
                    task.estimatedFinishDate = estimatedFinishDateEt.text.toString()

                    val resultIntent = intent
                    resultIntent.putExtra(EXTRA_TASK, task)
                    setResult(RESULT_OK, resultIntent)

                    finish()
                }
            }
        }
    }
}
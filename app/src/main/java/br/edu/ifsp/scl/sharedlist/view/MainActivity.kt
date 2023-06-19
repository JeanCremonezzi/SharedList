package br.edu.ifsp.scl.sharedlist.view

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import br.edu.ifsp.scl.sharedlist.R
import br.edu.ifsp.scl.sharedlist.adapter.OnTaskClickListenner
import br.edu.ifsp.scl.sharedlist.adapter.TaskAdapter
import br.edu.ifsp.scl.sharedlist.controller.TaskController
import br.edu.ifsp.scl.sharedlist.databinding.ActivityMainBinding
import br.edu.ifsp.scl.sharedlist.model.Task
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.Date

class MainActivity : BaseActivity(), OnTaskClickListenner {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var arl: ActivityResultLauncher<Intent>

    private val taskController: TaskController by lazy {
        TaskController(this)
    }

    private val taskAdapter: TaskAdapter by lazy {
        TaskAdapter(taskList, this)

    }

    private val taskList: MutableList<Task> = mutableListOf()

    lateinit var updateViewsHandler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        taskController.getTasks()
        binding.tasksRecVw.layoutManager = LinearLayoutManager(this)
        binding.tasksRecVw.adapter = taskAdapter

        updateViewsHandler = Handler(Looper.myLooper()!!) { msg ->
            taskController.getTasks()
            true
        }

        arl = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val task = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    result.data?.getParcelableExtra(EXTRA_TASK, Task::class.java)
                } else {
                    result.data?.getParcelableExtra(EXTRA_TASK)
                }

                task?.let {_task ->
                    val position = taskList.indexOfFirst { it.id == _task.id }

                    if (position != -1) {
                        taskList[position] = _task
                        taskController.editTask(_task)
                        taskAdapter.notifyDataSetChanged()
                        Toast.makeText(this, "Task updated!", Toast.LENGTH_LONG).show()
                        
                    } else {
                        taskController.insertTask(_task)
                        Toast.makeText(this, "Task created!", Toast.LENGTH_LONG).show()
                        updateViewsHandler.sendMessageDelayed(Message(), 3000)
                    }
                }
            }
        }

        updateViewsHandler.sendMessageDelayed(Message(), 3000)
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

    override fun onEditMenuItemClick(position: Int) {
        val task = taskList[position]
        val taskIntent = Intent(this@MainActivity, UpdateTaskActivity::class.java)
        taskIntent.putExtra(EXTRA_TASK, task)
        arl.launch(taskIntent)
    }

    override fun onRemoveMenuItemClick(position: Int) {
        val task = taskList[position]
        taskList.removeAt(position)
        taskController.removeTask(task)
        taskAdapter.notifyDataSetChanged()
        Toast.makeText(this, "Task deleted!", Toast.LENGTH_LONG).show()
    }

    override fun onCompleteMenuItemClick(position: Int) {
        val task = taskList[position]
        task.finishedBy = FirebaseAuth.getInstance().currentUser!!.email.toString()
        task.finishedAt = SimpleDateFormat("dd/MM/yyyy").format(Date())
        taskController.editTask(task)
        taskList[position] = task
        taskAdapter.notifyDataSetChanged()
        Toast.makeText(this, "Task Finished!", Toast.LENGTH_LONG).show()
    }

    fun updateTaskList(_taskList: MutableList<Task>) {
        taskList.clear()
        taskList.addAll(_taskList)
        taskAdapter.notifyDataSetChanged()
    }
}
package br.edu.ifsp.scl.sharedlist.controller

import br.edu.ifsp.scl.sharedlist.model.Task
import br.edu.ifsp.scl.sharedlist.model.TaskDao
import br.edu.ifsp.scl.sharedlist.model.TaskDaoFb
import br.edu.ifsp.scl.sharedlist.view.MainActivity

class TaskController(private val mainActivity: MainActivity) {
    private val dao: TaskDao = TaskDaoFb()

    fun insertTask(task: Task) {
        Thread {
            dao.createTask(task)
        }.start()
    }

    fun getTask(id: String) = dao.getTask(id)

    fun getTasks() {
        Thread {
            val list = dao.getTasks()
            mainActivity.runOnUiThread {
                //mainActivity.updateTaskList(list)
            }
        }.start()
    }

    fun editTask(task: Task) {
        Thread {
            dao.updateTask(task)
        }.start()
    }

    fun removeTask(task: Task) {
        Thread {
            dao.deleteTask(task)
        }.start()
    }
}
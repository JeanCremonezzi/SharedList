package br.edu.ifsp.scl.sharedlist.model

import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue

class TaskDaoFb: TaskDao {
    private val DB_REFERENCE = "taskList"
    private val db = FirebaseDatabase.getInstance().getReference(DB_REFERENCE)

    private val taskList: MutableList<Task> = mutableListOf()

    init {
        db.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val task: Task? = snapshot.getValue<Task>()

                task?.let { _task ->
                    if (!taskList.any { _task.id == it.id }) {
                        taskList.add(_task)
                    }
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                val task: Task? = snapshot.getValue<Task>()

                task?.let { _task ->
                    taskList[taskList.indexOfFirst { _task.id == it.id }] = _task
                }
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                val task: Task? = snapshot.getValue<Task>()

                task?.let { _task ->
                    taskList.remove(_task)
                }
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                // NA
            }

            override fun onCancelled(error: DatabaseError) {
                // NA
            }

        })

        db.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val taskHashMap = snapshot.getValue<HashMap<String, Task>>()
                taskList.clear()

                taskHashMap?.values?.forEach {
                    taskList.add(it)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // NA
            }
        })
    }

    override fun createTask(task: Task) {
        createOrUpdateTask(task)
    }

    override fun getTask(id: String): Task {
        return taskList[taskList.indexOfFirst { id == it.id }]
    }

    override fun getTasks(): MutableList<Task> {
        return taskList;
    }

    override fun updateTask(task: Task): Int {
        createOrUpdateTask(task)
        return 1
    }

    override fun deleteTask(task: Task): Int {
        db.child(task.id).removeValue()
        return 1
    }

    override fun countByTitle(title: String): Int {
        return taskList.count { it.title == title }
    }

    private fun createOrUpdateTask(task: Task) {
        db.child(task.id).setValue(task)
    }
}
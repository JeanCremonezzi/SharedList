package br.edu.ifsp.scl.sharedlist.model

import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import androidx.room.Insert

interface TaskDao {
    @Insert
    fun createTask(task: Task)

    @Query("SELECT * FROM Task WHERE id = :id")
    fun getTask(id: String): Task?

    @Query("SELECT * FROM Task")
    fun getTasks(): MutableList<Task>

    @Update
    fun updateTask(task: Task): Int

    @Delete
    fun deleteTask(task: Task): Int

    @Query("SELECT COUNT(*) FROM Task WHERE title = :title")
    fun countByTitle(title: String): Int
}
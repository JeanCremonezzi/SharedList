package br.edu.ifsp.scl.sharedlist.adapter

import android.graphics.Paint
import android.view.*
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.edu.ifsp.scl.sharedlist.databinding.TaskItemBinding
import br.edu.ifsp.scl.sharedlist.model.Task

class TaskAdapter (
    private val taskList: MutableList<Task>,
    private val onTaskClickListenner: OnTaskClickListenner
): RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    inner class TaskViewHolder(tileTaskBinding: TaskItemBinding):
        RecyclerView.ViewHolder(tileTaskBinding.root), View.OnCreateContextMenuListener {

        val titleTxt: TextView = tileTaskBinding.titleTxt
        val descriptionTxt: TextView = tileTaskBinding.descriptionTxt
        val estimatedFinishDateTxt: TextView = tileTaskBinding.estimatedFinishDateTxt
        val createdByTxt: TextView = tileTaskBinding.createdByTxt
        val createdAtTxt: TextView = tileTaskBinding.createdAtTxt
        val finishedByTxt: TextView = tileTaskBinding.finishedByTxt
        val finishedAtTxt: TextView = tileTaskBinding.finishedAtTxt
        var taskPosition= -1

        init{
            tileTaskBinding.root.setOnCreateContextMenuListener(this)
        }
        override fun onCreateContextMenu(
            menu: ContextMenu?,
            v: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {
            val task = taskList[taskPosition]
            if (task.finishedBy.isEmpty()) {
                menu?.add(Menu.NONE, 0, 0, "Finish")?.setOnMenuItemClickListener {
                    if (taskPosition != -1) {
                        onTaskClickListenner.onCompleteMenuItemClick(taskPosition)
                    }

                    true
                }

                menu?.add(Menu.NONE, 1, 1, "Delete")?.setOnMenuItemClickListener {
                    if (taskPosition != -1) {
                        onTaskClickListenner.onRemoveMenuItemClick(taskPosition)
                    }

                    true
                }

                menu?.add(Menu.NONE, 2, 2, "Update")?.setOnMenuItemClickListener {
                    if (taskPosition != -1) {
                        onTaskClickListenner.onEditMenuItemClick(taskPosition)
                    }

                    true
                }
            }
        }
    }

    override fun getItemCount(): Int = taskList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val tileTaskBinding = TaskItemBinding.inflate(LayoutInflater.from(parent.context))
        val taskViewHolder = TaskViewHolder(tileTaskBinding)

        return taskViewHolder
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = taskList[position]

        if (task.finishedBy.isNotEmpty()) {
            holder.titleTxt.paintFlags = holder.titleTxt.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            holder.titleTxt.paintFlags = holder.titleTxt.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }

        holder.titleTxt.text = task.title
        holder.descriptionTxt.text = task.description
        holder.estimatedFinishDateTxt.text = "Due date: ${task.estimatedFinishDate}"
        holder.createdByTxt.text = "Created by ${task.createBy}"
        holder.createdAtTxt.text = "Created by ${task.createAt}"
        holder.finishedByTxt.text = "Finished By ${task.finishedBy}"
        holder.finishedAtTxt.text = "Finished At ${task.finishedAt}"

        holder.taskPosition = position
    }
}
package com.example.myapplication


import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemTodoBinding

// Adapter requires 3 methods - onCreate, onBind and size of list
class TodoAdapter(
    private val todos: MutableList<Todo>
) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    class TodoViewHolder(val binding: ItemTodoBinding) : RecyclerView.ViewHolder(binding.root)

    fun addTodo(todo: Todo) {
        todos.add(todo)
        notifyItemInserted(todos.size - 1)
    }

    fun deleteDoneTodos() {
        todos.removeAll { todo ->
            todo.isChecked
        }
        notifyDataSetChanged()
    }

    // viewHolder is the view component - which is a todo item in our case
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {

        // layout inflater is used to convert xml to view item
        // it is used to dynamically create views and then add them to layout
        return TodoViewHolder(
            ItemTodoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    private fun toggleStrikeThrough(tvTodoTitle: TextView, isChecked: Boolean) {
        if(isChecked) {
            // pain flags is a set of bits, STRIKE_THRU_TEXT_FLAG is one such bit
            // so we use bitwise or to retain other flags
            tvTodoTitle.paintFlags = tvTodoTitle.paintFlags or STRIKE_THRU_TEXT_FLAG
        } else {
            tvTodoTitle.paintFlags = tvTodoTitle.paintFlags and STRIKE_THRU_TEXT_FLAG.inv()
        }
    }

    // when view is bind to screen, we have its holder and position in todos
    // so we set its properties - if it is checked or not, etc
    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val currTodo = todos[position]
        holder.itemView.apply {

            holder.binding.tvTodoItem.text = currTodo.title
            holder.binding.cbDone.isChecked = currTodo.isChecked

            toggleStrikeThrough(holder.binding.tvTodoItem, currTodo.isChecked)

            holder.binding.cbDone.setOnCheckedChangeListener { _, isChecked ->
                toggleStrikeThrough(holder.binding.tvTodoItem, isChecked)
                currTodo.isChecked = !currTodo.isChecked
            }
        }
    }

    override fun getItemCount(): Int {
        return todos.size
    }
}



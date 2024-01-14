package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/*
* Our App starts from MainActivity(as described in AndroidManifest.xml)
*
* Activity = Screen
*
* We define the layout inside activity
* Layouts are of types: Constrained, Grid, Linear, etc
*
* Activity has lifecycle methods like - onCreate, onStart, onPause, onDestroy, etc
*
* We create components in our xml and give them id. Then we write logic to interact with
* these components in kotlin. Then finally map these logic to the components
*
* In this project we are creating a todo app.
* We can add, delete todos
*
* For this we will first create front screen where we have a text box, and two buttons
* one to add and one to delete already done todo
* We use recyclerView for it. RecyclerView is like a ListView but only renders the list
* items that are currently visible in our screen.
* For this it has parameters:
* ViewHolder: The view item which will contain one todo item
* Position: Position of this item in list
*
* Also, we create a separate xml for single todo item
*
* */

class MainActivity : ComponentActivity() {

    // declare adapter now - initialize later
    private lateinit var todoAdapter: TodoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        // load saved state of application
        super.onCreate(savedInstanceState)

        // use activity_main as content view
        setContentView(R.layout.activity_main)

        // create empty list of todos initially
        todoAdapter = TodoAdapter(mutableListOf())

        // get item by id - like we do in js DOM
        val rvTodoItems: RecyclerView = findViewById(R.id.rvTodoItems)

        rvTodoItems.adapter = todoAdapter
        rvTodoItems.layoutManager = LinearLayoutManager(this)

        val btnAddTodo: Button = findViewById(R.id.btnAddTodo)

        // add todo in list if text not empty
        btnAddTodo.setOnClickListener {
            val todoTitleWidget: EditText = findViewById(R.id.etTodoTitle)
            val todoTitle = todoTitleWidget.text.toString()
            if(todoTitle.isNotEmpty()) {
                val todo = Todo(todoTitle)
                todoAdapter.addTodo(todo)
                todoTitleWidget.text.clear()

            }
        }

        val btnDeleteWidget: Button = findViewById(R.id.btnDeleteTodo)

        // associate click listener for delete button also
        btnDeleteWidget.setOnClickListener {
            todoAdapter.deleteDoneTodos()
        }
    }
}

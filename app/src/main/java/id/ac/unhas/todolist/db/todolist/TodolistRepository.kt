package id.ac.unhas.todolist.db.todolist

import android.app.Application
import androidx.lifecycle.LiveData
import id.ac.unhas.todolist.db.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class TodolistRepository(application: Application) {

    private val todoListDao: TodolistDao
    private val todos: LiveData<List<Todolist>>

    init {
        val database = AppDatabase.getInstance(application.applicationContext)
        todoListDao = database!!.todoDao()
        todos = todoListDao.getTodos()
    }

    fun getTodos(): LiveData<List<Todolist>> {
        return todos
    }

    fun insert(todo: Todolist) = runBlocking {
        this.launch(Dispatchers.IO) {
            todoListDao.insertTodo(todo)
        }
    }

    fun update(todo: Todolist) = runBlocking {
        this.launch(Dispatchers.IO) {
            todoListDao.updateTodo(todo)
        }
    }

    fun delete(todo: Todolist) {
        runBlocking {
            this.launch(Dispatchers.IO) {
                todoListDao.deleteTodo(todo)
            }
        }
    }
}
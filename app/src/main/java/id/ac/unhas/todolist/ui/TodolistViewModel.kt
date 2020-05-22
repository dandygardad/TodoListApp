package id.ac.unhas.todolist.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import id.ac.unhas.todolist.db.todolist.Todolist
import id.ac.unhas.todolist.db.todolist.TodolistRepository

class TodolistViewModel(application: Application) : AndroidViewModel(application) {

    private var todolistRepository = TodolistRepository(application)
    private var todos: LiveData<List<Todolist>> = todolistRepository.getTodos()

    fun insertTodo(todo: Todolist) {
        todolistRepository.insert(todo)
    }

    fun getTodos(): LiveData<List<Todolist>> {
        return todos
    }

    fun deleteTodo(todo: Todolist) {
        todolistRepository.delete(todo)
    }

    fun updateTodo(todo: Todolist) {
        todolistRepository.update(todo)
    }

}
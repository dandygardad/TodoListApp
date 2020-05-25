package id.ac.unhas.todolist.ui

import android.app.Activity
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import id.ac.unhas.todolist.R
import id.ac.unhas.todolist.db.todolist.Todolist
import id.ac.unhas.todolist.utilities.Constants
import kotlinx.android.synthetic.main.activity_isi.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), TodolistAdapter.TodoEvents {

    private lateinit var todolistViewModel: TodolistViewModel
    private lateinit var searchView: SearchView
    private lateinit var todolistAdapter: TodolistAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        isiActivity.layoutManager = LinearLayoutManager(this)
        todolistAdapter = TodolistAdapter(this)
        isiActivity.adapter = todolistAdapter

        todolistViewModel = ViewModelProvider(this).get(TodolistViewModel::class.java)
        todolistViewModel.getTodos().observe(this, Observer {
            todolistAdapter.setTodos(it)
        })

        new_fab.setOnClickListener {
            resetSearchView()
            val intent = Intent(this@MainActivity, CreateActivity::class.java)
            startActivityForResult(intent, Constants.INTENT_CREATE_TODO)
        }
    }

    override fun onViewClicked(todoList: Todolist) {
        resetSearchView()
        val intent = Intent(this@MainActivity, UpdateActivity::class.java)
        intent.putExtra(Constants.INTENT_OBJECT, todoList)
        startActivityForResult(intent, Constants.INTENT_UPDATE_TODO)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            val todoList = data?.getParcelableExtra<Todolist>(Constants.INTENT_OBJECT)!!
            when (requestCode) {
                Constants.INTENT_CREATE_TODO -> {
                    todolistViewModel.insertTodo(todoList)
                }
                Constants.INTENT_UPDATE_TODO -> {
                    todolistViewModel.updateTodo(todoList)
                }
            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu?.findItem(R.id.searchtodo)
            ?.actionView as SearchView
        searchView.setSearchableInfo(searchManager
            .getSearchableInfo(componentName))
        searchView.maxWidth = Integer.MAX_VALUE
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                todolistAdapter.filter.filter(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                todolistAdapter.filter.filter(newText)
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.searchtodo -> true
            R.id.sortingDibuat ->{
                sortingDibuat()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun sortingDibuat(){
        todolistViewModel.getSortDibuat().observe(this, Observer {
            todolistAdapter.setSortDibuat(it)
        })
    }

    override fun onDeleteClicked(todoList: Todolist) {
        todolistViewModel.deleteTodo(todoList)
    }

    override fun onBackPressed() {
        resetSearchView()
        super.onBackPressed()
    }

    private fun resetSearchView() {
        if (!searchView.isIconified) {
            searchView.isIconified = true
            return
        }
    }
}
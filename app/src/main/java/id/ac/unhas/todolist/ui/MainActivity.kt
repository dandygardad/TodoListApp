package id.ac.unhas.todolist.ui

import android.app.Activity
import android.app.AlertDialog
import android.app.SearchManager
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import id.ac.unhas.todolist.R
import id.ac.unhas.todolist.db.todolist.Todolist
import id.ac.unhas.todolist.utilities.Constants
import id.ac.unhas.todolist.utilities.Constants.INSERT
import id.ac.unhas.todolist.utilities.Constants.UPDATE
import kotlinx.android.synthetic.main.activity_isi.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), TodolistAdapter.TodoEvents {

    private lateinit var todolistViewModel: TodolistViewModel
    private lateinit var searchView: SearchView
    private lateinit var todolistAdapter: TodolistAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        list.layoutManager = LinearLayoutManager(this)
        todolistAdapter = TodolistAdapter(this)
        list.adapter = todolistAdapter

        todolistViewModel = ViewModelProvider(this).get(TodolistViewModel::class.java)
        todolistViewModel.getTodos().observe(this, Observer {todo ->
            todo.let { noTodo(todo) }
        })

        new_fab.setOnClickListener {
            reset()
            val intent = Intent(this@MainActivity, CreateActivity::class.java)
            startActivityForResult(intent, INSERT)
        }
    }

    override fun showDialog(todoList: Todolist){
        var dialog: AlertDialog
        val builder = AlertDialog.Builder(this)

        builder.setTitle("Apa anda ingin menghapus ini?")
        builder.setMessage("Ini tidak bisa kembali jika dihapus..")

        val dialogHapus = DialogInterface.OnClickListener{ _, which ->
            when(which){
                DialogInterface.BUTTON_POSITIVE -> onDeleteClicked(todoList)
                DialogInterface.BUTTON_NEGATIVE -> ""
            }
        }

        builder.setPositiveButton("YA", dialogHapus)
        builder.setNegativeButton("TIDAK", dialogHapus)

        dialog = builder.create()
        dialog.show()
    }

    private fun noTodo(todoList: List<Todolist>){
        todolistAdapter.setTodos(todoList)
        if (todoList.isEmpty()){
            list.visibility = View.GONE
            todophoto.visibility = View.VISIBLE
            quotes.visibility = View.VISIBLE
            quotesby.visibility = View.VISIBLE
        }
        else{
            list.visibility = View.VISIBLE
            todophoto.visibility = View.GONE
            quotes.visibility = View.GONE
            quotesby.visibility = View.GONE
        }
    }



    override fun onViewClicked(todoList: Todolist) {
        reset()
        val intent = Intent(this@MainActivity, UpdateActivity::class.java)
        intent.putExtra(Constants.OBJECT, todoList)
        startActivityForResult(intent, UPDATE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            val todoList = data?.getParcelableExtra<Todolist>(Constants.OBJECT)!!
            when (requestCode) {
                INSERT -> {
                    todolistViewModel.insertTodo(todoList)
                }
                UPDATE -> {
                    todolistViewModel.updateTodo(todoList)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu?.findItem(R.id.searchlist)?.actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
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
            R.id.searchlist -> true
            R.id.sortingDibuat ->{
                sortingDibuat()
                return true
            }
            R.id.sortingJatuhTempo -> {
                sortingTempo()
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

    private fun sortingTempo(){
        todolistViewModel.getSortTempo().observe(this, Observer {
            todolistAdapter.setSortTempo(it)
        })
    }

    override fun onDeleteClicked(todoList: Todolist) {
        todolistViewModel.deleteTodo(todoList)
    }

    override fun onBackPressed() {
        reset()
        super.onBackPressed()
    }

    private fun reset() {
        if (!searchView.isIconified) {
            searchView.isIconified = true
            return
        }
    }
}
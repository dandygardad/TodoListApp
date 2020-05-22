package id.ac.unhas.todolist.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import id.ac.unhas.todolist.R
import id.ac.unhas.todolist.db.todolist.Todolist
import id.ac.unhas.todolist.utilities.Constants
import kotlinx.android.synthetic.main.activity_create.*

class CreateActivity : AppCompatActivity() {

    private var todoList: Todolist? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)

        val intent = intent
        if (intent != null && intent.hasExtra(Constants.INTENT_OBJECT)) {
            val todoList: Todolist = intent.getParcelableExtra(Constants.INTENT_OBJECT)
            this.todoList = todoList
            prePopulateData(todoList)
        }
        title = if (todoList != null) getString(R.string.editing) else getString(R.string.create_todo)
    }

    private fun prePopulateData(todoList: Todolist) {
        editTitle.setText(todoList.title)
        editIsi.setText(todoList.todo)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflate = menuInflater
        menuInflate.inflate(R.menu.save_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.save_todo -> {
                saveTodo()
            }
        }
        return true
    }

    private fun saveTodo() {
            val id = if (todoList != null) todoList?.id else null
            val todo = Todolist(id = id,
                title = editTitle.text.toString(),
                todo = editIsi.text.toString())
            val intent = Intent()
            intent.putExtra(Constants.INTENT_OBJECT, todo)
            setResult(RESULT_OK, intent)
            finish()
    }
}
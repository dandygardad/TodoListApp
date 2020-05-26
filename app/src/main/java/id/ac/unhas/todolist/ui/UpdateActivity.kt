package id.ac.unhas.todolist.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import id.ac.unhas.todolist.R
import id.ac.unhas.todolist.db.todolist.Todolist
import id.ac.unhas.todolist.utilities.Constants
import id.ac.unhas.todolist.utilities.Constants.waktuUnix
import kotlinx.android.synthetic.main.activity_create.editIsi
import kotlinx.android.synthetic.main.activity_create.editTitle
import kotlinx.android.synthetic.main.activity_create.tanggal_tempo
import kotlinx.android.synthetic.main.activity_create.waktu_tempo
import java.text.SimpleDateFormat
import java.util.*

class UpdateActivity : AppCompatActivity() {

    private var todoList: Todolist? = null
    private var jam: Int = 0
    private var menit: Int = 0
    private var calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        val intent = intent
        if (intent != null && intent.hasExtra(Constants.OBJECT)) {
            val todoList: Todolist = intent.getParcelableExtra(Constants.OBJECT)
            this.todoList = todoList
            prePopulateData(todoList)
        }
        tanggal_tempo.setOnClickListener {
            Constants.showDatePickerDialog(this, tanggal_tempo)
        }

        waktu_tempo.setOnClickListener {
            Constants.showTimePickerDialog(this,waktu_tempo)
        }
        title = getString(R.string.editing)
    }

    private fun prePopulateData(todoList: Todolist) {
        editTitle.setText(todoList.title)
        editIsi.setText(todoList.todo)
        waktu_tempo.setText(todoList.tempoWaktu)
        tanggal_tempo.setText(todoList.tempoTanggal)
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
        val sdf = SimpleDateFormat("dd/M/yyyy HH:mm")
        val id = if (todoList != null) todoList?.id else null
        val todo = todoList?.waktuDibuat?.let {
            Todolist(
                id = id,
                title = editTitle.text.toString(),
                todo = editIsi.text.toString(),
                tempo = waktuUnix,
                tempoTanggal = tanggal_tempo.text.toString(),
                tempoWaktu = waktu_tempo.text.toString(),
                waktuDibuat = it,
                waktuDibuatString = todoList!!.waktuDibuatString,
                waktuUpdate = sdf.format(Date()),
                judulWaktuUpdate = "Tanggal Update :"
            )
        }
        val intent = Intent()
        intent.putExtra(Constants.OBJECT, todo)
        setResult(RESULT_OK, intent)
        finish()
    }
}
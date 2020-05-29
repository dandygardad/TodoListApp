package id.ac.unhas.todolist.ui

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import id.ac.unhas.todolist.R
import id.ac.unhas.todolist.db.todolist.Todolist
import id.ac.unhas.todolist.utilities.AlarmReceiver
import id.ac.unhas.todolist.utilities.Constants
import id.ac.unhas.todolist.utilities.Constants.waktuUnix
import kotlinx.android.synthetic.main.activity_create.*
import kotlinx.android.synthetic.main.activity_update.*
import java.text.SimpleDateFormat
import java.util.*

class UpdateActivity : AppCompatActivity() {

    private var todoList: Todolist? = null
    private lateinit var alarmReceiver : AlarmReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        val intent = intent
        if (intent != null && intent.hasExtra(Constants.OBJECT)) {
            val todoList: Todolist = intent.getParcelableExtra(Constants.OBJECT)
            this.todoList = todoList
            prePopulateData(todoList)
        }
        tanggal_tempoUpdate.setOnClickListener {
            Constants.showDateTimePicker(this, tanggal_tempoUpdate)
        }

        title = getString(R.string.editing)
        alarmReceiver = AlarmReceiver()
    }

    private fun prePopulateData(todoList: Todolist) {
        editTitleUpdate.setText(todoList.title)
        editIsiUpdate.setText(todoList.todo)
        tanggal_tempoUpdate.setText(todoList.tempoTanggal)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflate = menuInflater
        menuInflate.inflate(R.menu.save_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.save_todo -> {
                todoList?.let { saveTodo(it) }
            }
        }
        return true
    }

    private fun saveTodo(todoList: Todolist) {
        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm")
        if(editTitleUpdate.text.toString() != todoList.title && editIsiUpdate.text.toString() != todoList.todo && tanggal_tempoUpdate.text.toString() != todoList.tempoTanggal) {
            todoList.title = editTitleUpdate.text.toString()
            todoList.todo = editIsiUpdate.text.toString()
            todoList.tempo = waktuUnix
            todoList.tempoTanggal = tanggal_tempoUpdate.text.toString()
            todoList.waktuUpdate = sdf.format(Date())
            todoList.judulWaktuUpdate = "Diubah"

            val intent = Intent()
            intent.putExtra(Constants.OBJECT, todoList)
            setResult(RESULT_OK, intent)
            alarmReceiver.setReminder(
                this,
                waktuUnix - 3600 * 1000,
                "Ada To Do yang akan mulai sebentar lagi"
            )
            finish()
        }
        else{
            var dialog: AlertDialog
            val builder = AlertDialog.Builder(this)

            builder.setTitle("Error")
            builder.setMessage("Ada data masih sama!")

            val dialogHapus = DialogInterface.OnClickListener{ _, which ->
                when(which){
                    DialogInterface.BUTTON_NEGATIVE -> ""
                }
            }

            builder.setNegativeButton("TIDAK", dialogHapus)

            dialog = builder.create()
            dialog.show()
        }
    }
}
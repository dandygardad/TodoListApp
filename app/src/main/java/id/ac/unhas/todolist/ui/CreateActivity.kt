package id.ac.unhas.todolist.ui

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import id.ac.unhas.todolist.R
import id.ac.unhas.todolist.db.todolist.Todolist
import id.ac.unhas.todolist.utilities.AlarmReceiver
import id.ac.unhas.todolist.utilities.Constants
import id.ac.unhas.todolist.utilities.Constants.waktuUnix
import kotlinx.android.synthetic.main.activity_create.*
import java.text.SimpleDateFormat
import java.util.*

class CreateActivity : AppCompatActivity() {

    private var todoList: Todolist? = null
    private lateinit var alarmReceiver: AlarmReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)

        tanggal_tempo.setOnClickListener {
            Constants.showDateTimePicker(this, tanggal_tempo)
        }
        title = getString(R.string.create_todo)
        alarmReceiver = AlarmReceiver()

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

    private fun currentTimeToLong(): Long {
        return System.currentTimeMillis()
    }

    private fun saveTodo() {
        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm")
        val id = if (todoList != null) todoList?.id else null
        if(editTitle.text.toString() != "" &&
            editIsi.text.toString() != "" &&
            tanggal_tempo.text.toString() != "Tanggal dan Waktu Tidak Diset") {

            val todo = Todolist(
                id = id,
                title = editTitle.text.toString(),
                todo = editIsi.text.toString(),
                tempo = waktuUnix,
                tempoTanggal = tanggal_tempo.text.toString(),
                waktuDibuat = currentTimeToLong(),
                waktuDibuatString = sdf.format(Date()),
                waktuUpdate = "",
                judulWaktuUpdate = ""
            )
            val intent = Intent()
            intent.putExtra(Constants.OBJECT, todo)
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
            builder.setMessage("Note masih kosong!")

            val dialogHapus = DialogInterface.OnClickListener{ _, which ->
                when(which){
                    DialogInterface.BUTTON_NEGATIVE -> ""
                }
            }

            builder.setNegativeButton("OKE", dialogHapus)

            dialog = builder.create()
            dialog.show()
        }
    }
}
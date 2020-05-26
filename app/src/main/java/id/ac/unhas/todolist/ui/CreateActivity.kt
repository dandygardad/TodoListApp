package id.ac.unhas.todolist.ui

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import id.ac.unhas.todolist.R
import id.ac.unhas.todolist.db.todolist.Todolist
import id.ac.unhas.todolist.utilities.Constants
import kotlinx.android.synthetic.main.activity_create.*
import java.text.SimpleDateFormat
import java.util.*

class CreateActivity : AppCompatActivity() {

    private var todoList: Todolist? = null
    private var bulan: Int = 0
    private var hari: Int = 0
    private var tahun: Int = 0
    private var jam: Int = 0
    private var menit: Int = 0
    private var calendar = Calendar.getInstance()
    private var timestamp: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)

        tanggal_tempo.setOnClickListener {
            showDatePickerDialog()
        }

        waktu_tempo.setOnClickListener {
            showTimePickerDialog()
        }
        title = getString(R.string.create_todo)
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
        val sdf = SimpleDateFormat("dd-M-yyyy HH:mm")
        val id = if (todoList != null) todoList?.id else null
        val todo = Todolist(
            id = id,
            title = editTitle.text.toString(),
            todo = editIsi.text.toString(),
            tempo = timestamp,
            tempoTanggal = tanggal_tempo.text.toString(),
            tempoWaktu = waktu_tempo.text.toString(),
            waktuDibuat = currentTimeToLong(),
            waktuDibuatString = sdf.format(Date()),
            waktuUpdate = "",
            judulWaktuUpdate = ""
        )
        val intent = Intent()
        intent.putExtra(Constants.INTENT_OBJECT, todo)
        setResult(RESULT_OK, intent)
        finish()
    }

    private fun showDatePickerDialog() {
        val calendarDate = Calendar.getInstance()
        hari = calendarDate.get(Calendar.DAY_OF_MONTH)
        bulan = calendarDate.get(Calendar.MONTH)
        tahun = calendarDate.get(Calendar.YEAR)
        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { _, tahun_muncul, bulan_muncul, hari_muncul ->
                tanggal_tempo.text = ("" + hari_muncul + "-" + (bulan_muncul+1) + "-" + tahun_muncul)
                hari = hari_muncul
                bulan = bulan_muncul
                tahun = tahun_muncul
                calendarDate.set(tahun_muncul,bulan_muncul, hari_muncul)
                timestamp = calendarDate.getTimeInMillis()
            },
            tahun,
            bulan,
            hari
        )
        datePickerDialog.show()
    }

    private fun showTimePickerDialog() {
        jam = calendar.get(Calendar.HOUR_OF_DAY)
        menit = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog.OnTimeSetListener { _, jam, menit ->
            calendar.set(Calendar.HOUR_OF_DAY, jam)
            calendar.set(Calendar.MINUTE, menit)
            waktu_tempo.setText(SimpleDateFormat("HH:mm").format(calendar.time))
            }
        TimePickerDialog(this, timePickerDialog, jam, menit,true).show()
    }

}
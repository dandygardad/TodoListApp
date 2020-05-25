package id.ac.unhas.todolist.ui

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import id.ac.unhas.todolist.R
import id.ac.unhas.todolist.db.todolist.Todolist
import id.ac.unhas.todolist.utilities.Constants
import kotlinx.android.synthetic.main.activity_create.*
import kotlinx.android.synthetic.main.activity_create.editIsi
import kotlinx.android.synthetic.main.activity_create.editTitle
import kotlinx.android.synthetic.main.activity_create.tanggal_tempo
import kotlinx.android.synthetic.main.activity_create.waktu_tempo
import kotlinx.android.synthetic.main.activity_update.*
import java.text.SimpleDateFormat
import java.util.*

class UpdateActivity : AppCompatActivity() {

    private var todoList: Todolist? = null
    private var bulan: Int = 0
    private var hari: Int = 0
    private var tahun: Int = 0
    private var jam: Int = 0
    private var menit: Int = 0
    private var calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        val intent = intent
        if (intent != null && intent.hasExtra(Constants.INTENT_OBJECT)) {
            val todoList: Todolist = intent.getParcelableExtra(Constants.INTENT_OBJECT)
            this.todoList = todoList
            prePopulateData(todoList)
        }
        tanggal_tempo.setOnClickListener {
            showDatePickerDialog()
        }

        waktu_tempo.setOnClickListener {
            showTimePickerDialog()
        }
        title = getString(R.string.editing)
    }

    private fun prePopulateData(todoList: Todolist) {
        editTitle.setText(todoList.title)
        editIsi.setText(todoList.todo)
        waktu_tempo.setText(todoList.tempoWaktu)
        tanggal_tempo.setText(todoList.tempo)
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
        val sdf = SimpleDateFormat("dd/M/yy HH:mm")
        val id = if (todoList != null) todoList?.id else null
        val todo = todoList?.waktuDibuat?.let {
            Todolist(id = id,
                title = editTitle.text.toString(),
                todo = editIsi.text.toString(),
                tempo = tanggal_tempo.text.toString(),
                tempoWaktu = waktu_tempo.text.toString(),
                waktuDibuat = it,
                waktuUpdate = sdf.format(Date()),
                judulWaktuUpdate = "Tanggal Update :"
            )
        }
        val intent = Intent()
        intent.putExtra(Constants.INTENT_OBJECT, todo)
        setResult(RESULT_OK, intent)
        finish()
    }

    private fun showDatePickerDialog() {
        hari = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        bulan = Calendar.getInstance().get(Calendar.MONTH)
        tahun = Calendar.getInstance().get(Calendar.YEAR)
        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { _, tahun_muncul, bulan_muncul, hari_muncul ->
                tanggal_tempo.text = ("" + hari_muncul + "/" + (bulan_muncul+1) + "/" + tahun_muncul)
                hari = hari_muncul
                bulan = bulan_muncul
                tahun = tahun_muncul
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
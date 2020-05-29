package id.ac.unhas.todolist.utilities

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*

object Constants{
    const val OBJECT = "object"
    const val INSERT = 1
    const val UPDATE = 2
    var waktuUnix: Long = 0
    val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())

    fun showDateTimePicker(context: Context, view: TextView) {
        val calendarDate = Calendar.getInstance()
        val yearGet = calendarDate.get(Calendar.YEAR)
        val monthGet = calendarDate.get(Calendar.MONTH)
        val dayGet = calendarDate.get(Calendar.DAY_OF_MONTH)
        val hourGet = calendarDate.get(Calendar.HOUR_OF_DAY)
        val minuteGet = calendarDate.get(Calendar.MINUTE)
        DatePickerDialog(
            context,
            DatePickerDialog.OnDateSetListener { _, tahun_muncul, bulan_muncul, hari_muncul ->
                TimePickerDialog(context, TimePickerDialog.OnTimeSetListener { _, jam, menit ->
                    val calendar = Calendar.getInstance()
                    calendar.set(tahun_muncul, bulan_muncul, hari_muncul, jam, menit)
                    view.setText(sdf.format(calendar.time))
                    waktuUnix = calendar.getTimeInMillis()
                }, hourGet, minuteGet, true).show()
            },
            yearGet,
            monthGet,
            dayGet
        ).show()
    }
}
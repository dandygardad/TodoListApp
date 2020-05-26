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

    fun showDatePickerDialog(context: Context, view: TextView) {
        val calendarDate = Calendar.getInstance()
        DatePickerDialog(
            context,
            DatePickerDialog.OnDateSetListener { _, tahun_muncul, bulan_muncul, hari_muncul ->
                val format = "dd/M/yy"
                calendarDate.set(Calendar.YEAR, tahun_muncul)
                calendarDate.set(Calendar.MONTH, bulan_muncul)
                calendarDate.set(Calendar.DAY_OF_MONTH, hari_muncul)
                val sdf = SimpleDateFormat(format, Locale.US)
                view.text = (sdf.format(calendarDate.time))
                waktuUnix = calendarDate.getTimeInMillis() / 1000
            },
            calendarDate.get(Calendar.YEAR),
            calendarDate.get(Calendar.MONTH),
            calendarDate.get(Calendar.DAY_OF_MONTH)

        )
            .show()
    }

    fun showTimePickerDialog(context: Context, view: TextView) {
        val calendar = Calendar.getInstance()
        calendar.get(Calendar.HOUR_OF_DAY)
        calendar.get(Calendar.MINUTE)

        TimePickerDialog (
            context,
            TimePickerDialog.OnTimeSetListener { _, jam, menit ->
                calendar.set(Calendar.HOUR_OF_DAY, jam)
                calendar.set(Calendar.MINUTE, menit)
                view.setText(SimpleDateFormat("HH:mm").format(calendar.time))
        },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true)
            .show()
    }
}
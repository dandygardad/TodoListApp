package id.ac.unhas.todolist.utilities

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import id.ac.unhas.todolist.R

class AlarmReceiver : BroadcastReceiver(){
    companion object{
        private const val ID_REMINDER = 100
        private const val EXTRA_MESSAGE = "message"
    }

    override fun onReceive(context: Context, intent: Intent) {
        val message = intent.getStringExtra(EXTRA_MESSAGE) as String
        val title = "Reminder ToDo List"
        val remindID =  ID_REMINDER
        showAlarmNotification(context, title, message, remindID)
    }

    fun setReminder(context: Context, waktu: Long, message: String) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        intent.putExtra(EXTRA_MESSAGE, message)

        val pendingIntent = PendingIntent.getBroadcast(context, ID_REMINDER, intent, 0)
        alarmManager.set(AlarmManager.RTC_WAKEUP, waktu, pendingIntent)
    }

    private fun showAlarmNotification(context: Context, title: String, message: String, remindID: Int) {
        val idChannel = "Channel"
        val nameChannel = "Notification ToDoList"
        val notificationManagerCompat = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val builder : NotificationCompat.Builder

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notifChannel = NotificationChannel(idChannel, nameChannel, NotificationManager.IMPORTANCE_DEFAULT)
            notifChannel.enableVibration(true)
            notifChannel.vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)
            notificationManagerCompat.createNotificationChannel(notifChannel)
            builder = NotificationCompat.Builder(context, idChannel)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setSmallIcon(R.mipmap.ic_launchers_round)
                .setSound(RingtoneManager.getDefaultUri((RingtoneManager.TYPE_NOTIFICATION)))
                .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
        }
        else{
            builder = NotificationCompat.Builder(context, idChannel)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setSound(RingtoneManager.getDefaultUri((RingtoneManager.TYPE_NOTIFICATION)))
                .setSmallIcon(R.mipmap.ic_launchers_round)
                .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
        }
        notificationManagerCompat.notify(remindID, builder.build())
    }

}
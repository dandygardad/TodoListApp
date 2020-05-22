package id.ac.unhas.todolist.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import id.ac.unhas.todolist.db.todolist.Todolist
import id.ac.unhas.todolist.db.todolist.TodolistDao

@Database(entities = [Todolist::class], exportSchema = false, version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun todoDao(): TodolistDao

    companion object {
        private const val DB_NAME = "TODO_DB"
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase? {
            if (instance == null) {
                synchronized(AppDatabase::class) {
                    instance = Room
                        .databaseBuilder(
                            context,
                            AppDatabase::class.java,
                            DB_NAME)
                        .build()
                }
            }
            return instance
        }
    }
}
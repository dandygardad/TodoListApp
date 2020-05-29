package id.ac.unhas.todolist.db.todolist

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TodolistDao {
    @Query("Select * from todo")
    fun getTodos(): LiveData<List<Todolist>>

    @Query("Select * from todo ORDER BY waktu_dibuat_millis")
    fun getSortDibuat() : LiveData<List<Todolist>>

    @Query("Select * from todo ORDER BY tempo_millis")
    fun getSortTempo(): LiveData<List<Todolist>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTodo(todo: Todolist)

    @Delete
    suspend fun deleteTodo(todo: Todolist)

    @Update
    suspend fun updateTodo(todo: Todolist)
}
package id.ac.unhas.todolist.db.todolist

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize()
@Entity(tableName = "todo")
data class Todolist(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int? = null,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "todo")
    val todo: String,
    @ColumnInfo(name = "tempo")
    val tempo: String,
    @ColumnInfo(name = "tempoWaktu")
    val tempoWaktu: String,
    @ColumnInfo(name = "waktuDibuat")
    val waktuDibuat: String
) : Parcelable
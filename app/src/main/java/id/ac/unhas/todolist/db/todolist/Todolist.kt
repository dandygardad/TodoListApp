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
    val tempo: Long,
    @ColumnInfo(name = "tempoTanggal")
    val tempoTanggal: String,
    @ColumnInfo(name = "tempoWaktu")
    val tempoWaktu: String,
    @ColumnInfo(name = "waktuDibuat")
    val waktuDibuat: Long,
    @ColumnInfo(name = "waktuDibuatString")
    val waktuDibuatString: String,
    @ColumnInfo(name = "waktuUpdate")
    val waktuUpdate: String,
    @ColumnInfo(name = "judulWaktuUpdate")
    val judulWaktuUpdate: String
) : Parcelable
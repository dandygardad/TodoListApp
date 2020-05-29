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
    var title: String,
    @ColumnInfo(name = "todo")
    var todo: String,
    @ColumnInfo(name = "tempo_millis")
    var tempo: Long,
    @ColumnInfo(name = "tempo_tanggal")
    var tempoTanggal: String,
    @ColumnInfo(name = "waktu_dibuat_millis")
    val waktuDibuat: Long,
    @ColumnInfo(name = "waktu_dibuat")
    val waktuDibuatString: String,
    @ColumnInfo(name = "waktu_update")
    var waktuUpdate: String,
    @ColumnInfo(name = "update_or_not")
    var judulWaktuUpdate: String
) : Parcelable
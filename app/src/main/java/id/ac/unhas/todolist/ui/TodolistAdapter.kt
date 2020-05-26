package id.ac.unhas.todolist.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import id.ac.unhas.todolist.R
import id.ac.unhas.todolist.db.todolist.Todolist
import kotlinx.android.synthetic.main.item_todo.view.*

class TodolistAdapter(todoEvents: TodoEvents) : RecyclerView.Adapter<TodolistAdapter.TodoViewHolder>(),
    Filterable {

    private var todo: List<Todolist> = arrayListOf()
    private var filteredTodoList: List<Todolist> = arrayListOf()
    private val listener: TodoEvents = todoEvents

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_todo,
            parent, false)
        return TodoViewHolder(view)
    }

    override fun getItemCount(): Int = filteredTodoList.size

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(filteredTodoList[position], listener)
    }

    class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(todo: Todolist, listener: TodoEvents) {
            itemView.title_todo.text = todo.title
            itemView.isi_todo.text = todo.todo
            itemView.view_jatuh_tanggal.text = todo.tempoTanggal
            itemView.view_jatuh_waktu.text = todo.tempoWaktu
            itemView.view_waktu_dibuat.text = todo.waktuDibuatString
            itemView.view_waktu_update.text = todo.waktuUpdate
            itemView.time_update.text = todo.judulWaktuUpdate.toString()

            itemView.delete_button.setOnClickListener {
                listener.onDeleteClicked(todo)
            }
            itemView.setOnClickListener {
                listener.onViewClicked(todo)
            }
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val charString = p0.toString()
                filteredTodoList = if (charString.isEmpty()) {
                    todo
                } else {
                    val filteredList = arrayListOf<Todolist>()
                    for (row in todo) {
                        if (row.title.toLowerCase().contains(charString.toLowerCase())
                            || row.todo.contains(charString.toLowerCase())) {
                            filteredList.add(row)
                        }
                    }
                    filteredList
                }
                val filterResults = FilterResults()
                filterResults.values = filteredTodoList
                return filterResults
            }
            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                filteredTodoList = p1?.values as List<Todolist>
                notifyDataSetChanged()
            }

        }
    }

    fun setTodos(todoList: List<Todolist>) {
        this.todo = todoList
        this.filteredTodoList = todoList
        notifyDataSetChanged()
    }

    fun setSortDibuat(todoList: List<Todolist>){
        this.todo = todoList
        this.filteredTodoList = todoList
        notifyDataSetChanged()
    }

    fun setSortTempo(todoList: List<Todolist>){
        this.todo = todoList
        this.filteredTodoList = todoList
        notifyDataSetChanged()
    }

    interface TodoEvents {
        fun onDeleteClicked(todoList: Todolist)
        fun onViewClicked(todoList: Todolist)
    }
}
package com.example.dbdemo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.row_view.view.*

class DataRecyclerViewAdapter(
    private val context: Context,
    private val repo: PersonRepository) :
    RecyclerView.Adapter<DataRecyclerViewAdapter.ViewHolder>() {

    lateinit var dataSet: List<Person>

    fun refreshData() {
        dataSet = repo.getAll()
    }

    init {
        refreshData()
    }


    private val inflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val rowView = inflater.inflate(R.layout.row_view, parent, false)
        return ViewHolder(rowView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val person = dataSet.get(position)
        holder.itemView.textViewId.text = person.id.toString()
        holder.itemView.textViewFirstName.text = person.firstName
        holder.itemView.textViewLastName.text = person.lastName
    }

    override fun getItemCount(): Int {
        return dataSet.count()
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}

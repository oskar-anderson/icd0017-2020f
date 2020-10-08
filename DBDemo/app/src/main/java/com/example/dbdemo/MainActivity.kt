package com.example.dbdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var personRepo : PersonRepository
    private lateinit var adapter: RecyclerView.Adapter<*>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        personRepo = PersonRepository(this).open()

        recyclerViewPersons.layoutManager = LinearLayoutManager(this)
        adapter = DataRecyclerViewAdapter(this, personRepo)
        recyclerViewPersons.adapter = adapter


        /*
        personRepo.add(Person("Andres", "Käver"))
        personRepo.add(Person("Kerman", "Saapar"))

        val persons = personRepo.getAll()

        for (person in persons) {
            Log.d("Person", person.toString() + " " + person.firstName + " " + person.lastName)
        }
        */
    }

    fun buttonAddPersonOnClick(view: View) {
        personRepo.add(
            Person(
                editTextTextPersonFirstName.text.toString(),
                editTextTextPersonLastName.text.toString()
            )
        )
        (adapter as DataRecyclerViewAdapter).refreshData()
        adapter.notifyDataSetChanged()

        val persons = personRepo.getAll()

        for (person in persons) {
            Log.d("Person", person.toString() + " " + person.firstName + " " + person.lastName)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        personRepo.close()
    }
}
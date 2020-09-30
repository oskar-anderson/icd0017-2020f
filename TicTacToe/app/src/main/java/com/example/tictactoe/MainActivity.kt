package com.example.tictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {

    var nextMoveByX = true;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun gameButtonOnClick(view: View){
        if ((view as Button).text == ""){
            (view as Button).text = if(nextMoveByX) "X" else "0"
            nextMoveByX = !nextMoveByX
        }
    }
}
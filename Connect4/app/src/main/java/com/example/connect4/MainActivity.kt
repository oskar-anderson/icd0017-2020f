package com.example.connect4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {

    var tileWidth = 48;
    var tileHeight = 48;

    val buttons = arrayOf<Array<Button>>()
    var nextMoveByX = true;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //playerOneScore = (TextView) findViewById(R.id.playerOneScore)
        //playerTwoScore = (TextView) findViewById(R.id.playerTwoScore)
    }

    fun gameButtonOnClick(view: View){
        if ((view as Button).text == ""){
            (view as Button).text = if(nextMoveByX) "X" else "0"
            nextMoveByX = !nextMoveByX
        }
    }
}
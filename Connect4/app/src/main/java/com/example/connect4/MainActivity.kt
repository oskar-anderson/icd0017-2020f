package com.example.connect4

import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    val buttons = arrayOf<Array<Button>>()
    var nextMoveByX = true;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //playerOneScore = (TextView) findViewById(R.id.playerOneScore)
        //playerTwoScore = (TextView) findViewById(R.id.playerTwoScore)
    }

    fun gameColumnOnClick(view: View){
        if (true){
            (view as Button).text = if(nextMoveByX) "X" else "0"
            (view as Button).background = Drawable.createFromPath("#F00")
            nextMoveByX = !nextMoveByX
        }
    }

    fun gameButtonOnClick(view: View){
        if (view.tag != "used"){
            view.setTag("used");
            (view as Button).text = if(nextMoveByX) "X" else "0"
            if (nextMoveByX){
                view.setBackgroundResource(R.drawable.circle_red)
                findViewById<TextView>(R.id.currentPlayerValue).text = "Yellow"
            }
            else {
                view.setBackgroundResource(R.drawable.circle_yellow)
                findViewById<TextView>(R.id.currentPlayerValue).text = "Red"
            }
            val roundNumber = findViewById<TextView>(R.id.roundNumber)
            roundNumber.text = Integer.toString(Integer.parseInt(roundNumber.text as String) + 1)
            nextMoveByX = !nextMoveByX


            //var red = Drawable.createFromPath("#F00");
            //var yellow = Drawable.createFromPath("#FF0");

            //view.background = resources.getDrawable(R.drawable.circle_red)
            //view.setCompoundDrawablesWithIntrinsicBounds(R.drawable.circle_red, 0, R.drawable.circle_red, 0)
            //(view as Button).setBackgroundColor(Color.RED);
        }
    }
}
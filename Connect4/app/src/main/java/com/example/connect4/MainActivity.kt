package com.example.connect4

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.column.view.*
import java.util.*

@SuppressLint("SetTextI18n")
class MainActivity : AppCompatActivity() {

    var isWon = false
    var gBoard: List<Button>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        gBoard = getBoard()
        setTags(gBoard!!)
        Logic(gBoard)

    }

    fun resetOnClick(view: View) {
        resetOnClick()
    }

    fun resetOnClick() {
        Log.d("t", "reset")
        isWon = false
        gBoard =  getBoard()
        for (button in gBoard!!) {
            button.setBackgroundResource(R.drawable.circle_blank)
        }
        findViewById<TextView>(R.id.currentPlayerValue).text = "Red"
        findViewById<TextView>(R.id.roundNumber).text = "1"
        Logic(gBoard)
    }

    fun getBoard(): List<Button> {
        val buttons = arrayOf(
            R.id.button0,
            R.id.button1,
            R.id.button2,
            R.id.button3,
            R.id.button4,
            R.id.button5,
        )

        val columns = arrayOf(
            R.id.col0,
            R.id.col1,
            R.id.col2,
            R.id.col3,
            R.id.col4,
            R.id.col5,
            R.id.col6,
        )
        val grid = mutableListOf<Button>()
        // var grid = arrayOfNulls<Button>(buttons1.size *  columns1.size)
        for (i in 0 until buttons.size) {
            for (j in 0 until columns.size) {
                // grid[i * buttons1.size + j] = findViewById<ConstraintLayout>(columns1[j]).findViewById<Button>(buttons1[i])
                grid.add(findViewById<ConstraintLayout>(columns[j]).findViewById<Button>(buttons[i]))
            }
        }
        return grid;
    }

    fun setTags(board: List<Button>) {
        for (i in 0 until board.size) {
            board[i].tag = i
        }
    }

    fun gameColumnOnClick(view: View){
        // Log.d("H", view.tag.toString())
        gameButtonOnClick(view.button0)
    }

    fun gameButtonOnClick(view: View){
        if (isWon) {
            return
        }
        // findViewById<ConstraintLayout>(R.id.row0).findViewById<Button>(R.id.button0).text = "H"
        val index = view.getTag().toString()
        val idx = Logic.MakeMove(index) ?: return

        gBoard!!.get(idx).setBackgroundResource(if (Logic.playerATurn) R.drawable.circle_red else R.drawable.circle_yellow)

        val isWin = Logic.IsWinningMove(Logic.getPlayerCellStateMarker())
        if (isWin.isWinning) {
            isWon = true
            for (cell in isWin.victoryCells) {
                gBoard!!.get(cell).setBackgroundResource(R.drawable.circle_victory)
            }
            Toast.makeText(this, Logic.getPlayerColor() + " wins", Toast.LENGTH_LONG).show()

            // HOW TO DO DELAY ???

            // resetOnClick()
            return
        }

        val currentPlayer = findViewById<TextView>(R.id.currentPlayerValue)
        currentPlayer.text = if (currentPlayer.text == "Red") "Yellow" else "Red"

        Logic.playerATurn = !Logic.playerATurn
        val roundNumber = findViewById<TextView>(R.id.roundNumber)
        roundNumber.text = Integer.toString(Integer.parseInt(roundNumber.text as String) + 1)
    }

    fun UpdateAll() {
        for (i in 0 until Logic.cells.size) {
            when (Logic.cells.get(i).cellState) {
                CellState.Empty -> gBoard!!.get(i).setBackgroundResource(R.drawable.circle_blank)
                CellState.PlayerA -> gBoard!!.get(i).setBackgroundResource(R.drawable.circle_red)
                CellState.PlayerB -> gBoard!!.get(i).setBackgroundResource(R.drawable.circle_yellow)
                else -> throw RuntimeException("bad enum")
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putBoolean("playerATurn", Logic.playerATurn)
        outState.putIntArray("freeSpotsByColumn", Logic.freeSpotsByColumn)
        outState.putParcelableArrayList("cells", Logic.cells as ArrayList<out Parcelable>)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        Logic.playerATurn = savedInstanceState.getBoolean("playerATurn")
        Logic.freeSpotsByColumn = savedInstanceState.getIntArray("freeSpotsByColumn")
        Logic.cells =  savedInstanceState.getParcelableArrayList("cells")

        UpdateAll()
    }
}
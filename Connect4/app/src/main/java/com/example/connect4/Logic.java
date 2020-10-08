package com.example.connect4;

import android.util.Log;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class Logic {
    private static int boardWidth = 7;
    private static int boardHeight = 6;
    private static int victoryCondition = 4;
    public static boolean playerATurn;
    public static int[] freeSpotsByColumn;
    public static List<Cell> cells;

    public static List<Button> ggbuttons;

    public Logic(List<Button> _grid){
        playerATurn = true;
        freeSpotsByColumn = new int[boardWidth];
        for (int i = 0; i < boardWidth; i++) {
            freeSpotsByColumn[i] = boardHeight - 1;
        }
        cells = new ArrayList<>();
        for (Button button : _grid) {
            cells.add(new Cell(button, CellState.Empty));
        }
    }

    /*
    public static void UpdateAll() {
        for (Cell cell : cells) {
            switch (cell.cellState){
                case Empty:
                    cell.getButton().setBackgroundResource(R.drawable.circle_blank);
                    break;
                case PlayerA:
                    cell.getButton().setBackgroundResource(R.drawable.circle_red);
                    break;
                case PlayerB:
                    cell.getButton().setBackgroundResource(R.drawable.circle_yellow);
                    break;
                default:
                    throw new RuntimeException("Enum bad");
            }
        }
    }
    */

    public static CellState getPlayerCellStateMarker(){
        if (playerATurn) {
            return CellState.PlayerA;
        } else {
            return CellState.PlayerB;
        }
    }

    public static String getPlayerColor() {
        if (Logic.playerATurn) {
            return "Red";
        } else {
            return "Yellow";
        }
    }

    public static Integer MakeMove(String sIndex) {
        if (! sIndex.matches("\\d+")) { throw new RuntimeException("WAT"); }
        int iIndex = Integer.parseInt(sIndex);

        int xClickedOn = iIndex % boardWidth;
        // int yClickedOn = iIndex / boardWidth;
        int yFree = freeSpotsByColumn[xClickedOn];
        if (yFree < 0) {return null; }
        freeSpotsByColumn[xClickedOn]--;
        cells.get(get2dAs1d(yFree, xClickedOn)).cellState = getPlayerCellStateMarker();
        return getButtonIndexAt(xClickedOn, yFree);
    }

    private static int getButtonIndexAt(int x, int y) {
        if (x < 0 || x >= boardWidth || y < 0 || y >= boardHeight) {
            throw new RuntimeException("OOB! x: " + x + "; y: " + y);
        }
        Log.d("", "x: " + x + "; y: " + y);
        return y * boardWidth + x;
    }

    private static int get2dAs1d(int y, int x) {
        // Operator overloading would be nice here
        if (x < 0 || x >= boardWidth || y < 0 || y >= boardHeight) {
            throw new RuntimeException("OOB! x: " + x + "; y: " + y);
        }
        Log.d("", "x: " + x + "; y: " + y);
        return y * boardWidth + x;
    }

    public static class IsWinningMoveResult {
        public List<Integer> victoryCells;
        public Boolean isWinning;

        public IsWinningMoveResult(Boolean isWinning, List<Integer> victoryCells) {
            this.isWinning = isWinning;
            this.victoryCells = victoryCells;
        }
    }

    public static IsWinningMoveResult IsWinningMove(CellState cellState){
        int line;
        List<Integer> victoryCells = new ArrayList<>();;

        // check horizontally
        for (int y = 0; y < boardHeight; y++) {
            for (int x = 0; x < boardWidth - (victoryCondition - 1); x++) {
                line = 0;
                for (int i = 0; i < victoryCondition; i++) {
                    if (cells.get(get2dAs1d(y, x + i)).cellState == cellState){ line++; }
                }
                if (line == victoryCondition) {
                    Log.d("T", "horizontally");
                    for (int i = 0; i < victoryCondition; i++) {
                        victoryCells.add(get2dAs1d(y, x + i));
                    }
                    return new IsWinningMoveResult(true, victoryCells);
                }
            }
        }

        // check vertically
        for (int y = 0; y < boardHeight - (victoryCondition - 1); y++)
        {
            for (int x = 0; x < boardWidth; x++)
            {
                line = 0;
                for (int i = 0; i < victoryCondition; i++) {
                    if (cells.get(get2dAs1d(y + i, x)).cellState == cellState) { line++; }
                }
                if (line == victoryCondition) {
                    Log.d("T", "vertically");
                    victoryCells = new ArrayList<>();
                    for (int i = 0; i < victoryCondition; i++) {
                        victoryCells.add(get2dAs1d(y + i, x));
                    }
                    return new IsWinningMoveResult(true, victoryCells);
                }
            }
        }

        // check y = x ascending line
        for (int y = 0; y < boardHeight - (victoryCondition - 1); y++)
        {
            for (int x = 0; x < boardWidth - (victoryCondition - 1); x++)
            {
                line = 0;
                for (int i = 0; i < victoryCondition; i++) {
                    if (cells.get(get2dAs1d(y + i, x + i)).cellState == cellState) { line++; }
                }
                if (line == victoryCondition) {
                    Log.d("T", "ascending");
                    for (int i = 0; i < victoryCondition; i++) {
                        victoryCells.add(get2dAs1d(y + i, x + i));
                    }
                    return new IsWinningMoveResult(true, victoryCells);
                }
            }
        }

        // check y = -x descending line
        for (int y = (victoryCondition - 1); y < boardHeight; y++)
        {
            for (int x = 0; x < boardWidth - (victoryCondition - 1); x++)
            {
                line = 0;
                for (int i = 0; i < victoryCondition; i++) {
                    if (cells.get(get2dAs1d(y + -i, x + i)).cellState == cellState) { line++; } }
                if (line == victoryCondition) {
                    Log.d("T", "descending");
                    for (int i = 0; i < victoryCondition; i++) {
                        victoryCells.add(get2dAs1d(y + -i, x + i));
                    }
                    return new IsWinningMoveResult(true, victoryCells);
                }
            }
        }
        return new IsWinningMoveResult(false, null);

    }


}

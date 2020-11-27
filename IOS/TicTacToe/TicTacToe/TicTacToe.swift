//
//  TicTacToe.swift
//  TicTacToe
//
//  Created by Selmo on 31/10/2020.
//  Copyright © 2020 Selmo. All rights reserved.
//

import Foundation

class TicTacToe {
    
    var gameBoard: [[Tile?]] = Array(repeating: Array(repeating: nil, count: 3), count: 3)
    
    var nextMoveByCross = true
    
    func getTile(columnNo col: Int, rowNo row: Int) -> Tile? {
        return gameBoard[col][row]
    }
    
    func move(columnNo col: Int, rowNo row: Int) {
        if (getTile(columnNo: col, rowNo: row) == nil) {
            gameBoard[col][row] = Tile(isCross: nextMoveByCross)
            nextMoveByCross = !nextMoveByCross
        }
    }
    
    func reset(){
        gameBoard = Array(repeating: Array(repeating: nil, count: 3), count: 3)
    }
}

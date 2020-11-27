//
//  ConnectFour.swift
//  Connect4Swift
//
//  Created by Selmo on 25/11/2020.
//  Copyright © 2020 Selmo. All rights reserved.
//

import Foundation

struct GameBrain {
    static let rows: Int = 6
    static let cols: Int = 7
    static let winCon: Int = 4
    
    var pieces:[[Piece]] = Array(repeating: Array(repeating: Piece(isWinning: false, type: 0), count: GameBrain.cols), count: GameBrain.rows)
    var isRedsTurn: Bool = false
    var turnCounter: Int = 1
    
    mutating func reset() {
        pieces = Array(repeating: Array(repeating: Piece(isWinning: false, type: 0), count: GameBrain.cols), count: GameBrain.rows)
        isRedsTurn = false
        turnCounter = 1
    }
    
    mutating func IsWinningMove(pieceType: Int) -> Bool {
        var line: Int = 0
        
        
        // horizontal
        for y in 0..<GameBrain.rows {
            for x in 0..<GameBrain.cols - (GameBrain.winCon - 1) {
                line = 0
                for i in 0..<GameBrain.winCon {
                    if pieces[y][x + i].type == pieceType {
                        line += 1
                    }
                }
                if line == GameBrain.winCon {
                    for i in 0..<GameBrain.winCon {
                        pieces[y][x + i].isWinning = true
                    }
                    return true
                }
            }
        }
        
        // vertical
        for y in 0..<GameBrain.rows - (GameBrain.winCon - 1) {
            for x in 0..<GameBrain.cols {
                line = 0
                for i in 0..<GameBrain.winCon {
                    if pieces[y + i][x].type == pieceType {
                        line += 1
                    }
                }
                if line == GameBrain.winCon {
                    for i in 0..<GameBrain.winCon {
                        pieces[y + i][x].isWinning = true
                    }
                    return true
                }
            }
        }
        
        // ascending
        for y in 0..<GameBrain.rows - (GameBrain.winCon - 1) {
            for x in 0..<GameBrain.cols - (GameBrain.winCon - 1) {
                line = 0
                for i in 0..<GameBrain.winCon {
                    if pieces[y + i][x + i].type == pieceType {
                        line += 1
                    }
                }
                if line == GameBrain.winCon {
                    for i in 0..<GameBrain.winCon {
                        pieces[y + i][x + i].isWinning = true
                    }
                    return true
                }
            }
        }
        
        // descending
        for y in (GameBrain.winCon - 1)..<GameBrain.rows {
            for x in 0..<GameBrain.cols - (GameBrain.winCon - 1) {
                line = 0
                for i in 0..<GameBrain.winCon {
                    if pieces[y - i][x + i].type == pieceType {
                        line += 1
                    }
                }
                if line == GameBrain.winCon {
                    for i in 0..<GameBrain.winCon {
                        pieces[y - i][x + i].isWinning = true
                    }
                    return true
                }
            }
        }
        
        return false
    }
    
    mutating func dropAt(col: Int) -> Bool {
        let numberOfPieces = numberOfPiecesAt(col: col)
        if numberOfPieces >= GameBrain.rows {
            return false
        }
        let row = GameBrain.rows - 1 - numberOfPieces
        let newPiece = Piece(isWinning: false, type: isRedsTurn ? 1 : 2)
        pieces[row][col] = newPiece
        
        isRedsTurn = !isRedsTurn
        turnCounter += 1
        return true
    }
    
    private func numberOfPiecesAt(col: Int) -> Int {
        var numberOfPieces: Int = 0
        for row in pieces {
            if row[col].type != 0 {
                numberOfPieces += 1
            }
        }
        return numberOfPieces
    }
}

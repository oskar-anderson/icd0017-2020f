//
//  ViewController.swift
//  Connect4Swift
//
//  Created by Selmo on 25/11/2020.
//  Copyright © 2020 Selmo. All rights reserved.
//

import UIKit

class ViewController: UIViewController {

    var game: GameBrain = GameBrain()
    
    @IBOutlet weak var boardView: BoardView!
    @IBOutlet weak var whoseTurnLabel: UILabel!
    @IBOutlet weak var turnCounter: UILabel!
    @IBOutlet weak var load: UIButton!
    @IBAction func reset(_ sender: UIButton) {
        game.reset()
        updateUI()
        boardView.isUserInteractionEnabled = true
    }
    @IBAction func tap(_ sender: UITapGestureRecognizer) {
        if !boardView.isUserInteractionEnabled {
            return
        }
        boardView.setNeedsDisplay()
        let fingerLocation = sender.location(in: boardView)
        let col = boardView.columnAt(x: fingerLocation.x)

        let didDrop = game.dropAt(col: col)
        print(String(col) + " " + String(didDrop))
        if !didDrop {
            return
        }
        if game.IsWinningMove(pieceType: game.isRedsTurn ? 2 : 1) {
            whoseTurnLabel.text = game.isRedsTurn ? "Yellow wins" : "Red wins"
            boardView.isUserInteractionEnabled = false
            boardView.shadowPieces = game.pieces
            print("boardView.setNeedsDisplay()")
            boardView.setNeedsDisplay()
        } else {
            updateUI()
        }
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        boardView.shadowPieces = game.pieces
        boardView.setNeedsDisplay()
    }
    
    func updateUI() {
        whoseTurnLabel.text = game.isRedsTurn ? "Red" : "Yellow"
        turnCounter.text = "Turn: " + String(game.turnCounter)
        boardView.shadowPieces = game.pieces
        print("boardView.setNeedsDisplay()")
        boardView.setNeedsDisplay()
    }


}


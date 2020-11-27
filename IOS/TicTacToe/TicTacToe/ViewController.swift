//
//  ViewController.swift
//  TicTacToe
//
//  Created by Selmo on 31/10/2020.
//  Copyright © 2020 Selmo. All rights reserved.
//

import UIKit

class ViewController: UIViewController {

    var game: TicTacToe = TicTacToe()
    
    @IBOutlet weak var labelNextMoveBy: UILabel!
    
    @IBOutlet var gameTiles: [UIButton]!
    
    @IBAction func resetClicked(_ sender: Any) {
        game.reset()
        updateUI()
    }
    
    @IBAction func gameTileClicked(_ sender: UIButton) {
        print(sender.tag)
        let (col, row) = getRowCol(tileNo: sender.tag)
        game.move(columnNo: col, rowNo: row)
        updateUI()
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        updateUI()
        // Do any additional setup after loading the view.
    }
    
    func updateUI() {
        labelNextMoveBy.text = game.nextMoveByCross ? "❌" : "⭕️"
        for gameTile in gameTiles {
            let (col, row) = getRowCol(tileNo: gameTile.tag)
            let tile = game.getTile(columnNo: col, rowNo: row)
            
            if let gameTileToDraw = tile {
                gameTile.setTitle(gameTileToDraw.isCross ? "❌" : "⭕️", for: UIControl.State.normal)
            } else {
                gameTile.setTitle("", for: UIControl.State.normal)
            }
        }
    }
    
    func getRowCol(tileNo: Int) -> (col: Int, row: Int) {
        let rowNo = tileNo / 3
        let colNo = tileNo - rowNo * 3
        
        return (colNo, rowNo)
    }


}


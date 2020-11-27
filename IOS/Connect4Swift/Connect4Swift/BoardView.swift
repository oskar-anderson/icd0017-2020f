//
//  BoardView.swift
//  Connect4Swift
//
//  Created by Selmo on 25/11/2020.
//  Copyright © 2020 Selmo. All rights reserved.
//

import UIKit

class BoardView: UIView {
    let boardToScreenRatio: CGFloat = 1
    
    var originX: CGFloat = 0
    var originY: CGFloat = 0
    var squareSide: CGFloat = 0
    
    var shadowPieces: [[Piece]] = [[Piece]]()
    
    override init(frame: CGRect) {
        super.init(frame: frame)
        didLoad()
    }
    
    required init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
        didLoad()
    }
    
    func didLoad(){
        let boardWidth = bounds.width * boardToScreenRatio
        squareSide = boardWidth / CGFloat(GameBrain.cols)
        
        originX = (1 - boardToScreenRatio) * bounds.width / 2
        originY = (bounds.height - CGFloat(GameBrain.rows) * squareSide) / 2
    }
    
    override func draw(_ rect: CGRect) {
        print("draw")
        drawBoard()
        drawPieces()
    }
    
    
    func columnAt(x: CGFloat) -> Int {
        return max(min(Int((x - originX) / squareSide), GameBrain.cols - 1), 0)
    }

    private func drawBoard() {
        UIColor.purple.setFill()
        let boardPath = UIBezierPath(
            roundedRect: CGRect(x: originX,
                                y: originY,
                                width: CGFloat(GameBrain.cols) * squareSide,
                                height: CGFloat(GameBrain.rows) * squareSide),
            cornerRadius: 0.25 * squareSide)
        boardPath.fill()
        
        for row in 0..<GameBrain.rows {
            for col in 0..<GameBrain.cols {
                drawCircle(col: col, row: row, color: UIColor.white, withStroke: false)
            }
        }
    }
    
    private func drawPieces() {
        for i in 0..<GameBrain.rows {
            for j in 0..<GameBrain.cols {
                let piece = shadowPieces[i][j];
                var color: UIColor
                if piece.type == 2{
                    color = UIColor.yellow
                }
                else if piece.type == 1{
                    color = UIColor.red
                }
                else {
                    continue
                }
                drawCircle(col: j, row: i, color: color, withStroke: piece.isWinning)
            }
        }
    }
    
    private func drawCircle(col: Int, row: Int, color: UIColor, withStroke: Bool) {
        color.setFill()
        let circleCenterX: CGFloat = originX + 0.5 * squareSide + CGFloat(col) * squareSide
        let circleCenterY: CGFloat = originY + 0.5 * squareSide + CGFloat(row) * squareSide
        let circle =
            UIBezierPath(arcCenter: CGPoint(x: circleCenterX, y: circleCenterY),
                         radius: 0.4 * squareSide,
                         startAngle: 0,
                         endAngle: 2 * CGFloat.pi,
                         clockwise: true)
        circle.fill()
        if withStroke {
            UIColor.blue.setStroke()
            circle.lineWidth = 2
            circle.stroke()
        }
    }
}

//
//  ConnectFourPiece.swift
//  Connect4Swift
//
//  Created by Selmo on 25/11/2020.
//  Copyright © 2020 Selmo. All rights reserved.
//

import Foundation

struct Piece: Hashable {
    var isWinning: Bool
    let type: Int // 0 - empty, 1 - Red, 2 - Yellow
}

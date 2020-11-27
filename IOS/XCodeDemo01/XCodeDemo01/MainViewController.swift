//
//  ViewController.swift
//  XCodeDemo01
//
//  Created by Selmo on 26/10/2020.
//  Copyright © 2020 Selmo. All rights reserved.
//

import UIKit

class MainViewController: UIViewController {

    @IBOutlet weak var MainLabel: UILabel!
    
    @IBOutlet weak var MainButton: UIButton!
    
    var counter: Int = 0;
    
    
    @IBAction func MainButtonTouchUpInside(_ sender: UIButton) {
        counter += 1
        MainLabel.text = "Val: \(counter)"
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
        
        MainLabel.text = "Hello there!"
        
        var numbers = [1, 2, 3, 4, 5]
    }

    func hello(aext aint: String, bext bint: String) {
        print(aint + " " + bint)
    }

}


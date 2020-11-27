//
//  Tab1ViewController.swift
//  MultiMVC
//
//  Created by Selmo on 06/11/2020.
//  Copyright © 2020 Selmo. All rights reserved.
//

import UIKit

class Tab1ViewController: UIViewController {

    @IBOutlet weak var tab1Label: UILabel!
    
    override func viewDidLoad() {
        super.viewDidLoad()

        
        print("Tab1")
        
        if let tabBarController = tabBarController as? MainTabBarController{
            print("\(tabBarController.id)")
        }
        
        // Do any additional setup after loading the view.
    }
    

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
    }
    */

}

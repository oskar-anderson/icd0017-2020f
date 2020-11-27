//
//  Tab2ViewController.swift
//  MultiMVC
//
//  Created by Selmo on 06/11/2020.
//  Copyright © 2020 Selmo. All rights reserved.
//

import UIKit

class Tab2ViewController: UIViewController {

    @IBAction func buttonTouchUpInside(_ sender: UIButton) {
        if let tabVC = tabBarController as? MainTabBarController{
            print("\(tabVC.id)")
            if let sisterVC = tabVC.viewControllers![0] as? Tab1ViewController {
                sisterVC.tab1Label.text = "foobar"
            }
        }
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        print("Tab2")

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

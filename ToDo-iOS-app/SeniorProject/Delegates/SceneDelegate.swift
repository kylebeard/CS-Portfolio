//
//  SceneDelegate.swift
//  SeniorProject
//
//  Created by Kyle Beard on 4/11/20.
//  Copyright © 2020 Kyle Beard. All rights reserved.
//

import UIKit
import SwiftUI

class SceneDelegate: UIResponder, UIWindowSceneDelegate {
   
   var window: UIWindow?
   
   
   func scene(_ scene: UIScene, willConnectTo session: UISceneSession, options connectionOptions: UIScene.ConnectionOptions) {
      
      
      // get data from disk
      let projects: ProjectList = loadProjects()
      let tasks: TaskList = loadTasks()
      
      // Create ContentView and put tasks and projects into the environment
      let contentView = NavigationView {
         ContentView()
            .environmentObject(tasks)
            .environmentObject(projects)
      }
      
      // Use a UIHostingController as window root view controller.
      if let windowScene = scene as? UIWindowScene {
         let window = UIWindow(windowScene: windowScene)
         window.rootViewController = UIHostingController(rootView: contentView)
         self.window = window
         window.makeKeyAndVisible()
      }
   }
   
   
   func sceneDidDisconnect(_ scene: UIScene) {}
   func sceneDidBecomeActive(_ scene: UIScene) {}
   func sceneWillResignActive(_ scene: UIScene) {}
   func sceneWillEnterForeground(_ scene: UIScene) {}
   func sceneDidEnterBackground(_ scene: UIScene) {}
   

}


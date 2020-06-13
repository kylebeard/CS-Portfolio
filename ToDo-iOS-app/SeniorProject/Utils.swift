//
//  Utils.swift
//  SeniorProject
//
//  Created by Kyle Beard on 6/1/20.
//  Copyright © 2020 Kyle Beard. All rights reserved.
//

import Foundation
import SwiftUI



var _text = "Test"
func saveProjects(_ projects: [Project]) {
   let encoder = JSONEncoder()
   if let encoded = try? encoder.encode(projects) {
      UserDefaults.standard.set(encoded, forKey: "Projects")
   }
}

/// Saves the tasks from the "Tasks" tab
/// Does not save any tasks associated with any projects
/// saveProjects(projects:) will save each projects task's
func saveTasks(_ tasks: [Task]){
   let encoder = JSONEncoder()
   if let encoded = try? encoder.encode(tasks) {
      UserDefaults.standard.set(encoded, forKey: "Tasks")
      
   }
}


/// get projects data from disk
func loadProjects() -> ProjectList {
   var projects = ProjectList([Project("Default Project")])
   if let _projects = UserDefaults.standard.data(forKey: "Projects"){
      let decoder = JSONDecoder()
      
      if let decoded = try? decoder.decode([Project].self, from: _projects){
         projects = ProjectList(decoded)
      }
   }
   
   return projects
}

/// get tasks data from disk
func loadTasks() -> TaskList {
   var tasks = TaskList()
   if let _tasks = UserDefaults.standard.data(forKey: "Tasks"){
      let decoder = JSONDecoder()
      
      if let decoded = try? decoder.decode([Task].self, from: _tasks){
         tasks = TaskList(decoded)
      }
   }
   
   return tasks
}


let grayBlue = Color(red: 20/255.0, green: 20/255.0, blue: 30/255.0)
let lightGray = Color(red: 35/255.0, green: 35/255.0, blue: 50/255.0)
let lightGray2 = Color(red: 35/255.0, green: 35/255.0, blue: 50/255.0)

let darkSchemeColor = Color(red: 24/255.0, green: 116/255.0, blue: 205/255.0)
let lightSchemeColor = Color(red: 0/255.0, green: 75/255.0, blue: 175/255.0)
/// color for text when a tasks date is today
let todayColor = Color(red: 25/255.0, green: 118/255.0, blue: 210/255.0)

let lowPriorityColor = Color(red: 1.0, green: 1.0, blue: 0.0)
let medPriorityColor = Color(red: 1.0, green: 0.5, blue: 0.0)
let highPriorityColor = Color(red: 1.0, green: 0.0, blue: 0.0)

/*
 darkSchemePurple : Color(red: 133/255.0, green: 39/255.0, blue: 248/255.0)
 lightSchemePurple: Color(red: 100/255.0, green: 12/255.0, blue: 200/255.0)
 
 blue : Color(red: 29/255.0, green: 34/255.0, blue: 190/255.0)
 Color(red: 62/255.0, green: 67/255.0, blue: 238/255.0)
 Color(red: 67/255.0, green: 72/255.0, blue: 208/255.0)
 rgb(47, 174, 205)
 deek sky blue: 0, 191, 255
 dodger blue: 24,116,205
 red :  Color(red: 217/255.0, green: 0/255.0, blue: 0/255.0)
 Color(red: 232/255.0, green: 59/255.0, blue: 41/255.0)
 
 yellow : rgb(255, 232, 20)
 orange 255,126,0
 
 */

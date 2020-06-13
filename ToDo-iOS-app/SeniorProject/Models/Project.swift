//
//  Project.swift
//  SeniorProject
//
//  Created by Kyle Beard on 6/2/20.
//  Copyright © 2020 Kyle Beard. All rights reserved.
//
/** Date model for a Project */
import Foundation

class Project: Equatable, Identifiable, ObservableObject, Codable {
   
   var title: String
   var tasks: TaskList
   
   init(_ title: String, tasks: TaskList = TaskList()){
      self.title = title
      self.tasks = tasks
   }
   
   static func == (lhs: Project, rhs: Project) -> Bool {
      lhs.id == rhs.id
   }
   
}

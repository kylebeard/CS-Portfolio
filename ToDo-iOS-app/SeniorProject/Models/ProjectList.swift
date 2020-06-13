//
//  ProjectList.swift
//  SeniorProject
//
//  Created by Kyle Beard on 6/2/20.
//  Copyright © 2020 Kyle Beard. All rights reserved.
//
/** Class that holds an array of projects */

import Foundation


class ProjectList: ObservableObject {
   @Published var projects: [Project] {
      didSet { saveProjects(projects) }
   }
   
   init(_ projects: [Project]){
      self.projects = projects
   }
   
   
   /// Add a project if the title is unique
   func add(_ project: Project){
      var dupTitle = false
      for p in projects {
         if p.title == project.title {
            dupTitle = true
         }
      }
      if !dupTitle {
         projects.append(project)
      }
   }
   
   
   func remove(project: Project){
      guard let i = projects.firstIndex(where: { $0.title == project.title}) else {
         return
      }
      projects.remove(at: i)
   }
   
}

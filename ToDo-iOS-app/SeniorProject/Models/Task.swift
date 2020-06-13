//
//   Task.swift
//   SeniorProject
//
//   Created by Kyle Beard on 4/20/20.
//   Copyright © 2020 Kyle Beard. All rights reserved.
//
///   Data model for a Task

import Foundation
import SwiftUI

class Task: Equatable, Identifiable, ObservableObject, Codable {
   let id: UUID
   @Published var title: String
   @Published var priority: Priority
   @Published var reminders: [Date] = []
   @Published var subtasks: [Task] = []
   @Published var projects: [String] = [] // Projects this task has been assigned to
   @Published var completed: Bool
   
   @Published var date_ : Date? // using this so I can know whether a user has selected a date
   var date: Date {
      get {
         guard let date_ = date_ else {
            return Date()
         }
         return date_
      }
      set {
         date_ = newValue
      }
   }
   
   enum CodingKeys: String, CodingKey {
      case title
      case priority
      case completed
      case date_
   }
   
   required convenience init(from decoder: Decoder) throws {
      let values = try decoder.container(keyedBy: CodingKeys.self)
      let title = try values.decode(String.self, forKey: .title)
      let priority = try values.decode(Priority.self, forKey: .priority)
      let completed = try values.decode(Bool.self, forKey: .completed)
      let date_ = try values.decode(Date?.self, forKey: .date_)
      
      self.init(title, date: date_, priority: priority, completed: completed)
      
   }
   
   func encode(to encoder: Encoder) throws {
      var container = encoder.container(keyedBy: CodingKeys.self)
      try container.encode(title, forKey: .title)
      try container.encode(priority, forKey: .priority)
      try container.encode(completed, forKey: .completed)
      try container.encode(date_, forKey: .date_)
   }
   
   /*
    Unimplented functionality: Categories. e.g. 'Work', 'Personal', etc.
    var categories: Set<String>
    */
   
   init(
      _ title: String,
      date: Date? = nil,
      priority: Priority = .none,
      projects: [String] = [],
      reminders: [Date] = [],
      completed:Bool = false
   ){
      id = UUID()
      self.title = title
      date_ = date
      self.priority = priority
      self.projects = projects
      self.reminders = reminders
      self.completed = completed
   }
   
   
   static func == (lhs: Task, rhs: Task) -> Bool {
      lhs.id == rhs.id
   }
   
   func hash(into hasher: inout Hasher) {
      hasher.combine(id)
   }
   
//      func addSubtask(task: Task){
//         subtasks.append(task)
//      }
//   
//      func deleteSubtask(task: Task){
//         if subtasks.contains(task) {
//            subtasks.remove(task)
//         }
//      }
//   
//      func addReminder(_ reminder: Date){
//         if !reminders.contains(reminder) {
//            reminders.insert(reminder)
//         }
//      }
//   
//      func deleteReminder(_ reminder: Date){
//         if reminders.contains(reminder) {
//            reminders.remove(reminder)
//         }
//      }
//   
//      func assignTo(project: String){
//         if !projects.contains(project){
//            projects.insert(project)
//         }
//      }
//
//      func removeFrom(project: String){
//         if projects.contains(project){
//            projects.remove(project)
//         }
//      }
   
   func setPriority(_ priority: Priority){
      self.priority = priority
   }
}



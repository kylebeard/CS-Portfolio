//
//  TaskList.swift
//  SeniorProject
//
//  Created by Kyle Beard on 5/26/20.
//  Copyright © 2020 Kyle Beard. All rights reserved.
/** class for an array of tasks */

import Foundation

class TaskList: ObservableObject, Codable {
   @Published var tasks: [Task]
      
   var completed: [Task] {
      tasks.filter({ $0.completed })
   }
   
   var notCompleted: [Task] {
      tasks.filter({ !$0.completed })
   }
   
   init(_ tasks: [Task] = []){
      self.tasks = tasks
   }
   
   enum CodingKeys: String, CodingKey {
      case taskList
   }
   
   required convenience init(from decoder: Decoder) throws {
      let values = try decoder.container(keyedBy: CodingKeys.self)
      let taskList = try values.decode([Task].self, forKey: .taskList)
      self.init(taskList)
      
   }
   
   func encode(to encoder: Encoder) throws {
      var container = encoder.container(keyedBy: CodingKeys.self)
      try container.encode(tasks, forKey: .taskList)
   }
   
   
   func add(task: Task){
      tasks.append(task)
   }
   
   func delete(_ task: Task){
      guard let i = tasks.firstIndex(where: { $0.id == task.id}) else {
         return
      }
      
      tasks.remove(at: i)
   }
}

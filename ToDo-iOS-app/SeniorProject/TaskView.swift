//
//  TaskView.swift
//  SeniorProject
//
//  Created by Kyle Beard on 4/21/20.
//  Copyright © 2020 Kyle Beard. All rights reserved.
//

/// Structure for representing the view of a task in the app.
/// This is the view of tasks in a list such as the tasks for
/// a given day or week.
/// This is not the TaskFormView for editing or creating a single task.
import SwiftUI

struct TaskRowView: View {
   @State var task: Task
   
   var body: some View {
      Text("Helo")
   }
   
} 

struct TaskView_Previews: PreviewProvider {
   static var previews: some View {
      TaskRowView(task: Task("New Task"))
   }
   
   
}

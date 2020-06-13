//
//  TaskListView.swift
//  SeniorProject
//
//  Created by Kyle Beard on 5/24/20.
//  Copyright © 2020 Kyle Beard. All rights reserved.
//

/** The core view of the app.
    This view shows the user's tasks
    and allows them to interact with them
    by completing/editing/deleting a task */

import SwiftUI
struct TaskListView: View {
   @EnvironmentObject var tasks: TaskList
   @EnvironmentObject var projects: ProjectList
   @Binding var needRefresh: Bool
   @State var isEditFormPresented = false
   
   // Dark or light mode
   @Environment(\.colorScheme)
   var colorScheme
   
   var hideCompleted: Bool
   var sortType: SortType
   var project: Project? // Is this TaskList associated with a project?
   
   // Are we editing the main task list or a specific project's task list?
   // this computed value will give us the correct task list
   var tasks_: TaskList {
      if project != nil {
         return project!.tasks
      } else {
         return tasks
      }
   }
   
   var body: some View {
      ScrollView(.vertical, showsIndicators: true) {
         
         // First list for all uncompleted tasks
         ForEach(tasks_.notCompleted.sorted(by: { sort($0, $1) })) {
            task in
            
            if !task.completed {
               TaskRowView(needRefresh: self.$needRefresh, project: self.project)
                  .environmentObject(task)
                  .environmentObject(self.tasks_)
                  .contextMenu {
                     Button(action: {
                        self.tasks_.delete(task)
                        self.saveAndUpdate()
                     }){
                        Text("Delete")
                     }
               }
            }
         }
         
         if !hideCompleted {
            // Second list for all completed tasks
            ForEach(tasks_.completed.sorted(by: { sort($0, $1) })) {
               task in
               
               TaskRowView(needRefresh: self.$needRefresh, project: self.project)
                  .environmentObject(task)
                  .environmentObject(self.tasks_)
                  .opacity(0.65)
                  .contextMenu {
                     Button(action: {
                        self.tasks_.delete(task)
                        self.saveAndUpdate()
                     }) {
                        Text("Delete")
                     }
               }
            }
         }
      }
      .animation(Animation.spring(dampingFraction: 0.9).speed(2))
         
      // this is a hack to get the list to refresh
      // when a task is marked as completed
      .accentColor(needRefresh ? .white : .black)
         
   }
   
 
   
   func saveAndUpdate(){
      project == nil ? saveTasks(self.tasks.tasks)
         : saveProjects(self.projects.projects)
      
      self.needRefresh.toggle()
   }
   
   // passed in to Array.sorted(by:) that will sort either by Date or Priority
   func sort(_ t1: Task, _ t2: Task) -> Bool {
      if sortType == .date {
         return t1.date < t2.date
      } else {
         let p1 = Int(t1.priority.rawValue)
         let p2 = Int(t2.priority.rawValue)
         return p1! < p2!
      }
   }
}


struct TaskListView_Previews: PreviewProvider {
   static var tasks = TaskList([
      Task("hello"),
      Task("hello", completed:true),
      Task("goodbye"),
      Task("shouldn't appear", completed:true)
   ])
   
   @State static var n = false
   static var previews: some View {
      return TaskListView(needRefresh: $n, hideCompleted: false, sortType: .date).environmentObject(tasks)
   }
}

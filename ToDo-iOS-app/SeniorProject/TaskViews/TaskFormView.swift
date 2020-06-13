//
//  CaptureView.swift
//  SeniorProject
//
//  Created by Kyle Beard on 4/11/20.
//  Copyright © 2020 Kyle Beard. All rights reserved.
//

import SwiftUI


/// View for creating and editing a Task
struct TaskFormView: View {
   @EnvironmentObject var tasks: TaskList
   @EnvironmentObject var task: Task
   @EnvironmentObject var projects: ProjectList
   var isNewTask: Bool
   
   /**
    These variables are used in 2 different ways.
    
    If a new task is being created they are the variables
    used in the Form and are used to create the new task when
    the user hits save.
    
    If the user is editing an existing task, they are used
    to save the tasks original state so that if the user hits cancel,
    the task can be restored to it's original state.
    */
   @State var title = ""
   @State var date = Date()
   @State var priority: Priority = .none
   
   @Environment(\.presentationMode)
   var presentationMode
   
   @Environment(\.colorScheme)
   var colorScheme
   
   var accentColor: Color {
      colorScheme == .light ? lightSchemeColor : darkSchemeColor
   }
   
   var project: Project?
   
   var tasks_: TaskList {
      if project != nil {
         return project!.tasks
      } else {
         return tasks
      }
   }
   
   var body: some View {
      NavigationView {
         VStack(spacing: 25) {
            TaskFormNavBarView(cancelAction: self.onCancelTapped,
                               saveAction: self.onSaveTapped,
                               isSaveDisabled: !canSave(),
                               isNewTask: isNewTask)
               .padding()
            
            Form {
               Section {
                  TextField("Title", text: isNewTask ? $title : $task.title)
                  
                  DatePicker(selection: isNewTask ? $date : $task.date,
                             displayedComponents: .date) {
                              Text("Due Date")
                                 .foregroundColor(.red)
                  }
               }
               
               Section(header: Text("Priority").font(.system(size: 20, weight: .semibold))) {
                  Picker(selection: isNewTask ? $priority : $task.priority,
                         label: Text("Priority")) {
                           ForEach(Priority.allCases){ priority in
                              Text(String(describing: priority).capitalized)
                                 .tag(priority)
                           }
                  }.pickerStyle(SegmentedPickerStyle())
               }
            }
            .accentColor(accentColor)
            .padding(.top, -20)
            
         }
         .navigationBarItems(
            leading: TaskFormNavBarView(cancelAction: self.onCancelTapped,
                                        saveAction: self.onSaveTapped,
                                        isSaveDisabled: !canSave(),
                                        isNewTask: isNewTask).layoutPriority(2.0)
         )
         .navigationBarHidden(true)
      }
      .onAppear(){
         if !self.isNewTask {
            self.saveTaskState()
         }
      }
   }
   
   func getPriorityColor(_ val: Priority) -> Color {
      return (val == .high) ? highPriorityColor
         : (val == .medium) ? medPriorityColor
         : (val == .low) ? lowPriorityColor
         : .gray
   }
   
   func canSave() -> Bool {
      isNewTask ?
         (self.title.count != 0) : (self.task.title.count != 0)
   }
   
   func saveTaskState(){
      self.title = task.title
      self.date = task.date
      self.priority = task.priority
   }
   
   func restoreTaskState(){
      task.title = self.title
      task.date = self.date
      task.priority = self.priority
   }
   
   func onCancelTapped(){
      if !isNewTask { restoreTaskState() }
      self.presentationMode.wrappedValue.dismiss()
   }
    
   func onSaveTapped(){
      if isNewTask {
         let newtask = Task(title, date: date, priority: priority)
         tasks_.add(task: newtask)
      }
      
      project == nil ? saveTasks(self.tasks.tasks)
                     : saveProjects(self.projects.projects)
      
      self.presentationMode.wrappedValue.dismiss()
      
   }
}

struct TaskFormNavBarView: View {
   let cancelAction: () -> Void
   let saveAction: () -> Void
   let isSaveDisabled: Bool
   let isNewTask: Bool
   
   @Environment(\.colorScheme)
   var colorScheme
   
   var accentColor: Color {
      colorScheme == .light ? lightSchemeColor : darkSchemeColor
   }
   
   var body: some View {
      HStack {
         Button(action: cancelAction) { Text("Cancel")
            .foregroundColor(darkSchemeColor)}
         
         Spacer()
         
         Text(isNewTask ? "Create Task" : "Edit Task" )
            .font(.system(size: 18, weight: .semibold))
            .offset(x:-12)
         
         Spacer()
         
         Button(action: saveAction) {
            Text("Save")
            
         }
         .foregroundColor(isSaveDisabled ? .gray : accentColor)
         .disabled(isSaveDisabled)
         
      }
   }
}

struct CaptureView_Previews: PreviewProvider {
   @State static var tasks = TaskList()
   static var previews: some View {
      TaskFormView(isNewTask: false).environmentObject(tasks)
   }
}

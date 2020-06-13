//
//  TaskRowView.swift
//  SeniorProject
//
//  Created by Kyle Beard on 4/21/20.
//  Copyright © 2020 Kyle Beard. All rights reserved.
//

/// Structure for representing the view of a single task in
/// in a TaskListView

import SwiftUI
import Foundation

struct TaskRowView: View {
   @EnvironmentObject var task: Task
   @EnvironmentObject var tasks: TaskList
   @EnvironmentObject var projects: ProjectList
   @Binding var needRefresh: Bool
   
   @Environment(\.colorScheme)
   var colorScheme
   
   var backgroundColor: Color {
      colorScheme == .light ? .white : lightGray
   }
   
   var shadowColor: Color {
      colorScheme == .light ? .black : .clear // no shadow in dark scheme
   }
   
   var cornerRadius = 16.0
   var project: Project? // is this task associated with a project
   
   var body: some View {
      HStack {
         CheckBoxView(needRefresh: $needRefresh, completed: $task.completed)
            .padding(8)
         
         TaskRowTextView()
            .environmentObject(task)
            .environmentObject(tasks)
            .environmentObject(projects)
            .fixedSize(horizontal: false, vertical: true)
         
         Spacer()
      }
         // save to disk if the task changes
         .onReceive(task.$completed, perform: { _ in self.saveAndUpdate() })
         .padding(5)
         .background(backgroundColor)
         .cornerRadius(16.0)
         .padding([.horizontal], 16.0)
         .padding([.top], 5.0)
         .padding(.bottom, -10)
         .shadow(color: shadowColor, radius: 1.0)
         .accentColor(needRefresh ? .white : .black) // a hack to refresh the view
        
      
      
      
   }
   
   func saveAndUpdate(){
      project == nil ? saveTasks(self.tasks.tasks)
                     : saveProjects(self.projects.projects)
         
      needRefresh.toggle()
   }
   
   
   /// View for everything but the Check Box
   struct TaskRowTextView: View {
      @EnvironmentObject var task: Task
      @EnvironmentObject var tasks: TaskList
      @EnvironmentObject var projects: ProjectList
      @State var isEditFormPresented = false
      
      var body: some View {
         
         VStack(alignment: .leading) {
            
            Text(task.title)
               .font(.body)
               .strikethrough(task.completed, color: .black)
            
            HStack {
               
               Text(getDateString())
                  .padding(.trailing, 20.0)
                  .font(.system(size:12,weight:.regular))
                  .foregroundColor(getDateColor())
               
               Image(systemName: "flag.fill")
                  .foregroundColor(getPriorityColor())
                  .font(.system(size:12,weight:.medium))
               
            }
            .padding(.top, -8)
         }
         .onTapGesture {
            self.isEditFormPresented = true
         }
         .sheet(isPresented: self.$isEditFormPresented, onDismiss: nil){
            TaskFormView(isNewTask: false)
               .environmentObject(self.tasks)
               .environmentObject(self.task)
               .environmentObject(self.projects)
         }
            
         
      }
        
      
      // returns a formatted String of a Date in the format "Sun, Jan 1"
      func getDateString() -> String {
         var calendar = Calendar(identifier: .gregorian)
         calendar.locale = Locale(identifier: "en_US")
         
         return calendar.isDateInToday(task.date) ? "Today"
            : calendar.isDateInTomorrow(task.date) ? "Tomorrow"
            : format(date: task.date)
      }
      
      func isPastDue() -> Bool{
         task.date < Date()
      }
      
      // Date will appear red if past due
      // blue if the date is today, gray otherwise
      func getDateColor() -> Color {
         let str = getDateString()
         
         if str == "Today" {
            return todayColor
         } else if isPastDue() {
            return Color.red
         } else {
            return Color.secondary
         }
      }
      
      // helper for formatting the Date String
      func format(date: Date) -> String {
         var calendar = Calendar(identifier: .gregorian)
         calendar.locale = Locale(identifier: "en_US")
         let mdw = calendar.dateComponents([.month, .day, .weekday], from: date)
         
         let weekday = calendar.shortWeekdaySymbols[mdw.weekday! - 1]
         let month = calendar.shortMonthSymbols[mdw.month! - 1]
         let dayOfMonth = mdw.day!
         
         return "\(weekday), \(month) \(dayOfMonth)"
      }
      
      // Colors for each priority level
      func getPriorityColor() -> Color {
         let val = task.priority
         
         return (val == .high) ? highPriorityColor
            : (val == .medium) ? medPriorityColor
            : (val == .low) ? lowPriorityColor
            : .gray
      }
      
   }
}

struct TaskView_Previews: PreviewProvider {
   @State static var pTask = Task("New Task", priority: .none)
   @State static var taskList = TaskList()
   @State static var needRefresh = false
   static var previews: some View {
      return TaskRowView(needRefresh: $needRefresh)
         .environmentObject(pTask)
         .environmentObject(taskList)
   }
}

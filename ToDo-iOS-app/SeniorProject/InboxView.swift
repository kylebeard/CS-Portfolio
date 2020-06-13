//
//  SwiftUIView.swift
//  SeniorProject
//
//  Created by Kyle Beard on 5/24/20.
//  Copyright © 2020 Kyle Beard. All rights reserved.
//

import SwiftUI

enum SortType {
   case date
   case priority
}

struct InboxView: View {
   @State var isAddFormPresented = false
   @EnvironmentObject var tasks: TaskList
   @EnvironmentObject var projects: ProjectList
   @EnvironmentObject var projectTasks: TaskList
   @State var hideCompleted = false
   @Binding var needRefresh: Bool
   @State var sortByPriority = false
   var navBarTitle: String
   var project: Project?
   
   var sortType: SortType {
      if sortByPriority {
         return .priority
      } else {
         return .date
      }
   }
   
   var body: some View {
      
      VStack {
         VStack(alignment: .leading) {
            Text(navBarTitle)
               .font(.system(size: 40, weight: .bold))
               .padding(.leading, 10)
               .padding([.top,.bottom], -60)
            
            VStack {
               
               HStack {
                  Text("Hide Completed Tasks")
                     .font(.system(size: 14))
                     .padding(.trailing, 10)
                  
                  Button(action: {
                     
                     self.needRefresh.toggle()
                     self.hideCompleted.toggle()
                  }) {
                     Image(systemName: hideCompleted ? "checkmark.circle.fill" : "circle")
                        .font(.system(size: 20))
                        .foregroundColor(darkSchemeColor).animation(.default)
                  }
                  
                  Spacer()
               }
               .padding([.horizontal,.top])
               
               HStack {
                  Text("Sort by Priority")
                     .font(.system(size: 14))
                     .padding(.trailing, 10)
                  
                  Button(action: {
                     
                     self.needRefresh.toggle()
                     self.sortByPriority.toggle()
                     
                  }) {
                     Image(systemName: sortByPriority ? "checkmark.circle.fill" : "circle")
                        .font(.system(size: 20))
                        .foregroundColor(darkSchemeColor).animation(.default)
                  }
                  
                  Spacer()
               }
               .padding([.horizontal, .bottom])
               
            }.padding(.top, -30)
         }
         ZStack {
            
            TaskListView(needRefresh: $needRefresh,
                         hideCompleted: hideCompleted,
                         sortType: sortType,
                         project: project)
            
            AddTaskButtonView(){
               self.isAddFormPresented = true
            }
            .sheet(isPresented: $isAddFormPresented) {
               TaskFormView(isNewTask: true, project: self.project)
                  .environmentObject(self.tasks)
                  .environmentObject(self.projects)
                  .environmentObject(Task(""))
            }
            
         }
         
      }
      .navigationBarHidden(true)
      .accentColor(needRefresh ? .white : .black)
      .padding(.bottom, 10)
   }
   
}

struct SwiftUIView_Previews: PreviewProvider {
   @State static var needRefresh = false
   static var previews: some View {
      InboxView(needRefresh: $needRefresh, navBarTitle: "Tasks")
   }
}

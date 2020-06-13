//
//  ContentView.swift
//  SeniorProject
//
//  Created by Kyle Beard on 4/11/20.
//  Copyright © 2020 Kyle Beard. All rights reserved.
//

import SwiftUI
//import Foundation.NSUserDefaults

struct ContentView: View {
   @EnvironmentObject var tasks: TaskList
   @EnvironmentObject var projects: ProjectList
   @State var needRefresh = false
   
   var body: some View {
      TabView {
         InboxView(needRefresh: $needRefresh, navBarTitle: "Tasks")
            .environmentObject(tasks)
            .accentColor(needRefresh ? .white : .black)
            .tabItem {
               Image(systemName: tasks.notCompleted.count > 0 ? "tray.full.fill" : "tray.fill")
               Text("Tasks")
         }
         
         ProjectsView(needRefresh: $needRefresh)
            .tabItem {
               Image(systemName: "folder.fill")
               Text("Projects")
         }
         .accentColor(needRefresh ? .white : .black)
      }
   }
   
   
}



struct ContentView_Previews: PreviewProvider {
   static var previews: some View {
      ContentView()
   }
}

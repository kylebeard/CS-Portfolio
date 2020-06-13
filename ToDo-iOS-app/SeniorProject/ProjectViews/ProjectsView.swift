//
//  ProjectsView.swift
//  SeniorProject
//
//  Created by Kyle Beard on 6/2/20.
//  Copyright © 2020 Kyle Beard. All rights reserved.
//

 ///   A view for the list of projects a user has.
 ///  Each project will contain a navigation link
 ///  to the tasks associated with the selected project
 
import SwiftUI

struct ProjectsView: View {
   @EnvironmentObject var projects: ProjectList
   @Binding var needRefresh: Bool
   @State var isAddFormVisible = false
   
   // Dark or light mode
   @Environment(\.colorScheme)
   var colorScheme
   
   var body: some View {
      
      VStack(alignment: .leading) {
         // Heading
         HStack {
            Text("Projects").font(.system(size: 40, weight: .bold))
         }
         .padding(.top, -40)
         .padding(.leading, 10)
         
         ZStack {
            
            // The list of projects
            List {
               ForEach(projects.projects){ project in
                  if project.title.count > 0 {
                     NavigationLink(
                        destination:
                        InboxView(needRefresh: self.$needRefresh, navBarTitle: project.title, project: project)
                           .environmentObject(project.tasks)
                           .accentColor(self.needRefresh ? .white : .black)
                        
                     ){
                        Text(project.title)
                     }
                  }
               }
               .onDelete(perform: onDelete)
            }
            
            // Button to add a Project
            AddTaskButtonView(){
               self.isAddFormVisible = true
            }
            .offset(y: -10)
            .sheet(isPresented: $isAddFormVisible){
               AddProjectForm(needRefresh: self.$needRefresh)
                  .environmentObject(self.projects)
            }
         }
         .padding(.top, -20)
      }
   }
   
   // used for swipeable deleting in a list
   private func onDelete(offsets: IndexSet) {
      projects.projects.remove(atOffsets: offsets)
   }
   
}
//struct ProjectsView_Previews: PreviewProvider {
//   @State static var projects = [Project("P1")]
//    static var previews: some View {
//      ProjectsView(projects: $projects)
//    }
//}

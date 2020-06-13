//
//  AddProjectForm.swift
//  SeniorProject
//
//  Created by Kyle Beard on 6/9/20.
//  Copyright © 2020 Kyle Beard. All rights reserved.
//

/// Simple form to create a new project
import SwiftUI

struct AddProjectForm: View {
   @State var newProjectName = ""
   @Binding var needRefresh: Bool
   @EnvironmentObject var projects: ProjectList
   
   // Dark or light mode
   @Environment(\.colorScheme)
   var colorScheme
   
   // The current presenting view -
   // only used to dismiss the add form on cancel or create
   @Environment(\.presentationMode)
   var presentationMode
   
   
   var body: some View {
      VStack(spacing: 15) {
         
         // Header
         Text("Create Project").padding(.top, 15).font(.headline)
         
         Divider()
         
         // Field to enter the name of the project
         TextField("Title", text: $newProjectName)
            .padding()
         
         // Row for Cancel and Create buttons
         HStack {
            Spacer()
            Button(action: self.onCancelTapped){
               Text("Cancel")
            }
            Spacer()
            Button(action: self.onCreateTapped){
               Text("Create")
            }.disabled(newProjectName.count == 0) // don't allow a project with no name
            Spacer()
         }
         Spacer()
      }
   }
   
   /// On cancel we just dismiss the add form
   func onCancelTapped(){
      self.presentationMode.wrappedValue.dismiss()
   }
   
   /// When user selects create - add the project to the project list
   /// then save the updated project list to storage
   func onCreateTapped(){
      projects.add(Project(newProjectName))
      saveProjects(self.projects.projects)
      self.needRefresh.toggle()
      self.presentationMode.wrappedValue.dismiss()
   }
   
}

//struct AddProjectForm_Previews: PreviewProvider {
//    static var previews: some View {
//        AddProjectForm()
//    }
//}

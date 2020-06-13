//
//  CheckBoxView.swift
//  SeniorProject
//
//  Created by Kyle Beard on 5/18/20.
//  Copyright © 2020 Kyle Beard. All rights reserved.
//

import Foundation
import SwiftUI


struct CheckBoxView: View {
   @Binding var needRefresh: Bool
   @Binding var completed : Bool
   let size: CGFloat = 25
   
   @Environment(\.colorScheme)
   var colorScheme
   
   var color: Color {
      colorScheme == .light ? lightSchemeColor : darkSchemeColor
   }
   
   var body: some View {
      Button(action: {
         
         self.needRefresh.toggle()
         self.completed.toggle()
         
      }){
         Image(systemName: completed ? "checkmark.circle.fill" : "circle")
            .font(.system(size: size))
            .foregroundColor(color)
            .animation(.default)
         
      }
   }
}

//struct CheckBoxView_Previews: PreviewProvider {
//   @State static var task = Task("Hello from CheckBoxView_Previews1")
//   @State static var task2 = Task("Hello from CheckBoxView_Previews2")
//   @State static var task3 = Task("Hello from CheckBoxView_Previews3")
//   static var previews: some View {
//      return VStack {
//         CheckBoxView(task: task, redrawOnChangeOf: $task.completed)
//         CheckBoxView(task: task, redrawOnChangeOf: $task2.completed)
//         CheckBoxView(task: task, redrawOnChangeOf: $task3.completed)
//      }
//   }
//}



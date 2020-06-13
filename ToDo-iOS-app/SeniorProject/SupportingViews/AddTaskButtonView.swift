//
//  AddTaskButtonView.swift
//  SeniorProject
//
//  Created by Kyle Beard on 5/30/20.
//  Copyright © 2020 Kyle Beard. All rights reserved.
//


import SwiftUI

struct AddTaskButtonView: View {
   private let action: () -> Void
   init(action: @escaping () -> Void = {}) {
      self.action = action
   }
   
   @Environment(\.colorScheme)
   var colorScheme
 
   var color: Color {
      colorScheme == .light ? lightSchemeColor : darkSchemeColor
   }
   
   var backgroundColor: Color {
      colorScheme == .light ? .white : .black
   }
   
   var shadowRadius: CGFloat {
      colorScheme == .light ? 4.0 : 0.0
   }
   
   var body: some View {
      VStack {
         Spacer()
         
         Button(action: action) {
            
            Image(systemName: "plus.circle.fill")
               .resizable()
               .frame(width: 54.0, height: 54.0)
               .background(backgroundColor)
               .foregroundColor(color)
               .clipShape(Circle())
               //.shadow(color: .gray, radius: shadowRadius)
            
         }
         .offset(y: -16)
         
      }
   }
}


struct RightDownFloatButton_Previews: PreviewProvider {
   static var previews: some View {
      AddTaskButtonView()
   }
}


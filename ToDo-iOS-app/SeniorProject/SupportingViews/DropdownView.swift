//
//  DropDownView.swift
//  SeniorProject
//
//  Created by Kyle Beard on 5/28/20.
//  Copyright © 2020 Kyle Beard. All rights reserved.
//

import SwiftUI

//struct Dropdown: View {
//   var options: [String]
//   var onSelect: ((_ key: String) -> Void)?
//   
//   var body: some View {
//      VStack(alignment: .leading, spacing: 0) {
//         ForEach(self.options, id: \.self) { option in
//            DropdownOptionElement(val: option.val, key: option.key, onSelect: self.onSelect)
//         }
//      }
//         
//      .background(Color.white)
//      .cornerRadius(dropdownCornerRadius)
//      .overlay(
//         RoundedRectangle(cornerRadius: dropdownCornerRadius)
//            .stroke(Color.coreUIPrimary, lineWidth: 1)
//      )
//   }
//}
//
//struct DropdownView: View {
//   @State var shouldShowDropdown = false
//   @Binding var displayText: String
//   var options: [DropdownOption]
//   var onSelect: ((_ key: String) -> Void)?
//   
//   let buttonHeight: CGFloat = 30
//   var body: some View {
//      Button(action: {
//         self.shouldShowDropdown.toggle()
//      }) {
//         HStack {
//            Text(displayText)
//            Spacer()
//               .frame(width: 20)
//            Image(systemName: self.shouldShowDropdown ? "chevron.up" : "chevron.down")
//         }
//      }
//      .padding(.horizontal)
//      .cornerRadius(dropdownCornerRadius)
//      .frame(height: self.buttonHeight)
//      .overlay(
//         RoundedRectangle(cornerRadius: dropdownCornerRadius)
//            .stroke(Color.coreUIPrimary, lineWidth: 1)
//      )
//         .overlay(
//            VStack {
//               if self.shouldShowDropdown {
//                  Spacer(minLength: buttonHeight + 10)
//                  Dropdown(options: self.options, onSelect: self.onSelect)
//               }
//            }, alignment: .topLeading
//      )
//         .background(
//            RoundedRectangle(cornerRadius: dropdownCornerRadius).fill(Color.white)
//      )
//   }
//}
//

struct DropdownOverlay: View {
   var options : [String]
   @Binding var selectedValue: String
   var body: some View{
      VStack(alignment: .leading, spacing: 0) {
         ForEach(self.options, id: \.self){ option in
            Button(action: { self.selectedValue = option }){
               Text(option)
            }.frame(width: 170, height: 35, alignment: .center).foregroundColor(.black)
         }
      }.background(Color.white)
         .cornerRadius(10)
         .overlay(
            RoundedRectangle(cornerRadius: 10)
               .stroke(Color.gray, lineWidth: 1)
      )
   }
}

struct DropdownView: View {
   var body: some View {
      Text("he")
   }
}


struct DropDownView_Previews: PreviewProvider {
   static var options = ["Today", "Tomorrow", "This Week"]
   @State static var selectedValue = "Today"
   static var previews: some View {
      DropdownOverlay(options: options, selectedValue: $selectedValue)
   }
}

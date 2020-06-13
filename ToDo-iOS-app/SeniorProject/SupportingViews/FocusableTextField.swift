//
//  FocusableTextField.swift
//  SeniorProject
//
//  Created by Kyle Beard on 5/31/20.
//  Copyright © 2020 Kyle Beard. All rights reserved.
//

import Foundation
import SwiftUI

struct KeyboardTypeView: View {
   @State var firstName = ""
   @State var lastName = ""
   @State var focused: [Bool] = [true, false]
   
   var body: some View {
      Form {
         Section(header: Text("Your Info")) {
            FocusableTextField(keyboardType: .default, returnVal: .default, tag: 0, placeHolder: nil, text: self.$firstName, isfocusAble: self.$focused)
            FocusableTextField(keyboardType: .default, returnVal: .done, tag: 1, placeHolder: nil, text: self.$lastName, isfocusAble: self.$focused)
            Text("Full Name :" + self.firstName + " " + self.lastName)
         }
      }
   }
}

struct CustomTextField: UIViewRepresentable {
   
   class Coordinator: NSObject, UITextFieldDelegate {
      
      @Binding var text: String
      var didBecomeFirstResponder = false
      
      init(text: Binding<String>) {
         _text = text
      }
      
      func textFieldDidChangeSelection(_ textField: UITextField) {
         text = textField.text ?? ""
      }
      
   }
   
   @Binding var text: String
   var isFirstResponder: Bool = false
   var placeholder: String?
   func makeUIView(context: UIViewRepresentableContext<CustomTextField>) -> UITextField {
      let textField = UITextField(frame: .zero)
      textField.placeholder = placeholder
      textField.delegate = context.coordinator
      return textField
   }
   
   func makeCoordinator() -> CustomTextField.Coordinator {
      return Coordinator(text: $text)
   }
   
   func updateUIView(_ uiView: UITextField, context: UIViewRepresentableContext<CustomTextField>) {
      uiView.text = text
      if isFirstResponder && !context.coordinator.didBecomeFirstResponder  {
         uiView.becomeFirstResponder()
         context.coordinator.didBecomeFirstResponder = true
      }
   }
}



struct FocusableTextField: UIViewRepresentable {
   let keyboardType: UIKeyboardType
   let returnVal: UIReturnKeyType
   var tag: Int
   let placeHolder: String?
   @Binding var text: String
   @Binding var isfocusAble: [Bool]
   
   func makeUIView(context: Context) -> UITextField {
      let textField = UITextField(frame: .zero)
      textField.placeholder = placeHolder
      textField.keyboardType = self.keyboardType
      textField.returnKeyType = self.returnVal
      textField.tag = self.tag
      textField.delegate = context.coordinator
      textField.autocorrectionType = .no
      
      return textField
   }
   
   func updateUIView(_ uiView: UITextField, context: Context) {
      if isfocusAble[tag] {
         uiView.becomeFirstResponder()
      } else {
         uiView.resignFirstResponder()
      }
   }
   
   func makeCoordinator() -> Coordinator {
      Coordinator(self)
   }
   
   class Coordinator: NSObject, UITextFieldDelegate {
      var parent: FocusableTextField
      
      init(_ textField: FocusableTextField) {
         self.parent = textField
      }
      
      func updatefocus(textfield: UITextField) {
         textfield.becomeFirstResponder()
      }
      
//      func textFieldShouldReturn(_ textField: UITextField) -> Bool {
//         
//         if parent.tag == 0 {
//            parent.isfocusAble = [false, true]
//            parent.text = textField.text ?? ""
//         } else if parent.tag == 1 {
//            parent.isfocusAble = [false, false]
//            parent.text = textField.text ?? ""
//         }
//         return true
//      }
      
   }
}

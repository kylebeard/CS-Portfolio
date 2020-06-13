//
//  DatePickerView.swift
//  SeniorProject
//
//  Created by Kyle Beard on 4/21/20.
//  Copyright © 2020 Kyle Beard. All rights reserved.
//

import SwiftUI

struct DatePickerView: View {
   
   var dateFormatter: DateFormatter {
      let formatter = DateFormatter()
      formatter.dateStyle = .full
      return formatter
   }
   
   // Default to 2 hours from now
   @State private var selectedDate = Date(timeIntervalSinceNow: 7200)
   
   var body: some View {
      VStack {
         Text("Select a future date").font(.largeTitle)
         
         // 3.
         DatePicker(selection: $selectedDate, in: Date()..., displayedComponents: [.hourAndMinute, .date]) {
            Text("") 
            }.padding(30)
             
         
         // 4.
         Text("Selected Date is \(selectedDate, formatter: dateFormatter)")
            .font(.body)
      }
   }
}

struct DatePickerView_Previews: PreviewProvider {
   static var previews: some View {
      DatePickerView()
   }
}

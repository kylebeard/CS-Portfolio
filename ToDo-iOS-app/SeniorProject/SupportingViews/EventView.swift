//
//  EventView.swift
//  SeniorProject
//
//  Created by Kyle Beard on 5/27/20.
//  Copyright © 2020 Kyle Beard. All rights reserved.
//

import SwiftUI

struct EventView: View {
   @State private var offset: CGSize = .zero
   @State private var currentCard = 0
   @State private var didJustSwipe = false
   
   var randomView: some View {
      return Rectangle()
         .foregroundColor(.purple)
         .cornerRadius(20)
         .frame(width: 300, height: 400)
         .shadow(radius: 10)
         .padding()
         .opacity(0.3)
   }
   
   func offset(for i: Int) -> CGSize {
      return i == currentCard ? offset : .zero
   }
   
   var body: some View {
      ZStack{
         ForEach(currentCard..<5, id: \.self) { i in
            self.randomView
               .offset(self.offset(for: i))
               .gesture(self.gesture)
               .animation(.linear(duration: 1))
         }
      }
   }
   
   var gesture: some Gesture {
      DragGesture()
         .onChanged {
            if self.didJustSwipe {
               self.didJustSwipe = false
               self.currentCard += 1
               self.offset = .zero
            } else {
               self.offset = $0.translation
            }
      }
      .onEnded {
         let w = $0.translation.width
         if abs(w) > 100 {
            self.didJustSwipe = true
            let x = w > 0 ? 1000 : -1000
            self.offset = .init(width: x, height: 0)
         } else {
            self.offset = .zero
         }
      }
   }
}

struct EventView_Previews: PreviewProvider {
    static var previews: some View {
        EventView()
    }
}

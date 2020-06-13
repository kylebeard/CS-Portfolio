//
//  Priority.swift
//  SeniorProject
//
//  Created by Kyle Beard
/** This file declares an Enum for how important a specific task is */

import Foundation

enum Priority: String, CaseIterable, Identifiable, Codable {
   var id: String { rawValue }
   
   case high = "1"
   case medium = "2"
   case low = "3"
   case none = "4"
}

extension Priority: CustomStringConvertible {
   var description: String {
      switch self {
      case .high: return "high"
      case .medium: return "medium"
      case .low: return "low"
      case .none: return "none"
      }
   }
   
   
   
   
}

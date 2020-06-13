//
//  ProjectRowView.swift
//  SeniorProject
//
//  Created by Kyle Beard on 6/2/20.
//  Copyright © 2020 Kyle Beard. All rights reserved.
//
/// What each project will look like in the main Project's List
import SwiftUI

struct ProjectRowView: View {
   var project: Project
    var body: some View {
      Text(project.title)
    }
}

struct ProjectRowView_Previews: PreviewProvider {
   @State static var project = Project("P1")
    static var previews: some View {
        ProjectRowView(project: project)
    }
}

//
//  ContainerView.swift
//  NewsSwiftUI-mvvm
//
//  Created by 1 on 22.02.2020.
//  Copyright © 2020 azharkova. All rights reserved.
//

import SwiftUI

import Foundation
import SwiftUI
import Combine

struct ContainerView<Content>: IContainer, View where Content: View&IModelView {
    
    @ObservedObject var containerModel = ContainerModel()
    private var content: Content
    
    public init(content: Content) {
        self.content = content
        self.content.setContainer(container: self)
    }
    
    var body : some View {
        
        ZStack {
            content
            if (self.containerModel.isLoading) {
                ZStack {
                    Rectangle().fill(.black.opacity(0.5)).ignoresSafeArea()
                    ActivityIndicator()
                        .frame(width: 64, height: 64)
                }
            }
            
            
            if containerModel.hasOpaqueLoader {
                LoaderView(isOpaque: true)
                    .transition(.opacity.animation(.easeInOut(duration: 0)))
            }
            
        }
        .alert("Error", isPresented: $containerModel.hasError) {
                    } message: {
            Text(containerModel.errorText)
        }
        .alert(isPresented: $containerModel.hasAlert) {
            Alert(title: Text(containerModel.alert?.title ?? ""),
                  message: Text(containerModel.alert?.message ?? ""),
                  primaryButton: .default (Text(containerModel.alert?.positiveButtonText ?? "")) {
                containerModel.alert?.positiveAction()
            },
                  secondaryButton: .default (Text(containerModel.alert?.negativeButtonText ?? "")) {
                containerModel.alert?.negativeAction()
            })
        }
        
    }
    
}

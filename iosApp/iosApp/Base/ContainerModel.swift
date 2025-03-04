//
//  ContainerModel.swift
//  NewsSwiftUI-mvvm
//
//  Created by 1 on 23.02.2020.
//  Copyright © 2020 azharkova. All rights reserved.
//

import Foundation
import Combine
import shared
import SwiftUI

class ContainerModel:ObservableObject {

    @Published var isLoading: Bool = false
    @Published var hasOpaqueLoader = false
    @Published var animateOpaqueLoaderAppear = false
    @Published var hasError: Bool = false
    @Published var errorText: String = ""
    @Published var hasAlert: Bool = false
    @Published var alert: ErrorState.AlertError?
    
    @Injected private var contentService: ContentService?
    
    private var cancellable = [AnyCancellable]()
    
    init() {
        contentService?.$showOpaqueLoader
            .receive(on: RunLoop.main)
            .sink { [weak self] val in
                self?.hasOpaqueLoader = val
            }.store(in: &cancellable)

        contentService?.$animateOpaqueLoaderAppear
            .receive(on: RunLoop.main)
            .sink { [weak self] val in
                self?.animateOpaqueLoaderAppear = val
            }.store(in: &cancellable)
        
        contentService?.$hasLoader
            .receive(on: RunLoop.main)
            .sink { [weak self] val in
                self?.isLoading = val
            }.store(in: &cancellable)

        contentService?.$hasError
            .receive(on: RunLoop.main)
            .sink { [weak self] val in
                self?.errorText = self?.contentService?.errorText ?? ""
                self?.hasError = val
            }.store(in: &cancellable)
        
        contentService?.$hasAlert
            .receive(on: RunLoop.main)
            .sink { [weak self] val in
                self?.alert = self?.contentService?.alert
                self?.hasAlert = val
            }.store(in: &cancellable)
        
    }

}

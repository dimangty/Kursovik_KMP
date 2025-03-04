//
//  ContentService.swift
//  iosApp
//
//  Created by Дмитрий Быков on 22.02.2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import Foundation
import Combine
import shared

class ContentService {

    var fullscreenErrorText: String?

    @Published private(set) var isShowToast: Bool = false
    @Published private(set) var toast: String = ""
    @Published private(set) var isContentPresented = false
    @Published var animateOpaqueLoaderAppear = false
    @Published var showOpaqueLoader = false
    
    @Published var hasLoader = false
    @Published var hasError = false
    @Published var hasAlert = false
    
    var alert: ErrorState.AlertError?
    var errorText = ""

    private var timer: Timer?
    
    func showLoader() {
        hasLoader = true
    }
    
    func hideLoader() {
        hasLoader = false
    }
    
    func showError(text: String) {
        hasError = true
        errorText = text
    }
    
    func hideError() {
        hasError = false
        errorText = ""
    }
    
    func showAlert(alert: ErrorState.AlertError) {
        hasAlert = true
        self.alert = alert
    }
    
    func hideAlert() {
        hasAlert = false
        alert = nil
    }
    

    func showOpaqueLoader(_ show: Bool, animated: Bool) {
        animateOpaqueLoaderAppear = animated
        showOpaqueLoader = show
    }
}

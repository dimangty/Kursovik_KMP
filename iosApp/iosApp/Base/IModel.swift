//
//  IModel.swift
//  NewsSwiftUI-mvvm
//
//  Created by 1 on 22.02.2020.
//  Copyright © 2020 azharkova. All rights reserved.
//

import Foundation
protocol  IModel:AnyObject {
    var listener:IContainer? {get set}
}


protocol  IContainer {
//    func showError(error: String)
//    
//    func showToast(toast: String)
//    
//    func showLoading()
//    
//    func hideLoading()
}

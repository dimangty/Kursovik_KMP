//
//  iosAppTests.swift
//  iosAppTests
//
//  Created by Дмитрий Быков on 06.09.2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import Testing
import shared
import UIKit

struct iosAppTests {

    @Test func testResource() async throws {
        // Test ResourceManager.companion.getDrawableResource().uiImage
        let drawableResource = ResourceManager.companion.getDrawableResource()
        
        // Get the UIImage - it's non-optional according to compiler
        let image = drawableResource.uiImage
        
        // Test basic properties  
        #expect(image.size.width > 0, "Width should be > 0, but got \(image.size.width)")
        #expect(image.size.height > 0, "Height should be > 0, but got \(image.size.height)")
        
        // Test the original requirement - width should be greater than 40
        #expect(image.size.width > 40, "Width should be > 40, but got \(image.size.width)")
        
        // Test for expected 360x40 dimensions from ic_example.xml
        #expect(image.size.width == 360.0, "Width should be 360 (from ic_example.xml), but got \(image.size.width)")
        #expect(image.size.height == 40.0, "Height should be 40 (from ic_example.xml), but got \(image.size.height)")
    }

}

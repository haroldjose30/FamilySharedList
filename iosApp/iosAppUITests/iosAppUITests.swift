//
//  iosAppUITests.swift
//  iosAppUITests
//
//  Created by Jose Harold on 27/01/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import XCTest

final class iosAppUITests: XCTestCase {
    
    // UI tests must launch the application that they test.
    var app: XCUIApplication!

    override func setUpWithError() throws {

        // Put setup code here. This method is called before the invocation of each test method in the class.
        try super.setUpWithError()
        
        // In UI tests it is usually best to stop immediately when a failure occurs.
        continueAfterFailure = false

        // In UI tests it’s important to set the initial state - such as interface orientation - required for your tests before they run. The setUp method is a good place to do this.
        
        app = XCUIApplication()
        app.launchArguments = ["isRunningUITests"]
        app.launchEnvironment["keySample"] = "valueSample"
        // UI tests must launch the application that they test.
        app.launch()
    }

    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
        app.terminate()
    }
    
    func test_WhenAppIsLaunchedTitleMustBeShowed() throws {
        
        //arrange
        let textTitle = app.textViews[AccessibilityId.FamilyListPage.textTitle.rawValue]
        let expectedValue = "Lista de Compras"
        
        //act
        
        
        //assert
        XCTAssertTrue(textTitle.exists)
        XCTAssertEqual(textTitle.value as? String, expectedValue)
    }
    
    func test_WhenButtonAddIsPressedTheContentMustBeEmpty() throws {
        
        
        //arrange
        let buttonAdd = app.buttons[AccessibilityId.FamilyListPage.buttonAdd.rawValue]
        let textFieldAdd = app.textFields[AccessibilityId.FamilyListPage.textFieldAdd.rawValue]
        
        guard
            buttonAdd.exists,
            textFieldAdd.exists
        else {
            XCTFail("some element no found")
            return
        }
        
        textFieldAdd.tap()
        textFieldAdd.typeText("fakeItem4Test- \(Date())")
         
        //act
        buttonAdd.tap()
        
        //assert
        XCTAssertEqual(textFieldAdd.label, .empty)
        
        //take a screenshot
        let screenshot = app.screenshot()
        let attachment = XCTAttachment(screenshot: screenshot)
        attachment.name = #function
        attachment.lifetime = .keepAlways
        add(attachment)
    }


    func testLaunchPerformance() throws {
        if #available(macOS 10.15, iOS 13.0, tvOS 13.0, watchOS 7.0, *) {
            // This measures how long it takes to launch your application.
            measure(metrics: [XCTApplicationLaunchMetric()]) {
                XCUIApplication().launch()
            }
        }
    }
    
    func testAnyUiForStudy(){
        
    }
}

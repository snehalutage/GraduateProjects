//
//  ImageClassificationCoreMLApp.swift
//  ImageClassificationCoreML
//
//  Created by Snehal on 11/27/21.
//  Name : Snehal Utage
//  ZID  : Z1888637
//  Project : Image classification using CoreML - MobileNetV2 ML model
//  Used the MobileNetV2 Machine learning pre-trained model provided by Apple
//  This model predict the dominant object in a camera frame or image.
//  Imported the CoreML into project and tested the model to classify the fruit images
//  SwitUI interface has
//  1. Image to be classified,
//  2. A classfiy button that returns the prediction provided by ML model for the current image,
//  3. Text to display the result,
//  4. Two button Previous and Next to scroll through images
//  As result displayed the classified fruit label and the class label probability

import SwiftUI

@main
struct ImageClassificationCoreMLApp: App
{
    var body: some Scene
    {
        WindowGroup
        {
            ContentView()
        }
    }
}//end ImageClassificationCoreMLApp

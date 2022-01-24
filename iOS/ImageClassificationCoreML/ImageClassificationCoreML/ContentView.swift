//
//  ContentView.swift
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
import CoreML

struct ContentView: View
{
    //Define state variables
    @State private var classificationLabel: String = ""
    @State private var currentIndex: Int = 0
    
    //Define constant that stores the list of image names to be classified
    let images = ["strawberries", "oranges", "lemon", "pineapple", "fig", "banana"]
    
    //Define Core ML image classifier model - MobileNetV2
    let model: MobileNetV2 = {
        do
        {
            let config = MLModelConfiguration()
            return try MobileNetV2(configuration: config)
        }
        catch
        {
            print(error)
            fatalError("Couldn't create MobileNetV2")
        }
    }()
    
    var body: some View
    {
        //Add views on ZStack
        ZStack
        {
            //LinearGradient for the background color
            LinearGradient(gradient: Gradient(colors: [.white, .gray]), startPoint: .top, endPoint: .bottom)
                .ignoresSafeArea()
            
            //Vertically stack the views
            VStack
            {
                //Text for title
                Text("Fruit Classification")
                    .bold()
                    .font(.largeTitle)
                    .padding()
                
                //Image of the fruit to be classified
                Image(images[currentIndex])
                    .resizable()
                    .frame(width: 250, height: 250)
                    .padding(10)
                    .border(Color.black)
                
                //Button when clicked gives the predicted label with the highest the prediction probability
                Button(
                    action:{ classifyImage() },
                    label:
                        {
                            Text("Classify Image")
                                .bold()
                                .foregroundColor(.white)
                                .padding()
                                .background(Capsule()  .foregroundColor(.black))
                        })
                
                // Text View to display the results of the classification
                Text(classificationLabel)
                    .bold()
                    .font(.title2)
                    .padding()
                
                //HStack to stack buttons to scroll through different images
                HStack(spacing: 20)
                {
                    Button(
                        action:{prevImages() },
                        label:
                            {
                                Text("Previous Image")
                                    .bold()
                                    .foregroundColor(.white)
                                    .padding(10)
                                    .background(Capsule()  .foregroundColor(.black))
                            })
                    
                    Button(
                        action:{ nextImages()},
                        label:
                            {
                                Text("Next Image")
                                    .bold()
                                    .foregroundColor(.white)
                                    .padding(10)
                                    .background(Capsule()  .foregroundColor(.black))
                            })
                }
                
                Spacer()
            }
        }
    }
    
    //Function to scroll to previous images
    private func prevImages()
    {
        if self.currentIndex >= self.images.count
        {
            self.currentIndex = self.currentIndex - 1
        }
        else
        {
            self.currentIndex = 0
        }
    }//end prevImages
    
    //Function to scroll to next images
    private func nextImages()
    {
        if self.currentIndex < self.images.count - 1
        {
            self.currentIndex = self.currentIndex + 1
        }
        else
        {
            self.currentIndex = 0
        }
    }//end nextImages
    
    // Function that changes the image size and format as required by the ML model
    // Calls the ML models prediction to get the classification of image
    // Return the classlabel with highest classlabel probability
    private func classifyImage()
    {
        //Get the current image
        let currentImageName = images[currentIndex]
                
        //Resize the image and convert to format required by the MobileNetV2 ML model
        guard let image = UIImage(named: currentImageName),
              let resizedImage = image.resizeImageTo(size:CGSize(width: 224, height: 224)),
              let buffer = resizedImage.convertToBuffer() else {
              return
        }
        
        //Get the prediction for the current image from the ML model
        let output = try? model.prediction(image: buffer)
        
        //Get the result predicted from model and set the classificationLabel with the highest classification probability
        if let output = output
        {
            let results = output.classLabelProbs.sorted { $0.1 > $1.1 }
            self.classificationLabel =  "\(results[0].key) = \(String(format: "%.2f", results[0].value * 100))%"
        }
    }//end classifyImage
    
}//end ContentView

struct ContentView_Previews: PreviewProvider
{
    static var previews: some View
    {
        ContentView()
    }
}

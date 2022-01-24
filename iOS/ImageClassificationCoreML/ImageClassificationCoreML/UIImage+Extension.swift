//
//  UIImage+Extension.swift
//  ImageClassificationCoreML
//
//  Created by Snehal on 11/28/21.
//  Name : Snehal Utage
//  ZID  : Z1888637
//  Project : Image classification using CoreML - MobileNetV2 ML model

import Foundation
import UIKit

// Add extensions to UIImage to convert the Image or UIImage to CVPixelBuffer
// as required by the MobileNetV2 image classifier model
extension UIImage
{
    
    //Functio to resize the image to the given size and return the resized UIImage
    func resizeImageTo(size: CGSize) -> UIImage?
    {

        UIGraphicsBeginImageContextWithOptions(size, false, 0.0)
        self.draw(in: CGRect(origin: CGPoint.zero, size: size))
        let resizedImage = UIGraphicsGetImageFromCurrentImageContext()!
        UIGraphicsEndImageContext()
        return resizedImage
    }//end resizeImage

    
    //Function to convert the UIImage to a CVPixelBuffer as required by the ML model
    func convertToBuffer() -> CVPixelBuffer?
    {

        let attributes = [
            kCVPixelBufferCGImageCompatibilityKey: kCFBooleanTrue,
            kCVPixelBufferCGBitmapContextCompatibilityKey: kCFBooleanTrue
            ] as CFDictionary

        var pixelBuffer: CVPixelBuffer?

        let status = CVPixelBufferCreate(
            kCFAllocatorDefault, Int(self.size.width),
            Int(self.size.height),
            kCVPixelFormatType_32ARGB,
            attributes,
            &pixelBuffer)

        guard (status == kCVReturnSuccess) else {
        return nil
        }

        CVPixelBufferLockBaseAddress(pixelBuffer!, CVPixelBufferLockFlags(rawValue: 0))

        let pixelData = CVPixelBufferGetBaseAddress(pixelBuffer!)
        let rgbColorSpace = CGColorSpaceCreateDeviceRGB()

        let context = CGContext(
            data: pixelData,
            width: Int(self.size.width),
            height: Int(self.size.height),
            bitsPerComponent: 8,
            bytesPerRow: CVPixelBufferGetBytesPerRow(pixelBuffer!),
            space: rgbColorSpace,
            bitmapInfo: CGImageAlphaInfo.noneSkipFirst.rawValue)

        context?.translateBy(x: 0, y: self.size.height)
        context?.scaleBy(x: 1.0, y: -1.0)

        UIGraphicsPushContext(context!)
        self.draw(in: CGRect(x: 0, y: 0, width: self.size.width, height: self.size.height))
        UIGraphicsPopContext()

        CVPixelBufferUnlockBaseAddress(pixelBuffer!, CVPixelBufferLockFlags(rawValue: 0))

        return pixelBuffer
    }//end convertToBuffer

}//end UIImage extension


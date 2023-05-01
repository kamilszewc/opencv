package com.computinglaboratory.opencv

import org.opencv.imgcodecs.Imgcodecs
import spock.lang.Specification

class OpenCVTest extends Specification {

    def "Load OpenCV"() {
        when:
            OpenCV.loadLibrary()
            def image = Imgcodecs.imread("/Users/kamil/Desktop/DSCF0626.JPG")
            //def image = Imgcodecs.imread("C:\\Users\\Kamil\\Desktop\\IMG_2775.JPG");
            //def image = Imgcodecs.imread("/home/kamil/Pulpit/screenshot.jpg");
        then:
            println image.width()
    }

    def "Detect operating system"() {
        when:
        def os = OpenCV.detectOS()
        then:
        os == OpenCV.OS.MACOS
    }

    def "Detect architecture"() {
        when:
            def arch = OpenCV.detectArchitecture()
        then:
            arch == OpenCV.Arch.ARM64
    }
}

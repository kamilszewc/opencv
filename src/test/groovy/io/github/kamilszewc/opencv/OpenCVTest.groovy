package io.github.kamilszewc.opencv


import org.opencv.imgcodecs.Imgcodecs
import spock.lang.Specification

class OpenCVTest extends Specification {

    def "Load OpenCV"() {
        when:
            OpenCV.loadLibrary()
            def image = Imgcodecs.imread("inputs/fly.jpg")
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

    def "Setting CascadeClassifier"() {
        when:
        OpenCV.loadLibrary()
        def image = org.opencv.objdetect.FaceRecognizerSF
        then:
        println image.width()
    }
}

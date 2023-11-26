# Java OpenCV

## Description

Java (11+) bindings to OpenCV library

Currently supported platforms:
- MacOS (Silicon)
- Windows (x64)
- Linux x64 (Ubuntu 22.04 tested)

## Installation

The compiled libraries are deployed to Maven Central.

Usage with maven:

```xml
<dependency>
    <groupId>io.github.kamilszewc</groupId>
    <artifactId>opencv</artifactId>
    <version>4.7.0-2</version>
</dependency>
```

Usage with gradle:

```groovy
implementation 'io.github.kamilszewc:opencv:4.7.0-2'
```

## Usage

Before using the library, you need to call `io.github.kamilszewc.opencv.OpenCV.loadLibrary()` static function.
Alternatively, you can simply call any other function from OpenCV library.
The reason is that, during that stage, the operating system and cpu architectures are detected
and the right native dynamic libraries of OpenCV are loaded.

Image reading example:

```java
import com.computinglaboratory.opencv.OpenCV;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

public class ImageLoader {

    static {
        try {
            OpenCV.loadLibrary();
        } catch (SystemNotSupportedException | IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    ImageLoader(String filename) {
        Mat mat = Imgcodecs.imread(filename);
    }
}
```

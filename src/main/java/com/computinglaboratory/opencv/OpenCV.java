package com.computinglaboratory.opencv;

import com.computinglaboratory.opencv.exception.SystemNotSupportedException;
import com.computinglaboratory.opencv.exception.UnknownCpuArchitectureException;
import com.computinglaboratory.opencv.exception.UnknownOperatingSystemException;
import org.apache.commons.lang3.SystemUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class OpenCV {

    public enum OS {MACOS, WINDOWS, LINUX};
    public enum Arch {X86, X64, ARM32, ARM64};

    static {
        try {
            loadLibrary();
        } catch (IOException | SystemNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void loadLibrary() throws IOException, SystemNotSupportedException {

        // Create temporary folder
        final Path tempDirectory = Files.createTempDirectory("opencv-");

        // Define the library path
        final Path libraryPath = tempDirectory.resolve("libopencv_java470.dylib");

        // Detect os and arch
        var os = detectOS();
        var arch = detectArchitecture();

        // Choose dynamic library depending on architecture
        String dynPath = null;
        if (os == OS.MACOS && arch == Arch.ARM64) dynPath = "/opencv/osx/silicon/libopencv_java470.dylib";
        else if (os == OS.WINDOWS && arch == Arch.X64) dynPath = "/opencv/win/x64/opencv_java470.dll";
        else if (os == OS.WINDOWS && arch == Arch.X86) dynPath = "/opencv/win/x86/opencv_java470.dll";
        else if (os == OS.LINUX && arch == Arch.X64) dynPath = "/opencv/linux/amd64/libopencv_java470.so";
        else throw new SystemNotSupportedException();

        // Copy the library to the temporary folder
        var inputStream = OpenCV.class.getResourceAsStream(dynPath);
        Files.copy(inputStream, libraryPath);

        // Loading the native library
        System.load(libraryPath.toString());

        // Adding shutdown hook to remove library after exit
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                Files.delete(libraryPath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }));
    }

    public static OS detectOS() {
        if (SystemUtils.IS_OS_LINUX) return OS.LINUX;
        else if (SystemUtils.IS_OS_MAC) return OS.MACOS;
        else if (SystemUtils.IS_OS_WINDOWS) return OS.WINDOWS;
        else throw new UnknownOperatingSystemException();
    }

    public static Arch detectArchitecture() {
        String architecture = System.getProperty("os.arch");
        if (architecture.equals("aarch64") && detectOS() == OS.MACOS) return Arch.ARM64;
        if (architecture.equals("amd64") && detectOS() == OS.WINDOWS) return Arch.X64;
        if (architecture.equals("x86") && detectOS() == OS.WINDOWS) return Arch.X86;
        if (architecture.equals("amd64") && detectOS() == OS.LINUX) return Arch.X64;
        else throw new UnknownCpuArchitectureException();
    }
}

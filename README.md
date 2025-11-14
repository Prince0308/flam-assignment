🔥 Flam – Real-Time Edge Detection Viewer

Android NDK + OpenCV (C++) + Web (OpenCV.js)
This project implements a real-time edge detection viewer for both Android and Web, as required in the Flam R&D Intern assignment.

📌 Project Overview

The system captures camera frames (Android/Web), sends them through a native/JS pipeline, performs Canny edge detection, and displays the processed frames in real-time using OpenGL ES (Android) or Canvas (Web).

📂 Repository Structure

flam-edge-viewer/
│
├── android/
│   └── EdgeViewer/          # Full Android Studio project
│       ├── app/src/main/java/...
│       ├── app/src/main/cpp/ (JNI + Native C++)
│       ├── app/src/main/opengl/ (GLRenderer)
│       └── CMakeLists.txt
│
├── web/
│   └── web-demo/            # Vite + TypeScript + OpenCV.js demo
│       ├── index.html
│       ├── src/main.ts
│       ├── vite.config.js
│       ├── tsconfig.json
│       └── package.json
│
├── screenshots/
│   ├── android_output.png
│   └── web_output.png
│
└── README.md

🚀 Features
✅ Android Native (Kotlin + NDK)
Camera2 API for high-speed frame capture
Converts YUV_420_888 → NV21 (custom converter)
Sends bytes to JNI (native-lib.cpp)
Uses OpenCV C++ (cvtColor, Canny)
Returns processed RGBA data back to Kotlin
Displays on screen with OpenGL ES 2.0 (GLRenderer)
Real-time FPS performance

✅ Web App (OpenCV.js + TypeScript)
Built using Vite + TS
Uses camera (getUserMedia) or file upload
Converts HTML ImageData into OpenCV.Mat

Performs:

RGBA → Grayscale
Canny edge detection
RGBA output rendering
Live canvas preview
Option to download processed output

🛠️ Tech Stack
Android

Kotlin
Camera2 API
OpenGL ES 2.0
Android NDK (C++)
OpenCV 4.x native C++

Web

Vite + TypeScript
OpenCV.js (WASM)
HTML Canvas

📦 Setup Instructions
📱 Android Setup (NDK + OpenCV)
1️⃣ Install NDK

Android Studio → SDK Manager → SDK Tools
✔ Check NDK
✔ Check CMake

2️⃣ Add OpenCV Android SDK

Download: https://opencv.org/releases/

Extract → copy OpenCV-android-sdk/sdk/native folder
Update path inside:

CMakeLists.txt


Example:

include_directories(${CMAKE_SOURCE_DIR}/opencv/native/jni/include)
add_library(opencv_java4 SHARED IMPORTED)
set_target_properties(opencv_java4 PROPERTIES IMPORTED_LOCATION
    ${CMAKE_SOURCE_DIR}/opencv/native/staticlibs/${ANDROID_ABI}/libopencv_java4.so)

3️⃣ Build
Build → Clean Project  
Build → Rebuild Project  


Install on device → camera will show real-time edges.

🌐 Web Setup (Vite + TypeScript)
1️⃣ Install dependencies
cd web/web-demo
npm install

2️⃣ Run dev server
npm run dev


Open:

http://localhost:5173

3️⃣ Features

✔ Open camera
✔ Upload image
✔ Live edge detection
✔ Download processed frame

🔧 Architecture Overview
Android Pipeline
Camera2 → ImageReader → NV21 bytes → JNI (native-lib.cpp)
    → OpenCV C++ (grayscale + Canny)
    → RGBA → Kotlin → OpenGL ES texture → Screen

Web Pipeline
Camera/File → Canvas → ImageData → OpenCV.js
    → cvtColor() + Canny()
    → Canvas output

🖼️ Screenshots
[Watch demo video](.)


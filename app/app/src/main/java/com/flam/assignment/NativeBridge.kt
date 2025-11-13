package com.flam.assignment

object NativeBridge {
    init {
        System.loadLibrary("native-lib")
    }

    external fun initNative(outputBuffer: java.nio.ByteBuffer, width: Int, height: Int): Boolean
    external fun processFrameDirect(
        inputBuffer: java.nio.ByteBuffer,
        width: Int,
        height: Int,
        rotation: Int,
        mode: Int
    ): Boolean

    external fun releaseNative(): Boolean
}

package com.flam.assignment

import android.app.Activity
import android.graphics.SurfaceTexture
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import android.view.TextureView

class CameraController(
    private val activity: Activity,
    private val fpsCallback: (Int) -> Unit
) {
    private val TAG = "CameraController"

    private var bgThread: HandlerThread? = null
    private var bgHandler: Handler? = null

    private var mode = 1

    fun startCamera(textureView: TextureView) {
        startBackgroundThread()

        textureView.surfaceTextureListener =
            object : TextureView.SurfaceTextureListener {
                override fun onSurfaceTextureAvailable(
                    surface: SurfaceTexture,
                    width: Int,
                    height: Int
                ) {
                    Log.d(TAG, "Surface ready (Camera2 init in next commit)")
                }

                override fun onSurfaceTextureSizeChanged(s: SurfaceTexture, w: Int, h: Int) {}
                override fun onSurfaceTextureDestroyed(s: SurfaceTexture) = true
                override fun onSurfaceTextureUpdated(s: SurfaceTexture) {}
            }
    }

    fun stopCamera() {}

    fun release() {
        stopCamera()
        stopBackgroundThread()
    }

    fun setMode(m: Int) {
        mode = m
    }

    private fun startBackgroundThread() {
        bgThread = HandlerThread("CameraBG").apply { start() }
        bgHandler = Handler(bgThread!!.looper)
    }

    private fun stopBackgroundThread() {
        bgThread?.quitSafely()
        bgThread = null
        bgHandler = null
    }
}

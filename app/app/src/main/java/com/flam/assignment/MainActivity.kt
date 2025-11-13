package com.flam.assignment

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.TextureView
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private lateinit var textureView: TextureView
    private lateinit var fpsText: TextView
    private lateinit var toggleButton: Button

    private lateinit var cameraController: CameraController

    private var currentMode = 1

    private val requestCamPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) startCamera()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textureView = findViewById(R.id.texture_view)
        fpsText = findViewById(R.id.fps_text)
        toggleButton = findViewById(R.id.toggle_button)

        cameraController = CameraController(this) { fps ->
            runOnUiThread { fpsText.text = "FPS: $fps" }
        }

        toggleButton.setOnClickListener {
            currentMode = if (currentMode == 0) 1 else 0
            cameraController.setMode(currentMode)
            toggleButton.text =
                if (currentMode == 1) "Mode: PROCESSED" else "Mode: RAW"
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_GRANTED
        ) {
            startCamera()
        } else {
            requestCamPermission.launch(Manifest.permission.CAMERA)
        }
    }

    private fun startCamera() {
        cameraController.startCamera(textureView)
    }

    override fun onPause() {
        super.onPause()
        cameraController.stopCamera()
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraController.release()
    }
}

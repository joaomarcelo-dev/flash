package com.example.flashligth

import android.content.Context
import android.content.pm.PackageManager
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private val buttonFlashLigth: Button by lazy { findViewById<Button>(R.id.button_ligth) }
    private var cameraManager: CameraManager? = null
    private var cameraId: String? = null
    private var isFlashOn = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setup()
    }

    private fun setup() {
        if (hasFlashLigth()) {
            cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
            try {
                cameraId = cameraManager!!.cameraIdList[0]
            } catch (e: CameraAccessException) {
                e.printStackTrace()
            }
        }


        buttonFlashLigth.setOnClickListener(this)
    }

    private fun hasFlashLigth(): Boolean {
        return packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)
    }

    private fun turnOnFlash(newStateFlash: Boolean) {
        cameraManager!!.setTorchMode(cameraId!!, newStateFlash)
        isFlashOn = newStateFlash
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.button_ligth -> {
                if (hasFlashLigth()) {
                    turnOnFlash(!isFlashOn)
                    buttonFlashLigth.text = if (isFlashOn) "Desligar" else "Ligar"
                } else {
                    Toast.makeText(this, "No tiene Flash", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


}
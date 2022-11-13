/*
 * Copyright 2021 Google LLC. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.angdroid.refrigerator_manament.presentation.camera

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.util.Size
import android.view.View
import android.widget.CompoundButton
import android.widget.ImageView
import android.widget.Toast
import android.widget.ToggleButton
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.get
import com.angdroid.refrigerator_manament.R
import com.angdroid.refrigerator_manament.presentation.camera.util.GraphicOverlay
import com.angdroid.refrigerator_manament.presentation.home.HomeActivity
import com.angdroid.refrigerator_manament.presentation.util.dpToPx
import com.angdroid.refrigerator_manament.presentation.util.pxToDp
import com.angdroid.refrigerator_manament.presentation.util.types.FoodConverter
import com.google.android.gms.common.annotation.KeepName
import com.google.android.gms.tasks.Task
import com.google.mlkit.common.model.LocalModel
import com.google.mlkit.vision.camera.CameraSourceConfig
import com.google.mlkit.vision.camera.CameraXSource
import com.google.mlkit.vision.camera.DetectionTaskCallback
import com.google.mlkit.vision.demo.kotlin.objectdetector.ObjectGraphic
import com.google.mlkit.vision.objects.DetectedObject
import com.google.mlkit.vision.objects.ObjectDetection
import com.google.mlkit.vision.objects.ObjectDetector
import com.google.mlkit.vision.objects.custom.CustomObjectDetectorOptions
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.util.*

/** Live preview demo app for ML Kit APIs using CameraXSource API. */
@KeepName
@RequiresApi(VERSION_CODES.LOLLIPOP)
class CameraXSourceDemoActivity : AppCompatActivity(), CompoundButton.OnCheckedChangeListener {
    private var previewView: PreviewView? = null
    private var graphicOverlay: GraphicOverlay? = null
    private var needUpdateGraphicOverlayImageSourceInfo = false
    private var lensFacing: Int = CameraSourceConfig.CAMERA_FACING_BACK
    private var cameraXSource: CameraXSource? = null
    private var customObjectDetectorOptions: CustomObjectDetectorOptions? = null
    private var targetResolution: Size? = null
    private val detectionTaskCallback: DetectionTaskCallback<List<DetectedObject>>? = null
    private var lastClickTime: Long = 0

    private fun allRuntimePermissionsGranted(): Boolean {
        for (permission in REQUIRED_RUNTIME_PERMISSIONS) {
            permission?.let {
                if (!isPermissionGranted(this, it)) {
                    return false
                }
            }
        }
        return true
    }

    private fun getRuntimePermissions() {
        val permissionsToRequest = java.util.ArrayList<String>()
        for (permission in REQUIRED_RUNTIME_PERMISSIONS) {
            permission?.let {
                if (!isPermissionGranted(this, it)) {
                    permissionsToRequest.add(permission)
                }
            }
        }

        if (permissionsToRequest.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                this,
                permissionsToRequest.toTypedArray(),
                PERMISSION_REQUESTS
            )
        }
    }

    private fun isPermissionGranted(context: Context, permission: String): Boolean {
        if (ContextCompat.checkSelfPermission(
                context,
                permission
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            Log.i(TAG, "Permission granted: $permission")
            return true
        }
        Log.i(TAG, "Permission NOT granted: $permission")
        return false
    }

    fun bitmapToByteArray(bitmap: Bitmap): ByteArray {
        var outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        return outputStream.toByteArray()
    }

    private fun bitmapToToByteArray(bitmap: Bitmap): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        bitmap.recycle()
        return stream.toByteArray()
    }

    private fun byteArrayToBitmap(byteArray: ByteArray): Bitmap? {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }

/*    fun completeDetection() {
        val drawable = previewView!!.bitmap
        val data = drawable?.let { bitmapToToByteArray(it) }
        Log.e("BITMAP!!!", drawable.toString())
        var intent = Intent(this, HomeActivity::class.java)
        intent.putExtra("img", data)
        startActivity(intent)
        finish()
        Log.d(TAG, "completeDectection")
    }*/

    init {
        instance = this
    }

    companion object {
        private var instance: CameraXSourceDemoActivity? = null

        fun getInstance(): CameraXSourceDemoActivity? {
            return instance
        }

        private const val TAG = "EntryChoiceActivity"
        private const val PERMISSION_REQUESTS = 1
        private val localModel: LocalModel =
            LocalModel.Builder().setAssetFilePath("custom_models/converted_model.tflite").build()
        private val REQUIRED_RUNTIME_PERMISSIONS =
            arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
    }
/*
override fun getRuntimePermissions() {
  val permissionsToRequest = ArrayList<String>()
  for (permission in REQUIRED_RUNTIME_PERMISSIONS) {
    permission?.let {
      if (!isPermissionGranted(this, it)) {
        permissionsToRequest.add(permission)
      }
    }
  }

 */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vision_cameraxsource_demo)

        //카메라 권한 체크 및 요청
        if (!allRuntimePermissionsGranted()) {
            getRuntimePermissions()
        }
        previewView = findViewById(R.id.preview_view)
        if (previewView == null) {
            Log.d(TAG, "previewView is null")
        }
        graphicOverlay = findViewById(R.id.graphic_overlay)
        if (graphicOverlay == null) {
        }
        val facingSwitch = findViewById<ToggleButton>(R.id.facing_switch)
        facingSwitch.setOnCheckedChangeListener(this)
        val settingsButton = findViewById<ImageView>(R.id.settings_button)
        settingsButton.setOnClickListener {
            val intent = Intent(applicationContext, SettingsActivity::class.java)
            intent.putExtra(
                SettingsActivity.EXTRA_LAUNCH_SOURCE,
                SettingsActivity.LaunchSource.CAMERAXSOURCE_DEMO
            )
            startActivity(intent)
        }
        //import com.google.mlkit.vision.camera.DetectionTaskCallback
        DetectionTaskCallback { detectionTask: Task<List<DetectedObject?>?> ->
            detectionTask
                .addOnSuccessListener { results: List<DetectedObject?>? ->
                    onDetectionTaskSuccess(
                        results
                    )
                }
                .addOnFailureListener { e: java.lang.Exception? ->
                    onDetectionTaskFailure(
                        e
                    )
                }
        }
    }

    @JvmName("onDetectionTaskSuccess1")
    private fun onDetectionTaskSuccess(results: List<DetectedObject?>?) {
        TODO("Not yet implemented")
    }

    @JvmName("onDetectionTaskFailure1")
    private fun onDetectionTaskFailure(e: java.lang.Exception?) {
        TODO("Not yet implemented")
    }

    override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
        if (lensFacing == CameraSourceConfig.CAMERA_FACING_FRONT) {
            lensFacing = CameraSourceConfig.CAMERA_FACING_BACK
        } else {
            lensFacing = CameraSourceConfig.CAMERA_FACING_FRONT
        }
        createThenStartCameraXSource()
    }

    @Override
    override fun onResume() {
        super.onResume()
        if (cameraXSource != null &&
            PreferenceUtils.getCustomObjectDetectorOptionsForLivePreview(this, localModel)
                .equals(customObjectDetectorOptions) &&
            PreferenceUtils.getCameraXTargetResolution(
                getApplicationContext(),
                lensFacing
            ) != null &&
            (Objects.requireNonNull(
                PreferenceUtils.getCameraXTargetResolution(getApplicationContext(), lensFacing)
            ) == targetResolution)
        ) {
            cameraXSource!!.start()
        } else {
            createThenStartCameraXSource()
        }
    }

    override fun onPause() {
        super.onPause()
        if (cameraXSource != null) {
            cameraXSource!!.stop()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (cameraXSource != null) {
            cameraXSource!!.stop()
        }
    }


    private fun createThenStartCameraXSource() {
        if (cameraXSource != null) {
            cameraXSource!!.close()
        }
        customObjectDetectorOptions =
            PreferenceUtils.getCustomObjectDetectorOptionsForLivePreview(
                getApplicationContext(),
                localModel
            )
        val objectDetector: ObjectDetector =
            ObjectDetection.getClient(customObjectDetectorOptions!!)
        var detectionTaskCallback: DetectionTaskCallback<List<DetectedObject>> =
            DetectionTaskCallback<List<DetectedObject>> { detectionTask ->
                detectionTask
                    .addOnSuccessListener { results -> onDetectionTaskSuccess(results) }
                    .addOnFailureListener { e -> onDetectionTaskFailure(e) }
            }
        val builder: CameraSourceConfig.Builder =
            CameraSourceConfig.Builder(
                getApplicationContext(),
                objectDetector!!,
                detectionTaskCallback
            )
                .setFacing(lensFacing)
        targetResolution =
            PreferenceUtils.getCameraXTargetResolution(getApplicationContext(), lensFacing)
        if (targetResolution != null) {
            builder.setRequestedPreviewSize(targetResolution!!.width, targetResolution!!.height)
        }
        cameraXSource = CameraXSource(builder.build(), previewView!!)
        needUpdateGraphicOverlayImageSourceInfo = true
        cameraXSource!!.start()
    }

    private fun onDetectionTaskSuccess(results: List<DetectedObject>) {
        graphicOverlay!!.clear()
        if (needUpdateGraphicOverlayImageSourceInfo) {
            val size: Size = cameraXSource!!.getPreviewSize()!!
            if (size != null) {
                val isImageFlipped =
                    cameraXSource!!.getCameraFacing() == CameraSourceConfig.CAMERA_FACING_FRONT
                if (isPortraitMode()) {
                    // Swap width and height sizes when in portrait, since it will be rotated by
                    // 90 degrees. The camera preview and the image being processed have the same size.
                    graphicOverlay!!.setImageSourceInfo(size.height, size.width, isImageFlipped)
                } else {
                    graphicOverlay!!.setImageSourceInfo(size.width, size.height, isImageFlipped)
                }
                needUpdateGraphicOverlayImageSourceInfo = false
            } else {
            }
        }
        for (obj in results) {
            graphicOverlay!!.add(ObjectGraphic(graphicOverlay!!, obj))
            obj.labels.forEach {
                if (it.confidence * 100 > 90.0f) { // 정확도 90퍼 이상일 경우 페이지 이동
                    //Log.d(ContentValues.TAG, "신뢰도 : " + (it.confidence * 100).toString())
                    //completeDetection()

                    previewView?.let { it1 ->
                        viewToBitmap(
                            previewView?.bitmap!!,
                            obj.boundingBox.left, obj.boundingBox.top,
                            obj.boundingBox.width().dpToPx(this),
                            obj.boundingBox.height().dpToPx(this)
                        ).let { bitmap ->
                            val elapsedRealtime = SystemClock.elapsedRealtime()
                            if ((elapsedRealtime - lastClickTime) < 1000) {
                                return
                            }
                            lastClickTime = elapsedRealtime
                            var intent = Intent(this, AddIngredientActivity::class.java)
                            intent.putExtra("img", saveBitmapToJpeg(bitmap))
                            intent.putExtra("label", FoodConverter.valueOf(obj.labels[0].text).label)
                            startActivity(intent)
                            finish()
                        }
                    }
                }
            }
        }


        graphicOverlay!!.add(InferenceInfoGraphic(graphicOverlay!!))
        graphicOverlay!!.postInvalidate()
    }

    fun viewToBitmap(bitmap: Bitmap, left: Int, top: Int, width: Int, height: Int): Bitmap {
        val bitmap = Bitmap.createBitmap(bitmap, 0 + left, 0 + top, width, height)
        return bitmap
    }


    private fun saveBitmapToJpeg(bitmap: Bitmap): String {
        try {
            val path = UUID.randomUUID().toString()
            val tempFile = File(cacheDir, path) // 파일 경로와 이름 넣기
            tempFile.createNewFile() // 자동으로 빈 파일을 생성하기
            val out = FileOutputStream(tempFile) // 파일을 쓸 수 있는 스트림을 준비하기
            bitmap.compress(
                Bitmap.CompressFormat.JPEG,
                80,
                out
            ) // compress 함수를 사용해 스트림에 비트맵을 저장하기 압축률은 80퍼센트 정도만,, 100퍼센트는 좀 클 것 같으니
            if (!bitmap.isRecycled) bitmap.recycle()
            out.close() // 스트림 닫아주기
            return path
        } catch (e: Exception) {
            return ""
        }
    }

    private fun isPortraitMode(): Boolean {
        return (applicationContext.resources.configuration.orientation
                != Configuration.ORIENTATION_LANDSCAPE)
    }


    private fun onDetectionTaskFailure(e: Exception) {
        graphicOverlay!!.clear()
        graphicOverlay!!.postInvalidate()
        val error = "Failed to process. Error: " + e.localizedMessage
        Toast.makeText(
            graphicOverlay!!.context, """
   $error
   Cause: ${e.cause}
   """.trimIndent(), Toast.LENGTH_SHORT
        )
            .show()
        Log.d(TAG, error)
    }
}


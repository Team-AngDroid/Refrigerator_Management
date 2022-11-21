package com.angdroid.refrigerator_manament.presentation.camera

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.graphics.Bitmap
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.util.Size
import android.widget.CompoundButton
import android.widget.Toast
import android.widget.ToggleButton
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.angdroid.refrigerator_manament.BuildConfig
import com.angdroid.refrigerator_manament.R
import com.angdroid.refrigerator_manament.presentation.camera.util.GraphicOverlay
import com.angdroid.refrigerator_manament.presentation.util.dpToPx
import com.angdroid.refrigerator_manament.presentation.util.types.FoodConverter
import com.google.android.gms.common.annotation.KeepName
import com.google.mlkit.common.model.LocalModel
import com.google.mlkit.vision.camera.CameraSourceConfig
import com.google.mlkit.vision.camera.CameraXSource
import com.google.mlkit.vision.camera.DetectionTaskCallback
import com.google.mlkit.vision.demo.kotlin.objectdetector.ObjectGraphic
import com.google.mlkit.vision.objects.DetectedObject
import com.google.mlkit.vision.objects.ObjectDetection
import com.google.mlkit.vision.objects.ObjectDetector
import com.google.mlkit.vision.objects.custom.CustomObjectDetectorOptions
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
    private var button: AppCompatButton? = null
    private var targetResolution: Size? = null
    private var tempBitmap: Bitmap? = null
    private var tempLabel: String? = null
    private val detectionTaskCallback: DetectionTaskCallback<List<DetectedObject>> by lazy {
        DetectionTaskCallback<List<DetectedObject>> { detectionTask ->
            detectionTask
                .addOnSuccessListener { results -> onDetectionTaskSuccess(results) }
                .addOnFailureListener { e -> onDetectionTaskFailure(e) }
        }
    }
    private var lastClickTime: Long = 0

    private fun allRuntimePermissionsGranted(): Boolean {
        for (permission in REQUIRED_RUNTIME_PERMISSIONS) {
            if (!isPermissionGranted(this, permission)) {
                return false
            }
        }
        return true
    }

    private fun getRuntimePermissions() {
        val permissionsToRequest = ArrayList<String>()
        for (permission in REQUIRED_RUNTIME_PERMISSIONS) {
            if (!isPermissionGranted(this, permission)) {
                permissionsToRequest.add(permission)
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vision_cameraxsource_demo)
        //카메라 권한 체크 및 요청
        if (!allRuntimePermissionsGranted()) {
            getRuntimePermissions()
        }
        previewView = findViewById(R.id.preview_view)
        graphicOverlay = findViewById(R.id.graphic_overlay)
        val facingSwitch = findViewById<ToggleButton>(R.id.facing_switch)
        facingSwitch.setOnCheckedChangeListener(this)
        button = findViewById(R.id.detector)
        button?.setOnClickListener {
            val intent = Intent(this, AddIngredientActivity::class.java)
            intent.putExtra("img", tempBitmap?.let { it1 -> saveBitmapToJpeg(it1) })
            intent.putExtra(
                "label",
                tempLabel?.let { it1 -> FoodConverter.valueOf(it1).label }
            )
            startActivity(intent)
            finish()

        }
    }

    override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
        if (lensFacing == CameraSourceConfig.CAMERA_FACING_FRONT) {
            lensFacing = CameraSourceConfig.CAMERA_FACING_BACK
        } else {
            lensFacing = CameraSourceConfig.CAMERA_FACING_FRONT
        }
        createThenStartCameraXSource()
    }

    private fun createThenStartCameraXSource() {
        cameraXSource?.close()
        customObjectDetectorOptions =
            PreferenceUtils.getCustomObjectDetectorOptionsForLivePreview(
                localModel
            )
        val objectDetector: ObjectDetector =
            ObjectDetection.getClient(customObjectDetectorOptions!!)

        val builder: CameraSourceConfig.Builder =
            CameraSourceConfig.Builder(
                applicationContext,
                objectDetector,
                detectionTaskCallback
            )
                .setFacing(lensFacing)
        targetResolution =
            PreferenceUtils.getCameraXTargetResolution(applicationContext, lensFacing)
        if (targetResolution != null) {
            builder.setRequestedPreviewSize(targetResolution!!.width, targetResolution!!.height)
        }
        cameraXSource = CameraXSource(builder.build(), previewView!!)
        needUpdateGraphicOverlayImageSourceInfo = true
        cameraXSource?.start()
    }

    private fun onDetectionTaskSuccess(results: List<DetectedObject>) {
        graphicOverlay?.clear()
        if (needUpdateGraphicOverlayImageSourceInfo) {
            val size: Size = cameraXSource?.previewSize!!
            val isImageFlipped =
                cameraXSource?.cameraFacing == CameraSourceConfig.CAMERA_FACING_FRONT
            if (isPortraitMode()) {
                // Swap width and height sizes when in portrait, since it will be rotated by
                // 90 degrees. The camera preview and the image being processed have the same size.
                graphicOverlay?.setImageSourceInfo(size.height, size.width, isImageFlipped)
            } else {
                graphicOverlay?.setImageSourceInfo(size.width, size.height, isImageFlipped)
            }
            needUpdateGraphicOverlayImageSourceInfo = false
        }
        for (obj in results) {
            if (obj.labels[0].confidence * 100 >= 88.0f) {

                graphicOverlay?.add(ObjectGraphic(graphicOverlay!!, obj))
                obj.labels.forEach {
                    if (it.confidence * 100 > 90.0f) { // 정확도 90퍼 이상일 경우 페이지 이동
                        val elapsedRealtime = SystemClock.elapsedRealtime()
                        if ((elapsedRealtime - lastClickTime) < 1000) {
                            return@forEach
                        }

                        previewView?.let {
                            tempBitmap = viewToBitmap(
                                previewView?.bitmap!!,
                                obj.boundingBox.left, obj.boundingBox.top,
                                obj.boundingBox.width().dpToPx(this),
                                obj.boundingBox.height().dpToPx(this)
                            )
                            tempLabel = obj.labels[0].text
                            button?.isEnabled = true
                            lastClickTime = elapsedRealtime
                        }
                    } else {
                        button?.isEnabled = false
                        tempBitmap = null
                        tempLabel = null
                    }
                }

            } else {
                button?.isEnabled = false
            }
        }
        graphicOverlay?.add(InferenceInfoGraphic(graphicOverlay!!))
        graphicOverlay?.postInvalidate()
    }

    private fun viewToBitmap(bitmap: Bitmap, left: Int, top: Int, width: Int, height: Int): Bitmap {
        val statusBar = getStatusBarHeight(this)
        val returnBitmap =
            if (left + width >= bitmap.width && top + height + statusBar >= bitmap.height) {
                Log.e("Each!!!", "Each!!!")
                Bitmap.createBitmap(
                    bitmap,
                    0 + left,
                    0 + top + statusBar,
                    bitmap.width - left,
                    bitmap.height - (top + statusBar)
                )
            } else if (top + height + statusBar >= bitmap.height) {
                Log.e("Height!!!!", "Height!!!!")
                Bitmap.createBitmap(
                    bitmap,
                    0 + left,
                    0 + top + statusBar,
                    width,
                    bitmap.height - (top + statusBar)
                )
            } else if (left + width >= bitmap.width) {
                Log.e("Width!!!!", "Width!!!!")
                Bitmap.createBitmap(
                    bitmap,
                    0 + left,
                    0 + top + statusBar,
                    bitmap.width - left,
                    height
                )
            } else {
                Log.e("Nothing!!!!", "Nothing!!!!")
                Bitmap.createBitmap(bitmap, 0 + left, 0 + top + statusBar, width, height)
            }
        return returnBitmap
    }

    private fun getStatusBarHeight(context: Context): Int {
        val screenSizeType = context.resources.configuration.screenLayout and
                Configuration.SCREENLAYOUT_SIZE_MASK
        var statusbar = 0
        if (screenSizeType != Configuration.SCREENLAYOUT_SIZE_XLARGE) {
            val resourceId =
                context.resources.getIdentifier("status_bar_height", "dimen", "android")
            if (resourceId > 0) {
                statusbar = context.resources.getDimensionPixelSize(resourceId)
            }
        }
        return statusbar
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

    @Override
    override fun onResume() {
        super.onResume()
        if (cameraXSource != null &&
            PreferenceUtils.getCustomObjectDetectorOptionsForLivePreview(localModel) == customObjectDetectorOptions &&
            PreferenceUtils.getCameraXTargetResolution(
                applicationContext,
                lensFacing
            ) != null &&
            (Objects.requireNonNull(
                PreferenceUtils.getCameraXTargetResolution(applicationContext, lensFacing)
            ) == targetResolution)
        ) {
            cameraXSource?.start()
        } else {
            createThenStartCameraXSource()
        }
    }

    override fun onPause() {
        super.onPause()
        cameraXSource?.stop()
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraXSource?.stop()
    }

    private fun isPortraitMode(): Boolean {
        return (applicationContext.resources.configuration.orientation
                != Configuration.ORIENTATION_LANDSCAPE)
    }


    private fun onDetectionTaskFailure(e: Exception) {
        graphicOverlay?.clear()
        graphicOverlay?.postInvalidate()
        val error = "Failed to process. Error: " + e.localizedMessage
        Toast.makeText(
            graphicOverlay?.context, """
   $error
   Cause: ${e.cause}
   """.trimIndent(), Toast.LENGTH_SHORT
        )
            .show()
        Log.d(TAG, error)
    }

    companion object {
        private const val TAG = "EntryChoiceActivity"
        private const val PERMISSION_REQUESTS = 1
        private val localModel: LocalModel =
            LocalModel.Builder().setAssetFilePath(BuildConfig.CUSTOM_MODEL).build()
        private val REQUIRED_RUNTIME_PERMISSIONS =
            arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
    }
}


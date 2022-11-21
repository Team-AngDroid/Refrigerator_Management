package com.angdroid.refrigerator_manament.presentation.camera

import android.content.Context
import android.os.Build.VERSION_CODES
import android.preference.PreferenceManager
import android.util.Size
import androidx.annotation.RequiresApi
import androidx.camera.core.CameraSelector
import com.angdroid.refrigerator_manament.R
import com.google.common.base.Preconditions
import com.google.mlkit.common.model.LocalModel
import com.google.mlkit.vision.objects.ObjectDetectorOptionsBase
import com.google.mlkit.vision.objects.custom.CustomObjectDetectorOptions

/** Utility class to retrieve shared preferences.  */
object PreferenceUtils {

    @RequiresApi(VERSION_CODES.LOLLIPOP)
    fun getCameraXTargetResolution(context: Context, lensfacing: Int): Size? {
        Preconditions.checkArgument((lensfacing == CameraSelector.LENS_FACING_BACK || lensfacing == CameraSelector.LENS_FACING_FRONT))
        val prefKey =
            if (lensfacing == CameraSelector.LENS_FACING_BACK) context.getString(R.string.pref_key_camerax_rear_camera_target_resolution) else context.getString(
                R.string.pref_key_camerax_front_camera_target_resolution
            )
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        return try {
            Size.parseSize(sharedPreferences.getString(prefKey, null))
        } catch (e: Exception) {
            null
        }
    }
    fun getCustomObjectDetectorOptionsForLivePreview(
        localModel: LocalModel
    ): CustomObjectDetectorOptions {
        return CustomObjectDetectorOptions.Builder(localModel).setDetectorMode(CustomObjectDetectorOptions.STREAM_MODE).enableClassification().setMaxPerObjectLabelCount(1).build()
    }
}
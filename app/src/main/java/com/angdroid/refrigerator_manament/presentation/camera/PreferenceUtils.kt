package com.angdroid.refrigerator_manament.presentation.camera

import android.content.Context
import android.os.Build.VERSION_CODES
import android.preference.PreferenceManager
import android.util.Size
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.camera.core.CameraSelector
import com.google.common.base.Preconditions
import com.google.mlkit.common.model.LocalModel
import com.google.mlkit.vision.objects.ObjectDetectorOptionsBase
import com.google.mlkit.vision.objects.custom.CustomObjectDetectorOptions
import com.google.mlkit.vision.objects.defaults.ObjectDetectorOptions
import com.angdroid.refrigerator_manament.R

/** Utility class to retrieve shared preferences.  */
object PreferenceUtils {
    private val POSE_DETECTOR_PERFORMANCE_MODE_FAST = 1
    fun saveString(context: Context, @StringRes prefKeyId: Int, value: String) {
        PreferenceManager.getDefaultSharedPreferences(context)
            .edit()
            .putString(context.getString(prefKeyId), value)
            .apply()
    }

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

    fun shouldHideDetectionInfo(context: Context): Boolean {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val prefKey = context.getString(R.string.pref_key_info_hide)
        return sharedPreferences.getBoolean(prefKey, false)
    }

    fun getObjectDetectorOptionsForStillImage(context: Context): ObjectDetectorOptions {
        return getObjectDetectorOptions(
            context,
            R.string.pref_key_still_image_object_detector_enable_multiple_objects,
            R.string.pref_key_still_image_object_detector_enable_classification,
            ObjectDetectorOptions.SINGLE_IMAGE_MODE
        )
    }

    fun getObjectDetectorOptionsForLivePreview(context: Context): ObjectDetectorOptions {
        return getObjectDetectorOptions(
            context,
            R.string.pref_key_live_preview_object_detector_enable_multiple_objects,
            R.string.pref_key_live_preview_object_detector_enable_classification,
            ObjectDetectorOptions.STREAM_MODE
        )
    }

    private fun getObjectDetectorOptions(
        context: Context,
        @StringRes prefKeyForMultipleObjects: Int,
        @StringRes prefKeyForClassification: Int,
        @ObjectDetectorOptionsBase.DetectorMode mode: Int
    ): ObjectDetectorOptions {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val enableMultipleObjects =
            sharedPreferences.getBoolean(context.getString(prefKeyForMultipleObjects), false)
        val enableClassification =
            sharedPreferences.getBoolean(context.getString(prefKeyForClassification), true)
        val builder = ObjectDetectorOptions.Builder().setDetectorMode(mode)
        if (enableMultipleObjects) {
            builder.enableMultipleObjects()
        }
        if (enableClassification) {
            builder.enableClassification()
        }
        return builder.build()
    }

    fun getCustomObjectDetectorOptionsForStillImage(
        context: Context, localModel: LocalModel
    ): CustomObjectDetectorOptions {
        return getCustomObjectDetectorOptions(
            context,
            localModel,
            R.string.pref_key_still_image_object_detector_enable_multiple_objects,
            R.string.pref_key_still_image_object_detector_enable_classification,
            CustomObjectDetectorOptions.SINGLE_IMAGE_MODE
        )
    }

    fun getCustomObjectDetectorOptionsForLivePreview(
        context: Context, localModel: LocalModel
    ): CustomObjectDetectorOptions {
        return getCustomObjectDetectorOptions(
            context,
            localModel,
            R.string.pref_key_live_preview_object_detector_enable_multiple_objects,
            R.string.pref_key_live_preview_object_detector_enable_classification,
            CustomObjectDetectorOptions.STREAM_MODE
        )
    }

    private fun getCustomObjectDetectorOptions(
        context: Context,
        localModel: LocalModel,
        @StringRes prefKeyForMultipleObjects: Int,
        @StringRes prefKeyForClassification: Int,
        @ObjectDetectorOptionsBase.DetectorMode mode: Int
    ): CustomObjectDetectorOptions {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val enableMultipleObjects =
            sharedPreferences.getBoolean(context.getString(prefKeyForMultipleObjects), false)
        val enableClassification =
            sharedPreferences.getBoolean(context.getString(prefKeyForClassification), true)
        val builder = CustomObjectDetectorOptions.Builder(localModel).setDetectorMode(mode)
        if (enableMultipleObjects) {
            builder.enableMultipleObjects()
        }
        if (enableClassification) {
            builder.enableClassification().setMaxPerObjectLabelCount(1)
        }
        return builder.build()
    }

    fun shouldGroupRecognizedTextInBlocks(context: Context): Boolean {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val prefKey = context.getString(R.string.pref_key_group_recognized_text_in_blocks)
        return sharedPreferences.getBoolean(prefKey, false)
    }

    fun showLanguageTag(context: Context): Boolean {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val prefKey = context.getString(R.string.pref_key_show_language_tag)
        return sharedPreferences.getBoolean(prefKey, false)
    }

    fun shouldShowTextConfidence(context: Context): Boolean {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val prefKey = context.getString(R.string.pref_key_show_text_confidence)
        return sharedPreferences.getBoolean(prefKey, false)
    }

    fun preferGPUForPoseDetection(context: Context): Boolean {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val prefKey = context.getString(R.string.pref_key_pose_detector_prefer_gpu)
        return sharedPreferences.getBoolean(prefKey, true)
    }

    fun shouldShowPoseDetectionInFrameLikelihoodLivePreview(context: Context): Boolean {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val prefKey =
            context.getString(R.string.pref_key_live_preview_pose_detector_show_in_frame_likelihood)
        return sharedPreferences.getBoolean(prefKey, true)
    }

    fun shouldShowPoseDetectionInFrameLikelihoodStillImage(context: Context): Boolean {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val prefKey =
            context.getString(R.string.pref_key_still_image_pose_detector_show_in_frame_likelihood)
        return sharedPreferences.getBoolean(prefKey, true)
    }

    fun shouldPoseDetectionVisualizeZ(context: Context): Boolean {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val prefKey = context.getString(R.string.pref_key_pose_detector_visualize_z)
        return sharedPreferences.getBoolean(prefKey, true)
    }

    fun shouldPoseDetectionRescaleZForVisualization(context: Context): Boolean {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val prefKey = context.getString(R.string.pref_key_pose_detector_rescale_z)
        return sharedPreferences.getBoolean(prefKey, true)
    }

    fun shouldPoseDetectionRunClassification(context: Context): Boolean {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val prefKey = context.getString(R.string.pref_key_pose_detector_run_classification)
        return sharedPreferences.getBoolean(prefKey, false)
    }

    fun shouldSegmentationEnableRawSizeMask(context: Context): Boolean {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val prefKey = context.getString(R.string.pref_key_segmentation_raw_size_mask)
        return sharedPreferences.getBoolean(prefKey, false)
    }

    /**
     * Mode type preference is backed by [android.preference.ListPreference] which only support
     * storing its entry value as string type, so we need to retrieve as string and then convert to
     * integer.
     */
    private fun getModeTypePreferenceValue(
        context: Context, @StringRes prefKeyResId: Int, defaultValue: Int
    ): Int {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val prefKey = context.getString(prefKeyResId)
        return sharedPreferences.getString(prefKey, defaultValue.toString())!!.toInt()
    }

    fun isCameraLiveViewportEnabled(context: Context): Boolean {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val prefKey = context.getString(R.string.pref_key_camera_live_viewport)
        return sharedPreferences.getBoolean(prefKey, false)
    }
}
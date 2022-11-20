package com.angdroid.refrigerator_manament.presentation.camera

import android.graphics.Bitmap
import android.graphics.Canvas
import com.angdroid.refrigerator_manament.presentation.camera.util.GraphicOverlay
import com.angdroid.refrigerator_manament.presentation.camera.util.GraphicOverlay.Graphic

/** Draw camera image to background.  */
class CameraImageGraphic(overlay: GraphicOverlay, private val bitmap: Bitmap) :
    Graphic(overlay) {
    override fun draw(canvas: Canvas) {
        canvas.drawBitmap(bitmap, getTransformationMatrix(), null)
    }
}
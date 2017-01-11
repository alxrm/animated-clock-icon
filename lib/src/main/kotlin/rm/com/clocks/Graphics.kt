package rm.com.clocks

import android.animation.ValueAnimator
import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.view.View

/**
 * Created by alex
 */
internal fun Resources.dip(value: Int): Int = (value * displayMetrics.density).toInt()

internal fun Context.dip(value: Int): Int = resources.dip(value)
internal fun View.dip(value: Int): Int = resources.dip(value)

internal fun paintOf(paintColor: Int = Color.WHITE): Paint =
    Paint().apply {
      isAntiAlias = true
      color = paintColor
    }

internal fun linePaintOf(paintColor: Int = Color.WHITE): Paint =
    paintOf(paintColor).apply {
      style = Paint.Style.STROKE
      strokeCap = Paint.Cap.ROUND
      strokeJoin = Paint.Join.ROUND
    }

inline internal fun animatorOf(init: ValueAnimator.() -> Unit) =
    ValueAnimator.ofFloat(0F, 1F).apply(init)

internal fun Canvas.drawLineWithAngle(fromX: Float, fromY: Float, angleDegrees: Float,
    length: Float, paint: Paint) {
  val endX = angleDegrees.endXOf(fromX, length)
  val endY = angleDegrees.endYOf(fromY, length)

  drawLine(fromX, fromY, endX, endY, paint)
}

internal val Drawable.width: Int get() = bounds.width()
internal val Drawable.centerX: Float get() = bounds.exactCenterX()
internal val Drawable.centerY: Float get() = bounds.exactCenterY()

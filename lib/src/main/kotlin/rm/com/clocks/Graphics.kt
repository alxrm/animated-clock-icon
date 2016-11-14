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

internal fun smoothPaint(paintColor: Int = Color.WHITE): Paint =
    Paint().apply {
      isAntiAlias = true
      color = paintColor
    }

internal fun smoothLinePaint(paintColor: Int = Color.WHITE): Paint =
    smoothPaint(paintColor).apply {
      style = Paint.Style.STROKE
      strokeCap = Paint.Cap.ROUND
      strokeJoin = Paint.Join.ROUND
    }

inline internal fun animatorOf(init: ValueAnimator.() -> Unit) =
    ValueAnimator.ofFloat(0F, 1F).apply(init)

internal fun Canvas.drawLineWithAngle(fromX: Float, fromY: Float, angleDegrees: Float, length: Float, paint: Paint) {
  val (endX, endY) = angleDegrees.endsOf(fromX, fromY, length)

  drawLine(fromX, fromY, endX, endY, paint)
}

internal fun Int.withAlpha(alpha: Int): Int {
  require(alpha in 0x00..0xFF)
  return this and 0x00FFFFFF or (alpha shl 24)
}

internal val Drawable.width: Int get() = bounds.width()
internal val Drawable.centerX: Float get() = bounds.exactCenterX()
internal val Drawable.centerY: Float get() = bounds.exactCenterY()

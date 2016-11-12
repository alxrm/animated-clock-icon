package rm.com.clocks

import android.content.Context
import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.drawable.Animatable
import android.graphics.drawable.Drawable

/**
 * Created by alex
 */
class ClockDrawable(ctx: Context) : Drawable(), Animatable {

  /**
   * defines whether this should draw a ring around the pointers
   */
  var hasFrame = true

  var hours = 0
    set(value) {
      field = value.cycledClamp(to = 12)
      invalidateSelf()
    }

  var minutes = 0
    set(value) {
      field = value.cycledClamp(to = 60)
      invalidateSelf()
    }

  private val frame = smoothLinePaint().apply { strokeWidth = ctx.dip(1).toFloat() }
  private val pointers = smoothLinePaint().apply { strokeWidth = ctx.dip(1).toFloat() }

  private val hoursAngle: Float
    get() = 30F * hours.toFloat() + minutes.toFloat() / 2F - 90

  private val minutesAngle: Float
    get() = 6F * minutes.toFloat() - 90

  private val centerX: Float get() = bounds.exactCenterX()
  private val centerY: Float get() = bounds.exactCenterY()

  private val width: Float get() = bounds.width().toFloat()
  private val height: Float get() = bounds.height().toFloat()

  private val frameRadius: Float get() = width / 2 - frame.strokeWidth
  private val minutesRadius: Float get() = frameRadius - frame.strokeWidth * 3
  private val hoursRadius: Float get() = frameRadius / 2

  private var absMinutes: Float = 0F
    set(value) {
      field = value

      minutes = (field % 60).toInt()
      hours = field.toInt().floorDiv(60)
    }

  override fun draw(canvas: Canvas?) {
    canvas ?: return

    if (hasFrame) {
      canvas.drawCircle(centerX, centerY, frameRadius, frame)
    }

    canvas.drawLineWithAngle(centerX, centerY, minutesAngle, minutesRadius, pointers)
    canvas.drawLineWithAngle(centerX, centerY, hoursAngle, hoursRadius, pointers)
  }

  override fun setAlpha(alpha: Int) {
    frame.alpha = alpha
    pointers.alpha = alpha
  }

  override fun getOpacity(): Int = pointers.alpha

  override fun setColorFilter(filter: ColorFilter?) {
    frame.colorFilter = filter
    pointers.colorFilter = filter
  }

  override fun isRunning(): Boolean {
    TODO()
  }

  override fun start() {
    TODO()
  }

  override fun stop() {
    TODO()
  }

}
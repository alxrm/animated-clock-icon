package rm.com.timecon

import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.drawable.Animatable
import android.graphics.drawable.Drawable

/**
 * Created by alex
 */
class ClockDrawable(res: Resources) : Drawable(), Animatable {

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

  private val frame = smoothLinePaint()
  private val pointers = smoothLinePaint()

  private val hoursAngle: Float
    get() = 30F * hours.toFloat() + minutes.toFloat() / 2F

  private val minutesAngle: Float
    get() = 6F * minutes.toFloat()

  private val centerX: Float get() = bounds.exactCenterX()

  private val centerY: Float get() = bounds.exactCenterY()

  /**
   * you can increment & decrement this however you want
   * you can even make this negative, it'll still gonna show you the right result
   *
   * it's here for indeterminate animation
   */
  private var absMinutes: Int = 0
    set(value) {
      minutes = field % 60
      hours = field.floorDiv(60)
    }

  override fun draw(canvas: Canvas?) {
    canvas ?: return
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
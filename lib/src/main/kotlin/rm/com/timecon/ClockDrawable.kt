package rm.com.timecon

import android.content.Context
import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.drawable.Animatable
import android.graphics.drawable.Drawable

/**
 * Created by alex
 */
class ClockDrawable(ctx: Context) : Drawable(), Animatable {

  val frame = smoothLinePaint()
  val pointers = smoothLinePaint()

  var hasFrame = true

  var absMinutes: Int = 0
    set(value) {
      minutes = field % 60
      hours = Math.round(field.toFloat() / 60F)
    }

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

  private val hoursAngle: Float
    get() = 30F * hours.toFloat() + minutes.toFloat() / 2F

  private val minutesAngle: Float
    get() = 6F * minutes.toFloat()

  override fun draw(canvas: Canvas?) {
    TODO()
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
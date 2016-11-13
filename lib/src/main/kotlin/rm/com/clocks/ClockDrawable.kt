package rm.com.clocks

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.drawable.Animatable
import android.graphics.drawable.Drawable
import android.view.animation.OvershootInterpolator

/**
 * Created by alex
 */

enum class Stroke { THIN, LIGHT, REGULAR, BOLD }

class ClockDrawable(private val ctx: Context) : Drawable(), Animatable {

  /**
   * defines whether this should draw a ring around the pointers
   */
  var hasFrame = true

  private var _minutes = 0
  private var _hours = 0

  var minutes: Int
    set(value) {
      _minutes = value.cycledClamp(to = 60)

      minutesAngle = getMinutesAngle(_minutes)

      invalidateSelf()
    }
    get() = _minutes

  var hours: Int
    set(value) {
      _hours = value.cycledClamp(to = 12)

      hoursAngle = getHoursAngle(_hours, minutes)

      invalidateSelf()
    }
    get() = _hours

  var clockColor = Color.WHITE
    set(value) {
      field = value

      frame.color = field
      pointers.color = field

      invalidateSelf()
    }

  var frameStroke = Stroke.LIGHT
    set(value) {
      field = value
      frame.strokeWidth = field.toDip()
      invalidateSelf()
    }

  var pointerStroke = Stroke.LIGHT
    set(value) {
      field = value
      pointers.strokeWidth = field.toDip()
      invalidateSelf()
    }

  var indeterminateSpeed = 1F

  private val frame = smoothLinePaint().apply {
    strokeWidth = frameStroke.toDip()
    color = clockColor
  }

  private val pointers = smoothLinePaint().apply {
    strokeWidth = pointerStroke.toDip()
    color = clockColor
  }

  private val centerX: Float get() = bounds.exactCenterX()
  private val centerY: Float get() = bounds.exactCenterY()

  private val width: Float get() = bounds.width().toFloat()

  private val frameRadius: Float get() = width / 2 - frame.strokeWidth
  private val minutesRadius: Float get() = frameRadius * .8F
  private val hoursRadius: Float get() = frameRadius * .5F

  private var minutesAngle = getMinutesAngle()
  private var hoursAngle = getHoursAngle()

  private var absMinutes: Float = 0F
    set(value) {
      field = value

      minutes = (field % 60).toInt()
      hours = field.toInt().floorDiv(60)
    }

  private val indeterminateAnimator = ValueAnimator.ofFloat(0F, 1F).apply {
    repeatMode = ValueAnimator.RESTART
    repeatCount = ValueAnimator.INFINITE

    addUpdateListener {
      absMinutes += indeterminateSpeed
    }
  }

  private val discreteAnimator = ValueAnimator.ofFloat(0F, 1F).apply {
    interpolator = OvershootInterpolator()
    duration = 400L
  }

  constructor(ctx: Context, stroke: Stroke, color: Int) : this(ctx) {
    this.frameStroke = stroke
    this.clockColor = color
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

  @JvmName("animateToTime")
  fun animateTo(nextHours: Int, nextMinutes: Int) {
    _hours = nextHours.cycledClamp(12)
    _minutes = nextMinutes.cycledClamp(60)

    val beforeHrs = hoursAngle
    val beforeMins = minutesAngle

    val afterHrs = getHoursAngle(_hours, _minutes)
    val afterMins = getMinutesAngle(_minutes)

    val diffHrs = afterHrs - hoursAngle
    val diffMins = afterMins - minutesAngle

    discreteAnimator.apply {
      addUpdateListener {
        hoursAngle = beforeHrs + diffHrs * it.animatedFraction
        minutesAngle = beforeMins + diffMins * it.animatedFraction
        invalidateSelf()
      }

      start()
    }
  }

  override fun isRunning(): Boolean = indeterminateAnimator.isRunning

  override fun start() {
    absMinutes = (60 * hours + minutes).toFloat()
    indeterminateAnimator.start()
  }

  override fun stop() = indeterminateAnimator.cancel()

  private fun getHoursAngle(hrs: Int = 0, mins: Int = 0) = 30F * hrs + mins.toFloat() / 2F - 90

  private fun getMinutesAngle(mins: Int = 0) = 6F * mins - 90

  private fun Stroke.toDip() = when (this) {
    Stroke.THIN -> ctx.dip(1).toFloat()
    Stroke.LIGHT -> ctx.dip(2).toFloat()
    Stroke.REGULAR -> ctx.dip(3).toFloat()
    Stroke.BOLD -> ctx.dip(5).toFloat()
  }
}

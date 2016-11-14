package rm.com.clocks

import android.animation.Animator
import android.animation.TimeInterpolator
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
class ClockDrawable(private val ctx: Context) : Drawable(), Animatable {

  private var _minutes = 0
  var minutes: Int
    set(value) {
      _minutes = value.cycledClamp(to = 60)
      minutesAngle = minutesDegOf(_minutes)

      invalidateSelf()
    }
    get() = _minutes

  private var _hours = 0
  var hours: Int
    set(value) {
      _hours = value.cycledClamp(to = 12)
      hoursAngle = hoursDegOf(_hours, minutes)

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

  /**
   * defines whether this should draw a ring around the pointers
   */
  var hasFrame = true
    set(value) {
      field = value
      invalidateSelf()
    }

  var frameStroke = Stroke.LIGHT
    set(value) {
      field = value
      frame.strokeWidth = field.toDip(ctx)
      invalidateSelf()
    }

  var pointerStroke = Stroke.LIGHT
    set(value) {
      field = value
      pointers.strokeWidth = field.toDip(ctx)
      invalidateSelf()
    }

  var indeterminateSpeed = 1F

  var discreteInterpolator: TimeInterpolator = OvershootInterpolator()
    set(value) {
      field = value

      discreteAnimator.interpolator = field
    }

  var discreteDuration = 400L
    set(value) {
      field = value

      discreteAnimator.duration = field
    }

  var animationListener: Animator.AnimatorListener? = null
    set(value) {
      field = value

      field?.let {
        discreteAnimator.removeAllListeners()
        discreteAnimator.addListener(it)
      }
    }

  private val frame = smoothLinePaint().apply {
    strokeWidth = frameStroke.toDip(ctx)
    color = clockColor
  }

  private val pointers = smoothLinePaint().apply {
    strokeWidth = pointerStroke.toDip(ctx)
    color = clockColor
  }

  private val frameRadius: Float get() = width / 2 - frame.strokeWidth
  private val minutesRadius: Float get() = frameRadius * .8F
  private val hoursRadius: Float get() = frameRadius * .5F

  private var minutesAngle = minutesDegOf()
  private var hoursAngle = hoursDegOf()

  private var _absMinutes: Float = 0F
  private var absMinutes: Float
    set(value) {
      _absMinutes = value

      minutes = (_absMinutes % 60).toInt()
      hours = _absMinutes.toInt().floorDiv(60)
    }
    get() = _absMinutes

  private val indeterminateAnimator = animatorOf {
    repeatMode = ValueAnimator.RESTART
    repeatCount = ValueAnimator.INFINITE

    addUpdateListener {
      absMinutes += indeterminateSpeed
    }
  }

  private val discreteAnimator = animatorOf {
    interpolator = discreteInterpolator
    duration = discreteDuration
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
  fun animateTo(hrs: Int, min: Int) {
    if (isRunning) stop()

    _hours = hrs.cycledClamp(12)
    _minutes = min.cycledClamp(60)
    _absMinutes = (60 * _hours + _minutes).toFloat()

    val beforeHrs = hoursAngle
    val beforeMins = minutesAngle

    val afterHrs = hoursDegOf(_hours, _minutes)
    val afterMins = minutesDegOf(_minutes)

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

  override fun start() = indeterminateAnimator.start()

  override fun stop() = indeterminateAnimator.cancel()

  class Builder(private val ctx: Context) {

    private var hours = 0
    private var minutes = 0
    private var color = Color.WHITE
    private var hasFrame = true
    private var frameStroke = Stroke.REGULAR
    private var pointerStroke = Stroke.REGULAR
    private var duration = 400L
    private var interpolator: TimeInterpolator = OvershootInterpolator()
    private var listener: Animator.AnimatorListener? = null

    private lateinit var clockDrawable: ClockDrawable

    fun hours(hours: Int) = this.apply {
      this.hours = hours
    }

    fun minutes(minutes: Int) = this.apply {
      this.minutes = minutes
    }

    fun withColor(color: Int) = this.apply {
      this.color = color
    }

    fun withFrame(with: Boolean) = this.apply {
      this.hasFrame = with
    }

    fun withFrameStroke(stroke: Stroke) = this.apply {
      this.frameStroke = stroke
    }

    fun withPointerStroke(stroke: Stroke) = this.apply {
      this.pointerStroke = stroke
    }

    fun withDuration(duration: Long) = this.apply {
      this.duration = duration
    }

    fun withInterpolator(interpolator: TimeInterpolator) = this.apply {
      this.interpolator = interpolator
    }

    fun withListener(listener: Animator.AnimatorListener) = this.apply {
      this.listener = listener
    }

    fun build(): ClockDrawable {
      this.clockDrawable = ClockDrawable(ctx)
      this.clockDrawable.hours = this.hours
      this.clockDrawable.minutes = this.minutes
      this.clockDrawable.clockColor = this.color
      this.clockDrawable.hasFrame = this.hasFrame
      this.clockDrawable.frameStroke = this.frameStroke
      this.clockDrawable.pointerStroke = this.pointerStroke
      this.clockDrawable.discreteDuration = this.duration
      this.clockDrawable.discreteInterpolator = this.interpolator
      this.clockDrawable.animationListener = this.listener
      return this.clockDrawable
    }
  }
}
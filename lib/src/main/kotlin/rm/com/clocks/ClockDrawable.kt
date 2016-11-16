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
import android.widget.ImageView

/**
 * Created by alex
 */
class ClockDrawable(private val ctx: Context) : Drawable(), Clock, Animatable {

  private var _minutes = 0
  override var minutes: Int
    set(value) {
      _minutes = value.cycledClamp(to = COUNT_MINUTES)
      minutesAngle = minutesDegOf(_minutes)

      invalidateSelf()
    }
    get() = _minutes

  private var _hours = 0
  override var hours: Int
    set(value) {
      _hours = value.cycledClamp(to = COUNT_HOURS)
      hoursAngle = hoursDegOf(_hours, _minutes)

      invalidateSelf()
    }
    get() = _hours

  override var clockColor = Color.WHITE
    set(value) {
      field = value

      frame.color = field
      pointers.color = field

      invalidateSelf()
    }

  override var hasFrame = true
    set(value) {
      field = value
      invalidateSelf()
    }

  override var frameWidth = DEFAULT_STROKE
    set(value) {
      field = value
      frame.strokeWidth = field.toDip(ctx)
      invalidateSelf()
    }

  override var pointerWidth = DEFAULT_STROKE
    set(value) {
      field = value
      pointers.strokeWidth = field.toDip(ctx)
      invalidateSelf()
    }

  override var indeterminateSpeed = DEFAULT_SPEED

  override var timeSetInterpolator: TimeInterpolator = DEFAULT_INTERPOLATOR
    set(value) {
      field = value

      timeSetAnimator.interpolator = field
    }

  override var timeSetDuration = DEFAULT_DURATION
    set(value) {
      field = value

      timeSetAnimator.duration = field
    }

  override var animationListener: Animator.AnimatorListener? = null
    set(value) {
      field = value

      field?.let {
        timeSetAnimator.removeAllListeners()
        timeSetAnimator.addListener(it)
      }
    }

  private val frame = linePaintOf().apply {
    strokeWidth = frameWidth.toDip(ctx)
    color = clockColor
  }

  private val pointers = linePaintOf().apply {
    strokeWidth = pointerWidth.toDip(ctx)
    color = clockColor
  }

  private val frameRadius: Float get() = width / 2 - frame.strokeWidth
  private val minutesRadius: Float get() = frameRadius * POINTER_FACTOR_MINUTES
  private val hoursRadius: Float get() = frameRadius * POINTER_FACTOR_HOURS

  private var minutesAngle = minutesDegOf()
  private var hoursAngle = hoursDegOf()

  private var _absMinutes: Float = 0F
  private var absMinutes: Float
    set(value) {
      _absMinutes = value

      minutes = (_absMinutes % COUNT_MINUTES).toInt()
      hours = _absMinutes.toInt().floorDiv(COUNT_MINUTES)
    }
    get() = _absMinutes

  private val indeterminateAnimator = animatorOf {
    repeatMode = ValueAnimator.RESTART
    repeatCount = ValueAnimator.INFINITE

    addUpdateListener {
      absMinutes += indeterminateSpeed
    }
  }

  private val timeSetAnimator = animatorOf {
    interpolator = timeSetInterpolator
    duration = timeSetDuration
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

  override fun animateToTime(hours: Int, minutes: Int) {
    if (isRunning) stop()

    _hours = hours.cycledClamp(to = COUNT_HOURS)
    _minutes = minutes.cycledClamp(to = COUNT_MINUTES)
    _absMinutes = (COUNT_MINUTES * _hours + _minutes).toFloat()

    val beforeHrs = hoursAngle
    val beforeMins = minutesAngle

    val afterHrs = hoursDegOf(_hours, _minutes)
    val afterMins = minutesDegOf(_minutes)

    val diffHrs = afterHrs - hoursAngle
    val diffMins = afterMins - minutesAngle

    timeSetAnimator.apply {
      addUpdateListener {
        hoursAngle = beforeHrs + diffHrs * it.animatedFraction
        minutesAngle = beforeMins + diffMins * it.animatedFraction
        invalidateSelf()
      }

      start()
    }
  }

  override fun animateIndeterminate() = start()

  override fun isRunning(): Boolean = indeterminateAnimator.isRunning

  override fun start() = indeterminateAnimator.start()

  override fun stop() = indeterminateAnimator.cancel()

  companion object {

    @JvmStatic
    fun builder(ctx: Context): ClockDrawable.Builder {
      return ClockDrawable.Builder(ctx)
    }
  }

  class Builder(private val ctx: Context) {
    private var hours = 0
    private var minutes = 0
    private var color = Color.WHITE
    private var speed = DEFAULT_SPEED
    private var hasFrame = true
    private var frameWidth = DEFAULT_STROKE
    private var pointerWidth = DEFAULT_STROKE
    private var duration = DEFAULT_DURATION
    private var interpolator: TimeInterpolator = DEFAULT_INTERPOLATOR
    private var listener: Animator.AnimatorListener? = null

    private lateinit var clockDrawable: ClockDrawable

    fun hours(hours: Int) = apply {
      this.hours = hours
    }

    fun minutes(minutes: Int) = apply {
      this.minutes = minutes
    }

    fun withColor(color: Int) = apply {
      this.color = color
    }

    fun withSpeed(speed: Float) = apply {
      this.speed = speed
    }

    fun withFrame(with: Boolean) = apply {
      this.hasFrame = with
    }

    fun withFrameWidth(width: Stroke) = apply {
      this.frameWidth = width
    }

    fun withPointerWidth(width: Stroke) = apply {
      this.pointerWidth = width
    }

    fun withDuration(duration: Long) = apply {
      this.duration = duration
    }

    fun withInterpolator(interpolator: TimeInterpolator) = apply {
      this.interpolator = interpolator
    }

    fun withListener(listener: Animator.AnimatorListener) = apply {
      this.listener = listener
    }

    fun into(view: ImageView): ClockDrawable =
        build().apply { view.setImageDrawable(this) }

    fun build(): ClockDrawable {
      this.clockDrawable = ClockDrawable(ctx)
      this.clockDrawable.hours = this.hours
      this.clockDrawable.minutes = this.minutes
      this.clockDrawable.clockColor = this.color
      this.clockDrawable.indeterminateSpeed = this.speed
      this.clockDrawable.hasFrame = this.hasFrame
      this.clockDrawable.frameWidth = this.frameWidth
      this.clockDrawable.pointerWidth = this.pointerWidth
      this.clockDrawable.timeSetDuration = this.duration
      this.clockDrawable.timeSetInterpolator = this.interpolator
      this.clockDrawable.animationListener = this.listener
      return this.clockDrawable
    }
  }
}
package rm.com.clocks

import android.animation.Animator
import android.animation.TimeInterpolator
import android.content.Context
import android.graphics.drawable.Animatable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.ImageView

/**
 * Created by alex
 */

class ClockImageView : ImageView, Clock, Animatable {
  var clock = ClockDrawable(context)
    private set

  override var hours: Int
    get() = clock.hours
    set(value) {
      clock.hours = value
    }

  override var minutes: Int
    get() = clock.minutes
    set(value) {
      clock.minutes = value
    }

  override var clockColor: Int
    get() = clock.clockColor
    set(value) {
      clock.clockColor = value
    }

  override var indeterminateSpeed: Float
    get() = clock.indeterminateSpeed
    set(value) {
      clock.indeterminateSpeed = value
    }

  override var hasFrame: Boolean
    get() = clock.hasFrame
    set(value) {
      clock.hasFrame = value
    }

  override var frameWidth: Stroke
    get() = clock.frameWidth
    set(value) {
      clock.frameWidth = value
    }

  override var pointerWidth: Stroke
    get() = clock.pointerWidth
    set(value) {
      clock.pointerWidth = value
    }

  override var timeSetDuration: Long
    get() = clock.timeSetDuration
    set(value) {
      clock.timeSetDuration = value
    }

  override var timeSetInterpolator: TimeInterpolator
    get() = clock.timeSetInterpolator
    set(value) {
      clock.timeSetInterpolator = value
    }

  override var animationListener: Animator.AnimatorListener?
    get() = clock.animationListener
    set(value) {
      clock.animationListener = value
    }

  constructor(context: Context?) : this(context, null)

  constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)

  constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs,
      defStyleAttr) {
    attrs?.let {
      inflateAttrs(attrs)
    }

    setImageDrawable(clock)
  }

  override fun setImageDrawable(drawable: Drawable?) {
    super.setImageDrawable(drawable)

    if (drawable is ClockDrawable) {
      clock = drawable
    }
  }

  override fun animateToTime(hours: Int, minutes: Int) = clock.animateToTime(hours, minutes)

  override fun animateIndeterminate() = clock.animateIndeterminate()

  override fun isRunning() = clock.isRunning

  override fun start() = clock.start()

  override fun stop() = clock.stop()

  private fun inflateAttrs(attrs: AttributeSet) {
    val resAttrs = context.theme.obtainStyledAttributes(
        attrs,
        R.styleable.ClockImageView,
        0,
        0
    ) ?: return

    with(resAttrs) {
      hours = getInteger(R.styleable.ClockImageView_hours, hours)
      minutes = getInteger(R.styleable.ClockImageView_minutes, minutes)
      clockColor = getColor(R.styleable.ClockImageView_clockColor, clockColor)
      indeterminateSpeed = getFloat(R.styleable.ClockImageView_indeterminateSpeed,
          indeterminateSpeed)
      hasFrame = getBoolean(R.styleable.ClockImageView_hasFrame, hasFrame)
      frameWidth = getInt(R.styleable.ClockImageView_frameWidth, 0).asStroke()
      pointerWidth = getInt(R.styleable.ClockImageView_pointerWidth, 0).asStroke()
      timeSetDuration = getInteger(R.styleable.ClockImageView_timeSetDuration,
          timeSetDuration.toInt()).toLong()
      recycle()
    }
  }
}
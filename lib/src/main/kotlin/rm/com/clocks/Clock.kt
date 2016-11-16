package rm.com.clocks

import android.animation.Animator
import android.animation.TimeInterpolator

/**
 * Created by alex
 */
interface Clock {

  /**
   * Get or set hours on icon without animation, default is 0
   */
  var hours: Int

  /**
   * Get or set minutes on icon without animation, default is 0
   */
  var minutes: Int

  /**
   * Color of icon(pointers and frame), default is [android.graphics.Color.WHITE]
   */
  var clockColor: Int

  /**
   * Speed of minutes pointer movement for endless(indeterminate) animation,
   * see [animateIndeterminate], default value is [DEFAULT_SPEED]
   *
   * [indeterminateSpeed] can be negative if you need the rewind effect
   * or if you need the effect of "ticking clock" you can set a very small fraction
   *
   * rewind: -1F
   * ticking: 0.001F
   */
  var indeterminateSpeed: Float

  /**
   * Flag of whether this icon has frame, e. g. the ring around the pointers
   *
   * Default is true
   */
  var hasFrame: Boolean

  /**
   * Width of the frame, default is [DEFAULT_STROKE]
   *
   * It has type of [Stroke]
   *
   * [Stroke.THIN] -> 1 dip
   * [Stroke.LIGHT] -> 2 dip
   * [Stroke.REGULAR] -> 3 dip
   * [Stroke.BOLD] -> 5 dip
   */
  var frameWidth: Stroke

  /**
   * Width of the pointers, default is [DEFAULT_STROKE]
   *
   * It has type of [Stroke]
   *
   * [Stroke.THIN] -> 1 dip
   * [Stroke.LIGHT] -> 2 dip
   * [Stroke.REGULAR] -> 3 dip
   * [Stroke.BOLD] -> 5 dip
   */
  var pointerWidth: Stroke

  /**
   * Duration of animated time setting, default is [DEFAULT_DURATION] ms
   */
  var timeSetDuration: Long

  /**
   * Interpolator of animated time setting, default is [DEFAULT_INTERPOLATOR]
   */
  var timeSetInterpolator: TimeInterpolator

  /**
   * Animation listener for time setting animation
   */
  var animationListener: Animator.AnimatorListener?

  /**
   * Animated time setting to concrete time
   *
   * [hours] new hours, where the shorter pointer will be animatedly moved
   * It's int and can be any number, but it better should be 0..24(12)
   * if it is less than zero, it'll be like 12 + [hours]
   * so if [hours] == -1, than the value will be 11
   *
   * [minutes] new minutes, where the longer pointer will be animatedly moved
   * It's int and can be any number, but it better should be 0..60
   * if it is less than zero, it'll be like 60 + [minutes]
   * so if [minutes] == -1, than the value will be 59
   */
  fun animateToTime(hours: Int, minutes: Int)

  /**
   * Starts indeterminate(endless) animation,
   * maybe useful for some waiting or processing indicators
   *
   * The steps of minute step is defined by [indeterminateSpeed]
   *
   * the hours pointer moves according to the range that minutes pointer moved through
   */
  fun animateIndeterminate()
}
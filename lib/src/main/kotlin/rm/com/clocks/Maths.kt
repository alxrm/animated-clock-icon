package rm.com.clocks

/**
 * Created by alex
 */

internal fun Int.cycledClamp(to: Int): Int =
    when {
      this >= 0 -> this % to
      else -> to + this % to
    }

internal fun Int.floorDiv(by: Int): Int = Math.floor(this.toDouble() / by.toDouble()).toInt()

internal fun Float.toRad() = Math.toRadians(this.toDouble())

internal fun cosOfDeg(degrees: Float) = Math.cos(degrees.toRad()).toFloat()

internal fun sinOfDeg(degrees: Float) = Math.sin(degrees.toRad()).toFloat()

internal fun hoursDegOf(hours: Int = 0, minutes: Int = 0) = 30F * hours + minutes.toFloat() / 2F - 90

internal fun minutesDegOf(minutes: Int = 0) = 6F * minutes - 90

internal fun Float.endXOf(fromX: Float, len: Float) = len * cosOfDeg(this) + fromX

internal fun Float.endYOf(fromY: Float, len: Float) = len * sinOfDeg(this) + fromY
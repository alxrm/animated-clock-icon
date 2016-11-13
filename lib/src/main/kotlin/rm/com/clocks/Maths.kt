package rm.com.clocks

/**
 * Created by alex
 */

fun Int.cycledClamp(to: Int): Int =
    when {
      this >= 0 -> this % to
      else -> to + this % to
    }

fun Int.floorDiv(by: Int): Int = Math.floor(this.toDouble() / by.toDouble()).toInt()

fun Float.toRad() = Math.toRadians(this.toDouble())

fun cosOfDeg(degrees: Float) = Math.cos(degrees.toRad()).toFloat()

fun sinOfDeg(degrees: Float) = Math.sin(degrees.toRad()).toFloat()

fun Float.endsOf(startX: Float, startY: Float, len: Float) =
    (len * cosOfDeg(this) + startX) to (len * sinOfDeg(this) + startY)
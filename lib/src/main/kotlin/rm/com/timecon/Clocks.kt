package rm.com.timecon

/**
 * Created by alex
 */

fun Int.cycledClamp(from: Int = 0, to: Int): Int = when {
  this >= 0 -> this % to
  else -> to + this % to
}
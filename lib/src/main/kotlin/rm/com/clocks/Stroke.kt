package rm.com.clocks

import android.content.Context

/**
 * Created by alex
 */

enum class Stroke { THIN, LIGHT, REGULAR, BOLD }

internal fun Stroke.toDip(ctx: Context) = when (this) {
  Stroke.THIN -> ctx.dip(1).toFloat()
  Stroke.LIGHT -> ctx.dip(2).toFloat()
  Stroke.REGULAR -> ctx.dip(3).toFloat()
  Stroke.BOLD -> ctx.dip(5).toFloat()
}

internal fun Int.asStroke() = when (this) {
  0 -> Stroke.THIN
  1 -> Stroke.LIGHT
  2 -> Stroke.REGULAR
  3 -> Stroke.BOLD
  else -> Stroke.THIN
}
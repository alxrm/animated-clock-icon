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
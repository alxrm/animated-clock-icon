package rm.com.clocks

import android.view.animation.OvershootInterpolator

/**
 * Created by alex
 */
internal const val COUNT_MINUTES = 60
internal const val COUNT_HOURS = 12
internal const val POINTER_FACTOR_HOURS = .5F
internal const val POINTER_FACTOR_MINUTES = .8F
internal const val DEFAULT_SPEED = 1F
internal const val DEFAULT_DURATION = 600L
internal val DEFAULT_STROKE = Stroke.THIN
internal val DEFAULT_INTERPOLATOR = OvershootInterpolator()
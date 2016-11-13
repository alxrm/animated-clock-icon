package rm.com.clocks

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.ImageView

/**
 * Created by alex
 */

class ClockImageView : ImageView {

  private var clockDrawable = ClockDrawable(context)

  constructor(context: Context?) : super(context)

  constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
    inflateAttrs(attrs)
  }

  constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
    inflateAttrs(attrs)
  }

  override fun setImageDrawable(drawable: Drawable?) {
    if (drawable !is ClockDrawable) return

    clockDrawable = drawable
    super.setImageDrawable(drawable)
  }

  private fun inflateAttrs(attrs: AttributeSet?) {
    val resAttrs = context.theme.obtainStyledAttributes(
        attrs,
        intArrayOf(), // R.styleable.AudioWaveView,
        0,
        0
    ) ?: return

    with(resAttrs) {
      recycle()
    }
  }
}
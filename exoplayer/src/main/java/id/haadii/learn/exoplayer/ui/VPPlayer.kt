package id.visionplus.vpplayer.ui

import android.content.Context
import android.util.AttributeSet
import com.google.android.exoplayer2.ui.PlayerView

/**
 * Created by nurrahmanhaadii on 28,May,2020
 */
class VPPlayer : PlayerView {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

}
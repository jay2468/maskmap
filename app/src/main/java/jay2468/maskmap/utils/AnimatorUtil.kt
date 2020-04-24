package jay2468.maskmap.utils

import android.animation.Animator
import android.view.View
import android.view.ViewAnimationUtils

object AnimatorUtil {
    fun revealView(view: View, enterX: Int, enterY: Int, startRadius: Float, endRadius: Float, visibility: Int) {
        ViewAnimationUtils.createCircularReveal(view, enterX, enterY, startRadius, endRadius).apply {
            duration = 300
            addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator?) {
                    if (visibility == View.VISIBLE)
                        view.visibility = visibility
                }

                override fun onAnimationEnd(animation: Animator?) {
                    if (visibility == View.GONE)
                        view.visibility = visibility
                }

                override fun onAnimationRepeat(animation: Animator?) {
                }

                override fun onAnimationCancel(animation: Animator?) {
                }
            })
        }.start()
    }
}
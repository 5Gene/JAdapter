/*
 * Copyright 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package sparkj.adapter.animate

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.TimeInterpolator
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import androidx.core.view.animation.PathInterpolatorCompat
import androidx.transition.Fade
import androidx.transition.SidePropagation
import androidx.transition.TransitionValues


const val LARGE_EXPAND_DURATION = 300L
val LINEAR_OUT_SLOW_IN: TimeInterpolator by lazy(LazyThreadSafetyMode.NONE) {
    PathInterpolatorCompat.create(0f, 0f, 0.2f, 1f)
}

/**
 * Transition for stagger effect.
 * TransitionManager.beginDelayedTransition(recycleview, transition);
 * TransitionManager
 *
 * 用于在 ViewGroup 第一次显示时，对其子视图应用一个整体的入场动画。
 * ViewGroup.setLayoutAnimation(controller)
 * 用于在 ViewGroup 的子视图发生变化时（如添加、删除或隐藏子视图），对这些变化应用动画
 * xml
 * android:animateLayoutChanges="true"
 * ViewGroup.layoutTransition = LayoutTransition()
 *
 */
// We extend Fade, so fade-in effect is handled by the parent. We customize and add a slight
// slide-up effect to it.
class Stagger : Fade(IN) {

    init {
        // This duration is for a single item. See the comment below about propagation.
        duration = LARGE_EXPAND_DURATION / 2
        interpolator = LINEAR_OUT_SLOW_IN
        propagation = SidePropagation().apply {
            setSide(Gravity.BOTTOM)
            // We want the stagger effect to take as long as the duration of a single item.
            // In other words, the last item starts to fade in around the time when the first item
            // finishes animating. The overall animation will take about twice the duration of one
            // item fading in.
            setPropagationSpeed(1f)
        }
    }

    override fun createAnimator(
        sceneRoot: ViewGroup,
        startValues: TransitionValues?,
        endValues: TransitionValues?
    ): Animator? {
        val view = startValues?.view ?: endValues?.view ?: return null
        // The parent can create an Animator for the fade-in.
        val fadeAnimator = super.createAnimator(sceneRoot, startValues, endValues) ?: return null
        return AnimatorSet().apply {
            playTogether(
                fadeAnimator,
                // We make the view to slide up a little as it fades in.
                ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, view.height * 0.5f, 0f)
            )
        }
    }
}

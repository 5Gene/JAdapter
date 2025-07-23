package sparkj.adapter.animate

import android.view.ViewPropertyAnimator
import androidx.recyclerview.widget.RecyclerView

//另一种给recycleView添加动画的方式
//LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(this, resId);
//recyclerView.setLayoutAnimation(animation);
//https://www.geeksforgeeks.org/android/how-to-animate-recyclerview-items-in-android/
class ScaleItemAnimator : MyDefaultItemAnimator() {
    override fun animateAdd(holder: RecyclerView.ViewHolder): Boolean {
        holder.itemView.scaleX = 0F
        holder.itemView.scaleY = 0F
        return super.animateAdd(holder)
    }

    override fun doAddAnimation(animation: ViewPropertyAnimator): ViewPropertyAnimator? {
        return animation.scaleX(1F).scaleY(1F)
    }

    override fun doRemoveAnimation(animation: ViewPropertyAnimator): ViewPropertyAnimator? {
        return animation.scaleX(0F).scaleY(0F)
    }
}
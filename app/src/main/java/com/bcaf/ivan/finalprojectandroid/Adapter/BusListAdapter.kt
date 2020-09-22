package com.bcaf.ivan.finalprojectandroid.Adapter

import android.animation.ValueAnimator
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bcaf.ivan.finalprojectandroid.Controller.MainActivity
import com.bcaf.ivan.finalprojectandroid.Entity.Bus
import com.bcaf.ivan.finalprojectandroid.Helper.CustomActivity
import com.bcaf.ivan.finalprojectandroid.R
import com.bcaf.ivan.finalprojectandroid.ViewHolder.BusListViewHolder


class BusListAdapter(private val list:List<Bus>) : RecyclerView.Adapter<BusListViewHolder>(){
    lateinit var activity:CustomActivity
    lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusListViewHolder {
        activity= CustomActivity(parent.context as MainActivity)
        context= parent.context
        return BusListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.bus_list, parent, false)
        )
    }

    override fun getItemCount(): Int = list?.size

    override fun onBindViewHolder(holder: BusListViewHolder, position: Int) {
        holder.busCode.text = list[position].code
        holder.busMake.text = list[position].make
        holder.busCapacity.text = list[position].capacity
        holder.btn_expand.setOnClickListener { view ->
            if(holder.lyt_detail.visibility==View.INVISIBLE){
                rotateImageUp(holder.btn_expand)
                slideDown(holder.lyt_detail)
//                holder.lyt_detail.visibility = View.VISIBLE
                holder.btn_expand.background = ContextCompat.getDrawable(context, R.drawable.ic_shrink)
            }else{
                rotateImageDown(holder.btn_expand)
                slideUp(holder.lyt_detail)
//                holder.lyt_detail.visibility = View.GONE
                holder.btn_expand.background = ContextCompat.getDrawable(context, R.drawable.ic_expand)
            }
//            activity.start(ListBusActivity::class.java,key="busId",value= list[position].id!!)
        }
    }
    fun rotateImageDown(view: View) {


        val animSlideDown = AnimationUtils.loadAnimation(
            context,
            R.anim.rotate_down
        )
        animSlideDown.setAnimationListener(object : Animation.AnimationListener{
            override fun onAnimationRepeat(p0: Animation?) {
            }

            override fun onAnimationEnd(p0: Animation?) {
            }

            override fun onAnimationStart(p0: Animation?) {
            }

        });
        view.startAnimation(animSlideDown)
    }
    fun rotateImageUp(view: View) {


        val animSlideUp = AnimationUtils.loadAnimation(
            context,
            R.anim.rotate_up
        )
        animSlideUp.setAnimationListener(object : Animation.AnimationListener{
            override fun onAnimationRepeat(p0: Animation?) {
            }

            override fun onAnimationEnd(p0: Animation?) {
            }

            override fun onAnimationStart(p0: Animation?) {
            }

        });
        view.startAnimation(animSlideUp)
    }
    fun slideUp(view: View) {


        val animSlideUp = AnimationUtils.loadAnimation(
            context,
            R.anim.slide_up
        )
        animSlideUp.setAnimationListener(object : Animation.AnimationListener{
            override fun onAnimationRepeat(p0: Animation?) {
            }

            override fun onAnimationEnd(p0: Animation?) {
                view.visibility = View.INVISIBLE
            }

            override fun onAnimationStart(p0: Animation?) {
                setHeightUp(view)
            }

        });
        view.startAnimation(animSlideUp)
    }
    fun slideDown(view: View) {
        val animSlideDown: Animation = AnimationUtils.loadAnimation(
            context,
            R.anim.slide_down
        )
        animSlideDown.setAnimationListener(object : Animation.AnimationListener{
            override fun onAnimationRepeat(p0: Animation?) {
            }

            override fun onAnimationEnd(p0: Animation?) {
                var lyParam = view.layoutParams
                lyParam.height=ViewGroup.LayoutParams.WRAP_CONTENT
            }

            override fun onAnimationStart(p0: Animation?) {
                view.visibility = View.VISIBLE
                setHeightDown(view)
            }

        });
        view.startAnimation(animSlideDown)
    }
    fun setHeightDown(view: View){
        val anim =
            ValueAnimator.ofInt(view.measuredHeight, 150)
        anim.addUpdateListener { valueAnimator ->
            val value = valueAnimator.animatedValue as Int
            val layoutParams: ViewGroup.LayoutParams = view.layoutParams
            layoutParams.height = value
            view.layoutParams=layoutParams
        }
        anim.duration = 500
        anim.start()
    }
    fun setHeightUp(view: View){
        val anim =
            ValueAnimator.ofInt(view.measuredHeight, -100)
        anim.addUpdateListener { valueAnimator ->
            val value = valueAnimator.animatedValue as Int
            val layoutParams: ViewGroup.LayoutParams = view.layoutParams
            layoutParams.height = value
            view.layoutParams=layoutParams
        }
        anim.duration = 750
        anim.start()
    }
}
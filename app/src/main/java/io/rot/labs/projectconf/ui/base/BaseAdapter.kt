package io.rot.labs.projectconf.ui.base

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T : Any, VH : BaseItemViewHolder<T, out BaseItemViewModel<T>>>(
    parentLifecycle: Lifecycle,
    private val dataList: ArrayList<T>
) : RecyclerView.Adapter<VH>() {

    private var recyclerView: RecyclerView? = null

    init {
        /**
         * Lifecycle to handle Views when parent activity goes into RESUMED or PAUSED State
         */
        parentLifecycle.addObserver(object : LifecycleObserver {

            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            fun onParentDestroy() {
                recyclerView?.run {
                    for (i in 0 until childCount) {
                        getChildAt(i)?.let {
                            (getChildViewHolder(it) as BaseItemViewHolder<*, *>).run {
                                onDestroy()
                            }
                        }
                    }
                }
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_START)
            fun onParentStart() {
                recyclerView?.run {
                    if (layoutManager is LinearLayoutManager || layoutManager is GridLayoutManager) {
                        val first = if (layoutManager is LinearLayoutManager) {
                            (layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()
                        } else {
                            (layoutManager as GridLayoutManager).findFirstCompletelyVisibleItemPosition()
                        }

                        val last = if (layoutManager is LinearLayoutManager) {
                            (layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
                        } else {
                            (layoutManager as GridLayoutManager).findLastCompletelyVisibleItemPosition()
                        }

                        if (first in 0..last) {
                            for (i in first..last) {
                                findViewHolderForAdapterPosition(i)?.let {
                                    (it as BaseItemViewHolder<*, *>).onStart()
                                }
                            }
                        }
                    }
                }
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
            fun onParentStop() {
                recyclerView?.run {
                    for (i in 0 until childCount) {
                        getChildAt(i)?.let {
                            (getChildViewHolder(it) as BaseItemViewHolder<*, *>).onStop()
                        }
                    }
                }
            }
        })
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(dataList[position])
    }

    override fun onViewAttachedToWindow(holder: VH) {
        super.onViewAttachedToWindow(holder)
        holder.onStart()
    }

    override fun onViewDetachedFromWindow(holder: VH) {
        super.onViewDetachedFromWindow(holder)
        holder.onStop()
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        this.recyclerView = null
    }

    fun appendData(dataList: List<T>) {
        val oldCount = itemCount
        this.dataList.addAll(dataList)
        val currentCount = itemCount
        if (oldCount == 0 && currentCount > 0) {
            notifyDataSetChanged()
        } else if (oldCount in 1 until currentCount) {
            notifyItemRangeChanged(oldCount - 1, currentCount - oldCount)
        }
    }

    fun updateData(dataList: List<T>) {
        this.dataList.clear()
        this.dataList.addAll(dataList)
        notifyDataSetChanged()
    }
}

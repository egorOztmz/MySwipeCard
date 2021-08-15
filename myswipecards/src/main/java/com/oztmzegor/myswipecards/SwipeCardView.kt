package com.oztmzegor.myswipecards

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.viewpager2.widget.ViewPager2

/**
 * This class has a similar behavior to [ViewPager2] but it consumes skipped pages instead
 * Populate list items by setting [cardItemAdapter]
 * You may register to consume event by using [registerOnCardConsumedListener] and unregister by [unregisterOnCardConsumedListener]
 */
open class SwipeCardView @JvmOverloads constructor(
    context: Context,
    attr : AttributeSet? = null,
    defStyleAttr : Int = 0,
    defStyleRes : Int = 0,
) : FrameLayout(context, attr, defStyleAttr, defStyleRes) {

    fun interface OnCardConsumedListener {
        fun onCardConsumed()
    }

    var cardItemAdapter : CardItemAdapter<*, *>? = null
        set(value) {
            field = value
            viewPager.adapter = field
        }

    protected val viewPager : ViewPager2
    protected val onCardConsumedListeners : MutableList<OnCardConsumedListener> = mutableListOf()

    init {
        View.inflate(context, R.layout.view_swipe_card, this)
        viewPager = findViewById(R.id.viewPager)

        viewPager.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {
                private var position = 0

                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    this.position = position
                }

                override fun onPageScrollStateChanged(state: Int) {
                    super.onPageScrollStateChanged(state)

                    if (state == ViewPager2.SCROLL_STATE_IDLE) {
                        cardItemAdapter?.let {adapter ->
                            if (position > 0) {
                                post {
                                    for (i in position downTo 1) {
                                        adapter.removeCard(i - 1)
                                        onCardConsumedListeners.forEach { it.onCardConsumed() }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        )
    }

    /**
     * Register consume events by using [OnCardConsumedListener]
     * don't forget to unregister it at the end of your lifecycle
     * @param onCardConsumedListener Listener implementation
     */
    fun registerOnCardConsumedListener(onCardConsumedListener: OnCardConsumedListener) {
        onCardConsumedListeners.add(onCardConsumedListener)
    }

    /**
     * Unregister [OnCardConsumedListener] via this method
     * @param onCardConsumedListener Registered listener implementation
     */
    fun unregisterOnCardConsumedListener(onCardConsumedListener: OnCardConsumedListener) {
        onCardConsumedListeners.remove(onCardConsumedListener)
    }

}
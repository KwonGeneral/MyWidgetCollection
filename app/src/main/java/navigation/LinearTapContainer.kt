package navigation

import android.content.Context
import android.content.res.Resources
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.children
import com.kwon.mywidgetcollection.R

class LinearTapContainer(context: Context?, attrs: AttributeSet?) : LinearLayout(context, attrs) {
    private var ltTitleStyle: Int = 0
    private var ltContentStyle: Int = 0
    private var ltItemSelector: Int = 0
    private var viewList = mutableListOf<View>()
    private val Int.dp: Int get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()
    var onItemClick: (action: String) -> Unit = { }

    init {
        context?.let { con ->
            attrs?.let { attr ->
                initAttrs(con, attr)
            }
        }
    }

    private fun child() {
        for(v in children) {
            viewList.add(v)
            (v as LinearTapItem).layoutParams = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1F)
            v.setTextStyle(ltTitleStyle, ltContentStyle)
            v.setItemSelector(ltItemSelector)
            v.setOnClickListener {
                for(vList in viewList) { v.itemInit((vList as LinearTapItem)) }
                if(v.isChecked) v.toggle() else v.toggle()
                onItemClick?.invoke(v.action)
            }
        }
    }

    private fun initAttrs(context: Context, attrs: AttributeSet) {
        gravity = Gravity.CENTER
        context.obtainStyledAttributes(attrs, R.styleable.LinearTapContainer)?.let { typedArray ->
            val ori = typedArray.getString(R.styleable.LinearTapContainer_lt_orientation)
            if(ori != null && ori == "vertical") {
                orientation = VERTICAL
            } else {
                orientation = HORIZONTAL
            }
//            setPadding(0, 8.dp, 0, 4.dp)
            ltTitleStyle = typedArray.getResourceId(R.styleable.LinearTapContainer_lt_title_styles, 0)
            ltContentStyle = typedArray.getResourceId(R.styleable.LinearTapContainer_lt_content_styles, 0)
            ltItemSelector = typedArray.getResourceId(R.styleable.LinearTapContainer_lt_item_selector, 0)
            typedArray.recycle()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        child()
    }
}
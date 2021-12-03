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

class BottomNavigationContainer(context: Context?, attrs: AttributeSet?) : LinearLayout(context, attrs) {
    private var nvTextStyles: Int = 0
    private var nvItemSelector: Int = 0
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
            (v as BottomNavigationItem).layoutParams = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1F)
            v.setTextStyle(nvTextStyles)
            v.setItemSelector(nvItemSelector)
            v.setOnClickListener {
                for(vList in viewList) { v.itemInit((vList as BottomNavigationItem)) }
                if(v.isChecked) v.toggle() else v.toggle()
                onItemClick?.invoke(v.action)
            }
        }
    }

    private fun initAttrs(context: Context, attrs: AttributeSet) {
        orientation = HORIZONTAL
        gravity = Gravity.CENTER
        context.obtainStyledAttributes(attrs, R.styleable.BottomNavigationContainer)?.let { typedArray ->
            setPadding(0, 8.dp, 0, 4.dp)
            nvTextStyles = typedArray.getResourceId(R.styleable.BottomNavigationContainer_nv_text_styles, 0)
            nvItemSelector = typedArray.getResourceId(R.styleable.BottomNavigationContainer_nv_item_selector, 0)
            typedArray.recycle()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        child()
    }
}
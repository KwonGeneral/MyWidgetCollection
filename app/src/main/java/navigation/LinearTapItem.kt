package navigation

import android.content.Context
import android.content.res.Resources
import android.content.res.XmlResourceParser
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.widget.Checkable
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import com.kwon.mywidgetcollection.data.LinearTapItemData

class LinearTapItem(context: Context?, val item: LinearTapItemData) : LinearLayout(context), Checkable {
    lateinit var action: String
    lateinit var titleTextView:AppCompatTextView
    lateinit var contentTextView:AppCompatTextView
    var defaultColor: String? = null
    var focusColor: String? = null
    var disableColor: String? = null
    private val Int.dp: Int get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

    init {
        context?.let { con ->
            initAttrs(con)
        }
    }

    private fun initAttrs(context: Context) {
        orientation = VERTICAL

        action = item.ty_action!!

        titleTextView = AppCompatTextView(context)
        with(titleTextView) {
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1f)
            gravity = Gravity.CENTER
            textAlignment = TextView.TEXT_ALIGNMENT_CENTER
            titleTextView.includeFontPadding = false
            item.ty_title?.let {
                text = it
            }
            addView(titleTextView)
        }

        contentTextView = AppCompatTextView(context)
        with(contentTextView) {
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1f)
            gravity = Gravity.CENTER
            textAlignment = TextView.TEXT_ALIGNMENT_CENTER
            contentTextView.includeFontPadding = false
            item.ty_content?.let {
                text = it
            }
            addView(contentTextView)
        }

    }

    fun itemInit(view: LinearTapItem) {
        view.isChecked = false
    }

    fun setTextStyle(title_id:Int, content_id:Int) {
        titleTextView.setTextAppearance(title_id)
        contentTextView.setTextAppearance(content_id)
    }

    fun setItemSelector(id: Int) {
        parseXml(resources.getXml(id))
    }

    private fun parseXml(parser: XmlResourceParser) {
        var eventType = -1
        val namespace = "http://schemas.android.com/apk/res/android"

        while (eventType != XmlResourceParser.END_DOCUMENT) {
            if (eventType == XmlResourceParser.START_TAG) {
                if (parser.name == "item") {
                    val stateFocused: String? = parser.getAttributeValue(namespace, "state_focused")
                    val stateEnabled: String? = parser.getAttributeValue(namespace, "state_enabled")
                    val color = resources.getString(parser.getAttributeResourceValue(namespace, "drawable", 0))
                    if(stateFocused != null) {
                        if(stateFocused == "true") focusColor = color else defaultColor = color
                    }
                    if(stateEnabled != null) {
                       disableColor = color
                    }
                }
            }
            eventType = parser.next()
        }
    }

    interface OnCheckedChangeListener {
        fun onCheckedChanged(checkableView: View?, isChecked: Boolean)
    }

    private val checkedStateSet = intArrayOf(android.R.attr.state_checked)
    private var mChecked = false
    private var mOnCheckedChangeListener: OnCheckedChangeListener? = null

    override fun isChecked(): Boolean {
        return mChecked
    }

    override fun setChecked(checked: Boolean) {
        if (checked != mChecked) {
            if(checked) {
                if(focusColor != null) {
                    titleTextView.setTextColor(Color.parseColor(focusColor))
                }
            } else {
                if(defaultColor != null) {
                    titleTextView.setTextColor(Color.parseColor(defaultColor))
                }
            }
            mChecked = checked
            refreshDrawableState()
            if (mOnCheckedChangeListener != null) {
                mOnCheckedChangeListener!!.onCheckedChanged(this, mChecked)
            }
        }
    }

    override fun toggle() {
        isChecked = !mChecked
    }

    override fun onCreateDrawableState(extraSpace: Int): IntArray? {
        val drawableState = super.onCreateDrawableState(extraSpace + 1)
        if (isChecked) {
            mergeDrawableStates(drawableState, checkedStateSet)
        }
        return drawableState
    }

    fun setOnCheckedChangeListener(listener: OnCheckedChangeListener?) {
        mOnCheckedChangeListener = listener
    }
}
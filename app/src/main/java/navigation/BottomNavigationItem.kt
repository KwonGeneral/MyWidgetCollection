package navigation

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.content.res.XmlResourceParser
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.Checkable
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.kwon.mywidgetcollection.R

class BottomNavigationItem(context: Context?, attrs: AttributeSet?) : LinearLayout(context, attrs), Checkable {
    lateinit var action: String
    lateinit var imageView: AppCompatImageView
    lateinit var textView:AppCompatTextView
    private lateinit var defaultColor: String
    private lateinit var focusColor: String
    private lateinit var disableColor: String
    private val Int.dp: Int get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

    init {
        context?.let { con ->
            attrs?.let { attr ->
                initAttrs(con, attr)
            }
        }
    }

    private fun initAttrs(context: Context, attrs: AttributeSet) {
        orientation = VERTICAL
        gravity = Gravity.CENTER

        context.obtainStyledAttributes(attrs, R.styleable.BottomNavigationItem)?.let { typedArray ->
            action = typedArray.getString(R.styleable.BottomNavigationItem_nv_action)!!

            imageView = AppCompatImageView(context)
            with(imageView) {
                val layout = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1f)
                layout.setMargins(0, 0, 0, 4.dp)
                layoutParams = layout
                adjustViewBounds = true
                setImageResource(typedArray.getResourceId(R.styleable.BottomNavigationItem_nv_image_src, 0))
                setBackgroundColor(Color.TRANSPARENT)
                isClickable = false
                addView(this)
            }

            textView = AppCompatTextView(context)
            with(textView) {
                layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
                textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                textView.includeFontPadding = false
                typedArray.getString(R.styleable.BottomNavigationItem_nv_text)?.let {
                    text = it
                }
                addView(textView)
            }

            typedArray.recycle()
        }

    }

    fun itemInit(view: BottomNavigationItem) {
        view.isChecked = false
    }

    fun setTextStyle(id:Int) {
        textView.setTextAppearance(id)
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
                imageView.imageTintList = ColorStateList.valueOf(Color.parseColor(focusColor))
                textView.setTextColor(Color.parseColor(focusColor))
            } else {
                imageView.imageTintList = ColorStateList.valueOf(Color.parseColor(defaultColor))
                textView.setTextColor(Color.parseColor(defaultColor))
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
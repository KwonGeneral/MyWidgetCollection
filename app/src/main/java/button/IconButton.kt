package button

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import com.kwon.mywidgetcollection.R

class IconButton : AppCompatButton {
    private var drawableWidth = 0
    private var drawablePosition: DrawablePositions? = null
    private var iconPadding = 0

    // Cached to prevent allocation during onLayout
    var bounds: Rect?

    private enum class DrawablePositions {
        NONE, LEFT_AND_RIGHT, LEFT, RIGHT
    }

    constructor(context: Context) : super(context) {
        bounds = Rect()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        bounds = Rect()
        applyAttributes(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        bounds = Rect()
        applyAttributes(attrs)
    }

    protected fun applyAttributes(attrs: AttributeSet?) {
        // Slight contortion to prevent allocating in onLayout
        if (null == bounds) {
            bounds = Rect()
        }
        val typedArray: TypedArray = getContext().obtainStyledAttributes(attrs, R.styleable.IconButton)
        val paddingId: Int = typedArray.getDimensionPixelSize(R.styleable.IconButton_iconPadding, 0)
        setIconPadding(paddingId)
        typedArray.recycle()
    }
    fun setIconPadding(padding: Int) {
        iconPadding = padding
        requestLayout()
    }
    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        val textPaint: Paint = getPaint()
        val text: String = getText().toString()
        textPaint.getTextBounds(text, 0, text.length, bounds)
        bounds?.width()?.let { textWidth ->
            val factor = if (drawablePosition == DrawablePositions.LEFT_AND_RIGHT) 2 else 1
            val contentWidth = drawableWidth + iconPadding * factor + textWidth
            val horizontalPadding = (getWidth() / 2.0 - contentWidth / 2.0).toInt()
            setCompoundDrawablePadding(-horizontalPadding + iconPadding)
            when (drawablePosition) {
                DrawablePositions.LEFT -> setPadding(
                    horizontalPadding,
                    getPaddingTop(),
                    0,
                    getPaddingBottom()
                )
                DrawablePositions.RIGHT -> setPadding(
                    0,
                    getPaddingTop(),
                    horizontalPadding,
                    getPaddingBottom()
                )
                DrawablePositions.LEFT_AND_RIGHT -> setPadding(
                    horizontalPadding,
                    getPaddingTop(),
                    horizontalPadding,
                    getPaddingBottom()
                )
                else -> setPadding(0, getPaddingTop(), 0, getPaddingBottom())
            }
        }

    }

    override fun setCompoundDrawablesWithIntrinsicBounds(
        left: Drawable?,
        top: Drawable?,
        right: Drawable?,
        bottom: Drawable?
    ) {
        super.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom)
        if (left != null && right != null) {
            drawableWidth = left.intrinsicWidth + right.intrinsicWidth
            drawablePosition = DrawablePositions.LEFT_AND_RIGHT
        } else if (left != null) {
            drawableWidth = left.intrinsicWidth
            drawablePosition = DrawablePositions.LEFT
        } else if (right != null) {
            drawableWidth = right.intrinsicWidth
            drawablePosition = DrawablePositions.RIGHT
        } else {
            drawablePosition = DrawablePositions.NONE
        }
        requestLayout()
    }
}
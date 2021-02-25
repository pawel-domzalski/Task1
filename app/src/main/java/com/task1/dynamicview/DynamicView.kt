package com.task1.dynamicview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import java.util.*

class DynamicView : View {

    private lateinit var paint: Paint
    private val points = ArrayList<DynamicPoint>()
    private var scaleXPoint = 1f
    private var scaleYPoint = 1f

    private var minX : DynamicPoint? = null
    private var maxX : DynamicPoint? = null

    private var minY : DynamicPoint? = null
    private var maxY : DynamicPoint? = null

    private var transitionX = 0f
    private var transitionY = 0f

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes)


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        points.forEach {
            canvas?.drawCircle(transitionX + it.x * scaleXPoint,
                transitionY + it.y * scaleYPoint, it.radius, paint)
        }

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val w = MeasureSpec.getSize(widthMeasureSpec)
        val h = MeasureSpec.getSize(heightMeasureSpec)

        val xDistance = (maxX?.x ?: 0f) - (minX?.x ?: 0f)
        val yDistance = (maxY?.y ?: 0f) - (minY?.y ?: 0f)

        transitionX = maxX?.radius ?: 0f
        transitionY = maxY?.radius ?: 0f

        scaleXPoint = w/(xDistance + transitionX/2)
        scaleYPoint = h/(yDistance + transitionY/2)

        if (scaleXPoint < scaleYPoint) {
            scaleYPoint = scaleXPoint
        } else{
            scaleXPoint = scaleYPoint
        }

    }

    private fun setupPaint() {
        paint = Paint()
        paint.style = Paint.Style.FILL
        paint.color = Color.BLUE
    }

    fun loadPoints(pointList: List<DynamicPoint>) {
        points.clear()

        setupPaint()

        points.addAll(pointList)

        minX = points.minByOrNull { it.x - it.radius }
        maxX = points.maxByOrNull { it.x + it.radius }

        minY = points.minByOrNull { it.y - it.radius }
        maxY = points.maxByOrNull { it.y + it.radius }

    }
}
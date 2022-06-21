package ru.ahoy.customweather.presentation.ui.views

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.toBitmap
import ru.ahoy.customweather.R


@Suppress("JoinDeclarationAndAssignment")
class WeatherView : androidx.appcompat.widget.AppCompatImageView {

    companion object {
        const val SUNNY = 0
        const val CLOUDY = 1
        const val PARTLY_CLOUDY = 2
        const val RAIN = -1
    }

    private lateinit var viewRect: RectF
    private lateinit var drawableRect: RectF
    private val mtrx: Matrix = Matrix()
    private var paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var cloud1: Bitmap
    private var cloud2: Bitmap
    private var cloud3: Bitmap
    private var sun: Bitmap
    private var result: Bitmap? = null
    var weather: Int = -1
        set(value) {
            field = value
            invalidate()
        }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.WeatherView, 0, 0)
        weather = a.getInt(R.styleable.WeatherView_weather, -1)
        a.recycle()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleInt: Int) : super(context, attrs, defStyleInt) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.WeatherView, 0, 0)
        weather = a.getInt(R.styleable.WeatherView_weather, -1)
        a.recycle()
    }

    init {
        cloud1 = getDrawable(R.drawable.weather_clouds_1)?.toBitmap()!!
        cloud2 = getDrawable(R.drawable.weather_clouds_2)?.toBitmap()!!
        cloud3 = getDrawable(R.drawable.weather_clouds_3)?.toBitmap()!!
        sun = getDrawable(R.drawable.weather_sun)?.toBitmap()!!
    }

    private fun Int.dp(): Int {
        return (resources.displayMetrics.density * this).toInt()
    }

    private fun getDrawable(id: Int): Drawable? {
        return AppCompatResources.getDrawable(context, id)
    }

    private fun cloudsBitmap(): Bitmap {
        val height = cloud3.height + cloud2.height * 0.8f
        val result = Bitmap.createBitmap(cloud2.width, height.toInt(), Bitmap.Config.ARGB_8888)
        val canvas = Canvas(result)

        canvas.drawBitmap(cloud3, cloud2.width.toFloat() - cloud3.width.toFloat(), 0f, paint)
        canvas.drawBitmap(cloud2, 0f, cloud3.height / 2.5f, paint)
        canvas.drawBitmap(cloud1, cloud1.width * 0.20f, cloud2.height * 0.9f, paint)
        return result
    }

    private fun sunCloudsBitmap(): Bitmap {
        val clouds = cloudsBitmap()
        val height = clouds.height + sun.height * 0.5f
        val width = clouds.width * 0.5f + sun.height
        val result = Bitmap.createBitmap(width.toInt(), height.toInt(), Bitmap.Config.ARGB_8888)
        val canvas = Canvas(result)

        canvas.drawBitmap(sun, clouds.width * 0.5f, 0f, paint)
        canvas.drawBitmap(clouds, 0f, sun.height * 0.5f, paint)
        return result
    }

    private fun sunBitmap(): Bitmap {
        val result = Bitmap.createBitmap(sun.width, sun.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(result)

        canvas.drawBitmap(sun, 0f, 0f, paint)
        return result
    }

    private fun placeholderBitmap(): Bitmap {
        val result = Bitmap.createBitmap(width, height, Bitmap.Config.ALPHA_8)
        val canvas = Canvas(result)

        canvas.drawARGB(10, 0, 0, 0)
        return result
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        result = when (weather) {
            0 -> sunBitmap()
            1 -> cloudsBitmap()
            2 -> sunCloudsBitmap()
            else -> placeholderBitmap()
        }.also {
            drawableRect = RectF(0f, 0f, it.width.toFloat(), it.height.toFloat())
            viewRect = RectF(0f, 0f, w.toFloat(), h.toFloat())
        }
    }

    @SuppressLint("CanvasSize")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        result?.let {
            mtrx.setRectToRect(drawableRect, viewRect, Matrix.ScaleToFit.CENTER)
            canvas?.drawBitmap(it, mtrx, paint)
        }
    }
}
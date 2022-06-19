package ru.ahoy.customweather.presentation.ui.views

import android.content.Context
import android.graphics.*
import android.graphics.Bitmap.createBitmap
import android.util.AttributeSet
import android.view.View
import ru.ahoy.customweather.R


@Suppress("JoinDeclarationAndAssignment")
class WeatherCloud : View {

    private lateinit var paint: Paint
    private lateinit var bitmapSrc: Bitmap
    private lateinit var bitmap: Bitmap
    private lateinit var matrixSrc: Matrix

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        bitmapSrc = BitmapFactory.decodeResource(resources, R.drawable.weather_clouds_1)
        matrixSrc = Matrix()
        bitmap = createBitmap(bitmapSrc, 0, 0, bitmapSrc.width / 2, bitmapSrc.height / 2, matrixSrc, true)

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawARGB(80, 102, 204, 255)
        canvas?.drawBitmap(bitmap, 0f, 0f, paint)
    }

}
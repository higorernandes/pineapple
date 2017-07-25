package pineapplesoftware.pineappleapp.util

import android.content.Context
import android.graphics.*
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation

/**
 * Created by root on 2017-07-24.
 */
class CircleTransform(context: Context) : BitmapTransformation(context) {

    //region Overridden Methods

    override fun transform(pool: BitmapPool, toTransform: Bitmap, outWidth: Int, outHeight: Int): Bitmap? {
        return circleCrop(pool, toTransform)
    }

    override fun getId(): String {
        return javaClass.name
    }

    //endregion

    //region Companion Object

    companion object {
        fun circleCrop(pool: BitmapPool, source: Bitmap) : Bitmap? {
            if (source == null) return null

            var size : Int = Math.min(source.width, source.height)
            var x = (source.width - size) / 2
            var y = (source.height - size) / 2

            var squared : Bitmap = Bitmap.createBitmap(source, x, y, size, size)

            var result : Bitmap = pool.get(size, size, Bitmap.Config.ARGB_8888)

            if (result == null) {
                result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
            }

            var canvas : Canvas = Canvas(result)
            var paint : Paint = Paint()
            paint.setShader(BitmapShader(squared, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP))
            paint.isAntiAlias = true
            var r : Float = size / 2f
            canvas.drawCircle(r, r, r, paint)

            return result
        }
    }

    //endregion
}
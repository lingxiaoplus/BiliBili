package com.camera.lingxiao.common.utills

import android.content.ContentResolver
import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.graphics.Rect
import android.media.ExifInterface
import android.net.Uri
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import java.io.BufferedOutputStream
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

/**
 * Created by lingxiao on 2017/9/18.
 */

class BitmapUtil {

    /**
     * 获取本地图片
     */
    private fun getLocationImgFile(path: String): Bitmap {
        //图片长宽缩小1/2倍
        //Bitmap bitmap = BitmapFactory.decodeFile(mFilePath, getBitmapOption(2));
        return BitmapFactory.decodeFile(path)
    }

    private fun getBitmapOption(inSampleSize: Int): BitmapFactory.Options {
        System.gc()
        val options = BitmapFactory.Options()
        options.inPurgeable = true
        options.inSampleSize = inSampleSize
        return options
    }

    companion object {
        /**
         * @description 计算图片的压缩比率
         *
         * @param options 参数
         * @param reqWidth 目标的宽度
         * @param reqHeight 目标的高度
         * @return
         */
        private fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
            // 源图片的高度和宽度
            val height = options.outHeight
            val width = options.outWidth
            var inSampleSize = 1
            if (height > reqHeight || width > reqWidth) {
                val halfHeight = height / 2
                val halfWidth = width / 2
                // Calculate the largest inSampleSize value that is a power of 2 and keeps both
                // height and width larger than the requested height and width.
                while (halfHeight / inSampleSize > reqHeight && halfWidth / inSampleSize > reqWidth) {
                    inSampleSize *= 2
                }
            }
            return inSampleSize
        }

        /**
         * @description 通过传入的bitmap，进行压缩，得到符合标准的bitmap
         *
         * @param src
         * @param dstWidth
         * @param dstHeight
         * @return
         */
        private fun createScaleBitmap(src: Bitmap?, dstWidth: Int, dstHeight: Int, inSampleSize: Int): Bitmap? {
            if (null == src) {
                return null
            }
            if (dstWidth < 0 || dstHeight < 0) {
                return null
            }
            // 如果是放大图片，filter决定是否平滑，如果是缩小图片，filter无影响，我们这里是缩小图片，所以直接设置为false
            val dst = Bitmap.createScaledBitmap(src, dstWidth, dstHeight, false)
            if (src != dst) { // 如果没有缩放，那么不回收
                src.recycle() // 释放Bitmap的native像素数组
            }
            return dst
        }

        /**
         * @description 从Resources中加载图片
         *
         * @param res
         * @param resId
         * @param reqWidth
         * @param reqHeight
         * @return
         */
        fun decodeSampledBitmapFromResource(res: Resources, resId: Int, reqWidth: Int, reqHeight: Int): Bitmap? {
            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true // 设置成了true,不占用内存，只获取bitmap宽高
            BitmapFactory.decodeResource(res, resId, options) // 读取图片长宽，目的是得到图片的宽高
            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight) // 调用上面定义的方法计算inSampleSize值
            // 使用获取到的inSampleSize值再次解析图片
            options.inJustDecodeBounds = false
            val src = BitmapFactory.decodeResource(res, resId, options) // 载入一个稍大的缩略图
            return createScaleBitmap(src, reqWidth, reqHeight, options.inSampleSize) // 通过得到的bitmap，进一步得到目标大小的缩略图
        }

        /**
         * @description 从SD卡上加载图片
         *
         * @param pathName
         * @param reqWidth
         * @param reqHeight
         * @return
         */
        fun decodeSampledBitmapFromFile(pathName: String, reqWidth: Int, reqHeight: Int): Bitmap? {
            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            BitmapFactory.decodeFile(pathName, options)
            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)
            options.inJustDecodeBounds = false
            val src = BitmapFactory.decodeFile(pathName, options)
            return createScaleBitmap(src, reqWidth, reqHeight, options.inSampleSize)
        }

        /**
         * 旋转图片
         *
         * @param angle  被旋转角度
         * @param bitmap 图片对象
         * @return 旋转后的图片
         */
        fun rotaingBitmap(angle: Int, bitmap: Bitmap?): Bitmap {
            var bitmap = bitmap
            //bitmap = small(bitmap);   不缩放
            var returnBm: Bitmap? = null
            // 根据旋转角度，生成旋转矩阵
            val matrix = Matrix()
            matrix.postRotate(angle.toFloat())
            // 将原始图片按照旋转矩阵进行旋转，并得到新的图片
            returnBm = Bitmap.createBitmap(
                bitmap!!, 0, 0,
                bitmap.width,
                bitmap.height, matrix, true
            )
            if (returnBm == null) {
                returnBm = bitmap
            }
            if (bitmap != returnBm && !bitmap.isRecycled) {
                bitmap.recycle()
                bitmap = null
            }
            return returnBm
        }


        /**
         * 将Bitmap转换成文件
         * 保存文件
         *
         * @param bm
         * @param filename
         * @throws IOException
         */
        @Throws(IOException::class)
        fun saveFile(bm: Bitmap, filename: String, filepath: String): File {
            val file = File(filepath + filename)
            if (file.exists()) {
                file.delete()
            }
            val bos = BufferedOutputStream(FileOutputStream(file))
            bm.compress(Bitmap.CompressFormat.JPEG, 100, bos)
            bos.flush()
            bos.close()
            return file
        }

        /**
         * 图片是横屏还是竖屏
         * @param path
         * @return
         */
        fun isLandscape(path: String): Boolean {
            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true // 设置成了true,不占用内存，只获取bitmap宽高
            BitmapFactory.decodeFile(path, options) // 读取图片长宽，目的是得到图片的宽高
            return if (options.outWidth > options.outHeight) {
                true
            } else false
        }

        /**
         * 将调用系统相册的图片进行压缩
         *
         * @param contentResolver
         * @param uri
         * @return
         */
        fun getBitmapFormUri(contentResolver: ContentResolver, uri: Uri, width: Float, height: Float): Bitmap? {
            try {
                var input = contentResolver.openInputStream(uri)
                val onlyBoundsOptions = BitmapFactory.Options()
                onlyBoundsOptions.inJustDecodeBounds = true
                onlyBoundsOptions.inDither = true//optional
                onlyBoundsOptions.inPreferredConfig = Bitmap.Config.ARGB_8888//optional
                BitmapFactory.decodeStream(input, null, onlyBoundsOptions)
                input!!.close()
                val originalWidth = onlyBoundsOptions.outWidth
                val originalHeight = onlyBoundsOptions.outHeight
                if (originalWidth == -1 || originalHeight == -1)
                    return null
                //图片分辨率以480x800为标准
                //float hh = 800f;//这里设置高度为800f
                //float ww = 480f;//这里设置宽度为480f
                //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
                var be = 1//be=1表示不缩放
                if (originalWidth > originalHeight && originalWidth > width) {//如果宽度大的话根据宽度固定大小缩放
                    be = (originalWidth / width).toInt()
                } else if (originalWidth < originalHeight && originalHeight > height) {//如果高度高的话根据宽度固定大小缩放
                    be = (originalHeight / height).toInt()
                }
                if (be <= 0)
                    be = 1
                //比例压缩
                val bitmapOptions = BitmapFactory.Options()
                bitmapOptions.inSampleSize = be//设置缩放比例
                bitmapOptions.inDither = true//optional
                bitmapOptions.inPreferredConfig = Bitmap.Config.ARGB_8888//optional
                input = contentResolver.openInputStream(uri)
                val bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions)
                input!!.close()

                return compressImageByQuality(bitmap!!, 1024)//再进行质量压缩
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return null
        }

        /**
         * 通过分辨率压缩bitmap
         * @param bitmap
         * @param width 需要压缩的尺寸
         * @param height
         * @return
         */
        fun compressImageByResolution(bitmap: Bitmap?, width: Float, height: Float): Bitmap? {
            var bitmap = bitmap
            try {
                val onlyBoundsOptions = BitmapFactory.Options()
                onlyBoundsOptions.inJustDecodeBounds = true
                onlyBoundsOptions.inDither = true
                onlyBoundsOptions.inPreferredConfig = Bitmap.Config.ARGB_8888

                val originalWidth = onlyBoundsOptions.outWidth
                val originalHeight = onlyBoundsOptions.outHeight
                if (originalWidth == -1 || originalHeight == -1)
                    return null
                //图片分辨率以480x800为标准
                //float hh = 800f;//这里设置高度为800f
                //float ww = 480f;//这里设置宽度为480f
                //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
                var be = 1f//be=1表示不缩放
                if (originalWidth > originalHeight && originalWidth > width) {//如果宽度大的话根据宽度固定大小缩放
                    be = originalWidth / width
                } else if (originalWidth < originalHeight && originalHeight > height) {//如果高度高的话根据宽度固定大小缩放
                    be = originalHeight / height
                }
                if (be <= 0)
                    be = 1f

                //比例压缩
                val bitmapOptions = BitmapFactory.Options()
                bitmapOptions.inSampleSize = be.toInt()//设置缩放比例
                bitmapOptions.inDither = true
                bitmapOptions.inPreferredConfig = Bitmap.Config.ARGB_8888

                val baos = ByteArrayOutputStream()
                //质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
                bitmap!!.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                val input = ByteArrayInputStream(baos.toByteArray())

                bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions)

                return bitmap//再进行质量压缩
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return null
        }

        /**
         * 通过质量压缩bitmap
         * @param image
         * @return
         */
        fun compressImageByQuality(image: Bitmap?, size: Int): Bitmap? {
            var image = image
            val baos = ByteArrayOutputStream()
            //质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
            image?.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            var options = 100
            //循环判断如果压缩后图片是否大于1000kb,大于继续压缩
            while (baos.toByteArray().size / 1024 > size) {
                baos.reset()//重置baos即清空baos
                //第一个参数 ：图片格式 ，第二个参数： 图片质量，100为最高，0为最差  ，第三个参数：保存压缩后的数据的流
                image?.compress(Bitmap.CompressFormat.JPEG, options, baos)//这里压缩options%，把压缩后的数据存放到baos中
                options -= 10//每次都减少10
            }
            //把压缩后的数据baos存放到ByteArrayInputStream中
            val isBm = ByteArrayInputStream(baos.toByteArray())
            //把ByteArrayInputStream数据生成图片
            val bitmap = BitmapFactory.decodeStream(isBm, null, null)

            if (image != null && !image.isRecycled) {
                image.recycle()
                image = null
            }
            try {
                baos.close()
                isBm.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            return bitmap
        }

        fun zoomImage(bigmage: Bitmap): Bitmap {
            // 获取这个图片的宽和高
            val width = bigmage.width.toFloat()
            val height = bigmage.height.toFloat()
            val maxSize = Math.max(width, height)
            val scale: Float
            var newWidth = 1920.0
            var newHeight = 1080.0
            if (maxSize > 1920) {
                if (width > height) {
                    scale = width / height
                    if (scale > 1.5f) {
                        newWidth = 1920.0
                        newHeight = 1080.0
                    } else if (scale > 1.0f) {
                        newWidth = 1440.0
                        newHeight = 1080.0
                    }
                } else {
                    scale = height / width
                    if (scale > 1.5f) {
                        newWidth = 1080.0
                        newHeight = 1920.0
                    } else if (scale > 1.0f) {
                        newWidth = 1080.0
                        newHeight = 1440.0
                    }
                }
            } else {
                return bigmage
            }
            // 创建操作图片用的matrix对象
            val matrix = Matrix()
            // 计算宽高缩放率
            val scaleWidth = newWidth.toFloat() / width
            val scaleHeight = newHeight.toFloat() / height
            // 缩放图片动作
            matrix.postScale(scaleWidth, scaleHeight)
            return Bitmap.createBitmap(
                bigmage, 0, 0, width.toInt(),
                height.toInt(), matrix, true
            )
        }

        /**
         * 获取照片旋转角度
         * @param filepath
         * @return
         */
        fun getExifOrientation(filepath: String): Int {
            var degree = 0
            var exif: ExifInterface? = null
            try {
                exif = ExifInterface(filepath)
            } catch (ex: IOException) {
                LogUtils.d("cannot read exif$ex")
            }

            if (exif != null) {
                val orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1)
                if (orientation != -1) {
                    when (orientation) {
                        ExifInterface.ORIENTATION_ROTATE_90 -> degree = 90
                        ExifInterface.ORIENTATION_ROTATE_180 -> degree = 180
                        ExifInterface.ORIENTATION_ROTATE_270 -> degree = 270
                    }
                }
            }
            return degree
        }

        /**
         * 按正方形裁切图片
         */
        fun ImageCrop(bitmap: Bitmap?, rect: Rect): Bitmap? {
            if (bitmap == null) {
                return null
            }
            val top = rect.top
            val left = rect.left  //取左上角
            val bottom = rect.bottom
            val right = rect.right
            val scaleW = right - left
            val scaleH = bottom - top

            return Bitmap.createBitmap(
                bitmap, left, top, scaleW, scaleH, null,
                false
            )

        }


        /**
         * 模糊图像
         * @param bitmap
         * @param radius 模糊程度 0<radius></radius><=25
         * @param context
         * @return
         */
        fun blurBitmap(bitmap: Bitmap, radius: Float, context: Context): Bitmap {
            val rs = RenderScript.create(context)
            //Create allocation from Bitmap bitmap中的数据装填
            val allocation = Allocation.createFromBitmap(rs, bitmap)
            val t = allocation.type
            //Create allocation with the same type 与第一个allocation的大小和type都相同多2D数组
            val blurredAllocation = Allocation.createTyped(rs, t)
            //Create script
            val blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs))

            //Set blur radius (maximum 25.0)
            blurScript.setRadius(radius)
            //Set input for script
            blurScript.setInput(allocation)
            //Call script for output allocation
            blurScript.forEach(blurredAllocation)
            //Copy script result into bitmap
            blurredAllocation.copyTo(bitmap)
            //Destroy everything to free memory
            allocation.destroy()
            blurredAllocation.destroy()
            blurScript.destroy()
            rs.destroy()
            return bitmap
        }
    }

}

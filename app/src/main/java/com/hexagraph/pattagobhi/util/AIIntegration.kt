package com.hexagraph.pattagobhi.util

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.core.graphics.get
import androidx.core.graphics.scale
import java.io.FileInputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.sqrt
import kotlin.math.max
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.roundToInt

object AIIntegration {
//    private const val FACE_NET_MODEL_PATH = "face_net_512.tflite"
//    private const val ANTI_SPOOF_MODEL_PATH = "anti_spoof_model.tflite"
//    private const val MOBILE_NET_MODEL_PATH = "mobile_net.tflite"
//
//    const val FACE_NET_IMAGE_SIZE = 160
//    const val FACE_NET_EMBEDDING_SIZE = 512
//    const val MOBILE_NET_IMAGE_SIZE = 224
//
//    private const val IMAGE_MEAN = 128.0f
//    private const val IMAGE_STD = 128.0f
//    const val DEFAULT_SIMILARITY = 0.8f
//    var isRunning = false
//
//    private fun getInterceptor(path: String, context: Context): Interpreter {
//        val fileDescriptor = context.assets.openFd(path)
//        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
//        val fileChannel = inputStream.channel
//        val startOffset = fileDescriptor.startOffset
//        val declaredLength = fileDescriptor.declaredLength
//        val modelBuffer: MappedByteBuffer =
//            fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
//        return Interpreter(modelBuffer)
//    }
//
//    val Context.faceNetInterceptor get() = getInterceptor(FACE_NET_MODEL_PATH, this)
//    val Context.antiSpoofInterceptor get() = getInterceptor(ANTI_SPOOF_MODEL_PATH, this)
//    val Context.mobileNetInterceptor get() = getInterceptor(MOBILE_NET_MODEL_PATH, this)
//
//
//    //Prepare the input Bitmap for MobileFaceNet
//    fun preprocessBitmapForMobileFaceNet(
//        bitmap: Bitmap,
//        size: Int,
//        isModelQuantized: Boolean = false
//    ): Result<ByteBuffer> = runCatching {
//        val resizedBitmap = bitmap.scale(size, size)
//        val inputBuffer = ByteBuffer.allocateDirect(size * size * 3 * 4).apply {
//            order(ByteOrder.nativeOrder())
//        }
//        for (y in 0 until size) {
//            for (z in 0 until size) {
//                val pixelValue = resizedBitmap[z, y]
//                if (isModelQuantized) {
//                    // Quantized model
//                    inputBuffer.put((pixelValue shr 16 and 0xFF).toByte())
//                    inputBuffer.put((pixelValue shr 8 and 0xFF).toByte())
//                    inputBuffer.put((pixelValue and 0xFF).toByte())
//                } else {
//                    // Float model
//                    inputBuffer.putFloat(((pixelValue shr 16 and 0xFF) - IMAGE_MEAN) / IMAGE_STD)
//                    inputBuffer.putFloat(((pixelValue shr 8 and 0xFF) - IMAGE_MEAN) / IMAGE_STD)
//                    inputBuffer.putFloat(((pixelValue and 0xFF) - IMAGE_MEAN) / IMAGE_STD)
//                }
//            }
//        }
//        inputBuffer
//    }.onFailure {
//        Log.e("AIIntegration", it.message ?: "Error while preprocessing bitmap for MobileFaceNet")
//    }
//
//    // Calculate the cosine similarity between two embeddings
//    fun calculateCosineSimilarity(
//        embeddingBuffer1: ByteBuffer,
//        embeddingBuffer2: ByteBuffer
//    ): Result<Float> = runCatching {
//        var dotProduct = 0.0f
//        var norm1 = 0.0f
//        var norm2 = 0.0f
//
//        for (i in 0 until FACE_NET_EMBEDDING_SIZE) {
//            val value1 = embeddingBuffer1.getFloat(i * 4)
//            val value2 = embeddingBuffer2.getFloat(i * 4)
//            dotProduct += value1 * value2
//            norm1 += value1 * value1
//            norm2 += value2 * value2
//        }
//
//        norm1 = sqrt(norm1)
//        norm2 = sqrt(norm2)
//
//        dotProduct / (norm2 * norm1)
//    }.onFailure {
//        Log.e("AIIntegration", it.message ?: "Error while calculating cosine similarity")
//    }
//
//    fun calculateDistanceBtwEmbeddings(
//        embeddingBuffer1: ByteBuffer,
//        embeddingBuffer2: ByteBuffer
//    ): Result<Float> = runCatching {
//        var sum = 0.0f
//        for (i in 0 until FACE_NET_EMBEDDING_SIZE) {
//            val diff = embeddingBuffer1.getFloat(i * 4) - embeddingBuffer2.getFloat(i * 4)
//            sum += diff * diff
//        }
//        sqrt(sum.toDouble()).toFloat()
//    }.onFailure {
//        Log.e("AIIntegration", it.message ?: "Error while calculating distance between embeddings")
//    }
}

    fun getCurrentTime(): String {
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        return current.format(formatter)
    }

    fun calculateHalfLife(nReviews: Int, difficulty: Double): Double {
        // Step 1: Initial half-life (based on difficulty)
        val minInitial = 15 * 60          // 15 minutes in seconds
        val maxInitial = 12 * 3600        // 12 hours in seconds
        val initialHalfLife = maxInitial - (maxInitial - minInitial) * difficulty

        // Step 2: Multiplier logic based on review count and difficulty
        val multiplier = if (nReviews < 10) {
            when {
                difficulty <= 0.3 -> 1.3
                difficulty <= 0.7 -> 1.1
                else -> 1.05
            }
        } else {
            when {
                difficulty <= 0.3 -> 1.8
                difficulty <= 0.7 -> 0.95
                else -> 0.85
            }
        }

        // Step 3: Apply growth (starting from review #2)
        val effectiveReviews = max(nReviews - 1, 0)
        val halfLifeSeconds = initialHalfLife * multiplier.pow(effectiveReviews)

        // Step 4: Clamp between 15 minutes and 30 days
        val minSeconds = 15 * 60
        val maxSeconds = 30 * 24 * 3600

        return max(min(halfLifeSeconds, maxSeconds.toDouble()), minSeconds.toDouble())
    }

    fun getNextReviewTime(nReviews: Int, difficulty: Double): String {
        val currentTime = LocalDateTime.now()
        val halfLifeSeconds = calculateHalfLife(nReviews, difficulty).toLong()

        val nextReviewTime = currentTime.plusSeconds(halfLifeSeconds)
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        return nextReviewTime.format(formatter)
    }

    fun formatTime(seconds: Double): String {
        return when {
            seconds < 60 -> {
                // Less than 60 seconds: show in seconds.
                "$seconds second${if (seconds != 1.0) "s" else ""}"
            }

            seconds < 3600 -> {
                // Less than 3600 seconds (1 hour): convert to minutes.
                // Rounding to nearest minute.
                val minutes = (seconds / 60.0).roundToInt()
                "$minutes minute${if (minutes != 1) "s" else ""}"
            }

            seconds < 86400 -> {
                // Less than 86400 seconds (1 day): convert to hours.
                // Rounding to nearest hour.
                val hours = (seconds / 3600.0).roundToInt()
                "$hours hour${if (hours != 1) "s" else ""}"
            }

            else -> {
                // One day or more: convert to days.
                // Rounding to nearest day.
                val days = (seconds / 86400.0).roundToInt()
                "$days day${if (days != 1) "s" else ""}"
            }
        }
    }

package com.hexagraph.pattagobhi.service

import android.graphics.Bitmap
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import com.hexagraph.pattagobhi.BuildConfig

class GeminiService {
    private val generativeModel = GenerativeModel(
        modelName = "gemini-1.5-flash",
        apiKey = BuildConfig.apiKey
    )

    suspend fun generateContent(
        bitmap: Bitmap? = null,
        prompt: String
    ): String? {
        return try {
            val response = generativeModel.generateContent(
                content {
                    if(bitmap != null)
                    image(bitmap)
                    text(prompt)
                }
            )
            response.text
        } catch (e: Exception) {
            throw e
        }
    }
}
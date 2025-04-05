package com.hexagraph.pattagobhi.Entity

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hexagraph.pattagobhi.util.Review

class Converters {

    private val gson = Gson()

    @TypeConverter
    fun fromReview(review: Review): String {
        return review.name
    }

    @TypeConverter
    fun toReview(value: String): Review {
        return Review.valueOf(value)
    }

    @TypeConverter
    fun fromStringList(list: List<String>): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun toStringList(json: String): List<String> {
        val type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(json, type)
    }
}
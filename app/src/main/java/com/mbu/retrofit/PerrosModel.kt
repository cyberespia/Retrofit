package com.mbu.retrofit

import com.google.gson.annotations.SerializedName

data class PerrosModel(var status: String, @SerializedName("message") var images: List<String>)

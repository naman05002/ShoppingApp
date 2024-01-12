package com.example.shopping.model

import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
@Parcelize
data class BaseClass(
    @SerializedName("data") val data: Data?= null,
    @SerializedName("error") val error: String? = null,
    @SerializedName("status") val status: String? = null
) : Parcelable

@Parcelize
data class Data(
    @SerializedName("items") val item: List<ShoppingItem>? = null
) : Parcelable

@Parcelize
data class ShoppingItem (
    @SerializedName("name") val name: String? = null,
    @SerializedName("price") val price: String? =null,
    @SerializedName("image") val image: String? =null,
    @SerializedName("extra") val extra: String? =null,
    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 0
): Parcelable
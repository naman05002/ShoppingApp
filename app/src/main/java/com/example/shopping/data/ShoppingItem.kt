package com.example.shopping.data
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "shoppingitem")
data class ShoppingItem (
    @SerializedName("name") val name: String? = null,
    @SerializedName("price") val price: String? =null,
    @SerializedName("image") val image: String? =null,
    @SerializedName("extra") val extra: String? =null,
    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 0
)
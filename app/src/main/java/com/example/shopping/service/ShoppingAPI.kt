package com.example.shopping.service

import com.example.shopping.model.BaseClass
import com.example.shopping.model.ShoppingItem
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface ShoppingAPI{
    @GET("")
    abstract fun getData(): Call<BaseClass>
}
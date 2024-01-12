package com.example.shopping.ui.home

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopping.MainActivity
import com.example.shopping.data.ShoppingItemDatabase
import com.example.shopping.model.BaseClass
import com.example.shopping.model.ShoppingItem
import com.example.shopping.service.ShoppingAPI
import com.example.shopping.utils.CustomSharedPreferences
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeViewModel : ViewModel() {

    private val _items = MutableLiveData<ArrayList<ShoppingItem>?>()
    val items get() = _items
    private var customPreferences = CustomSharedPreferences()
    private var refreshTime = 0.01 * 60 * 1000 * 1000 * 1000L
    fun getData(context: Context) {
        val updateTime = customPreferences.getTime()
        //Log.i(TAG, "$refreshTime  getData: "+(System.nanoTime() - updateTime!!))
        if (updateTime != null && updateTime != 0L && System.nanoTime() - updateTime < refreshTime) {
            getDataFromSQLite(context)
            Toast.makeText(context,"Products From SQLite", Toast.LENGTH_LONG).show()
        }else{
            getDataFromAPI(context)
            Toast.makeText(context,"Products From API", Toast.LENGTH_LONG).show()
        }
    }


    private fun getDataFromAPI(context: Context){
        val retrofit = Retrofit
            .Builder()
            .baseUrl(MainActivity.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(ShoppingAPI::class.java)
        val call = service.getData()
        call.enqueue(object : Callback<BaseClass> {
            override fun onFailure(call: Call<BaseClass>, t: Throwable) {
                t.printStackTrace()
            }
            override fun onResponse(call: Call<BaseClass>, response: Response<BaseClass>) {

                response.body()?.let {
                    println("onResponse is worked")
                    it.data?.item?.let { it1 -> storeInSQLite(context, it1 as ArrayList<ShoppingItem>) }
                    _items.postValue(it.data?.item as ArrayList<ShoppingItem>?)
                }
            }
        })
    }
    fun storeInSQLite(context: Context, products: ArrayList<ShoppingItem>) {
        println("storeInSQLLite")
        viewModelScope.launch {
            val productDb = ShoppingItemDatabase.invoke(context).shoppingItemDao()
            productDb.deleteAllRecords()
            products.forEach {
                val aProduct = com.example.shopping.data.ShoppingItem(it.name,it.price,it.image,it.extra)
                productDb.insertEntity(aProduct)
            }
        }
        customPreferences.saveTime(System.nanoTime())
    }
    private fun getDataFromSQLite(context: Context) {
        viewModelScope.launch {
            val productDb = ShoppingItemDatabase(context).shoppingItemDao().getAllRecords()
            val products1 = arrayListOf<ShoppingItem>()
            productDb.forEach{
                val list = ShoppingItem(it.name,it.price,it.image,it.extra)
                products1.add(list)
            }
            Toast.makeText(context,"Products From SQLite",Toast.LENGTH_LONG).show()
            _items.postValue(products1)
        }
    }
}
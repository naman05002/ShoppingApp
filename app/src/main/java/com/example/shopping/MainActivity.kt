package com.example.shopping
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.shopping.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.json.JSONException
import org.json.JSONObject


class ShoppingList(val name: String, val image_url: String, val price: String, val extra: String) {}

fun parseJsonResponse(jsonResponse: String) : ArrayList<ShoppingList> {
    try {
        val jsonObject = JSONObject(jsonResponse)
        val status = jsonObject.getString("status")

        if ("success" == status) {
            val dataObject = jsonObject.getJSONObject("data")
            val itemsArray = dataObject.getJSONArray("items")

            val items = Array(itemsArray.length()) { index ->
                val itemObject = itemsArray.getJSONObject(index)

                val name = itemObject.getString("name")
                val price = itemObject.getString("price")
                val image = itemObject.getString("image")
                val extra = itemObject.optString("extra")

                // Create an instance of YourItemClass and store it in the array
                ShoppingList(name, image, price, extra)
            }
            return items.toCollection(ArrayList())
        } else {
            return arrayListOf()
        }
    } catch (e: JSONException) {
        e.printStackTrace()
        // Handle JSON parsing errors
        return arrayListOf()
    }
}



class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var shoppingArray: ArrayList<ShoppingList>
    companion object {
        const val BASE_URL = "https://run.mocky.io/v3/7181ed68-194b-4509-9889-7513e29345d4"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

    }

}

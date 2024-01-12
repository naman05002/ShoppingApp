package com.example.shopping.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.shopping.R
import com.example.shopping.ShoppingList
import com.example.shopping.adapter.ShoppingAdapter
import com.example.shopping.adapter.ShoppingAdapterCard
import com.example.shopping.databinding.FragmentDashboardBinding
import com.example.shopping.parseJsonResponse

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var shoppingArray: ArrayList<ShoppingList>
    private lateinit var adapter: ShoppingAdapterCard

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textDashboard
//        dashboardViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        // Lookup the recyclerview in activity layout

        val rvShoppingList = root.findViewById<View>(R.id.rvShoppingCard) as RecyclerView
        //Fetch data
        val searchView = root.findViewById(R.id.search_view_card) as SearchView


        val url = "https://run.mocky.io/v3/7181ed68-194b-4509-9889-7513e29345d4"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            { response ->
                Log.v("jsonres","hey")
                shoppingArray = parseJsonResponse(response.toString())
                adapter = ShoppingAdapterCard(shoppingArray)
                rvShoppingList.adapter = adapter
                val layoutManager = GridLayoutManager(context, 3)
                rvShoppingList.layoutManager = layoutManager;


            },
            { error ->
                Log.v("errorRequest", error.toString())
            }
        )
        Volley.newRequestQueue(context).add(jsonObjectRequest)

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                adapter.filter.filter(query)

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                Log.v("querytext","newText")
                return true
            }

        })

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
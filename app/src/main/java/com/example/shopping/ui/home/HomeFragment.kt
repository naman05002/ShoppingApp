package com.example.shopping.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.shopping.R
import com.example.shopping.ShoppingList
import com.example.shopping.adapter.ShoppingAdapter
import com.example.shopping.databinding.FragmentHomeBinding
import com.example.shopping.parseJsonResponse

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var shoppingArray: ArrayList<ShoppingList>
    private lateinit var adapter: ShoppingAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]
//        homeViewModel.getData(requireContext())
//        homeViewModel.items.observe(viewLifecycleOwner,androidx.lifecycle.Observer { items ->
//            items.let {
//                adapter = items?.let {it1 ->
//                    Log.v("hey",it1.toString())
//                    ShoppingAdapter(it1.toList())
//                }!!
//
//            }
//        })
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        // Lookup the recyclerview in activity layout
        val rvShoppingList = root.findViewById<View>(R.id.rvShoppingList) as RecyclerView
        //Fetch data


        //search
        val searchView = root.findViewById(R.id.search_view) as SearchView




        val url = "https://run.mocky.io/v3/7181ed68-194b-4509-9889-7513e29345d4"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            { response ->
                Log.v("jsonres","hey")
                shoppingArray = parseJsonResponse(response.toString())
                adapter = ShoppingAdapter(shoppingArray)
                rvShoppingList.adapter = adapter
                rvShoppingList.layoutManager = LinearLayoutManager(context)
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
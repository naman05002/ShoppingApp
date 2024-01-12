package com.example.shopping.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shopping.R
import com.example.shopping.ShoppingList
import com.example.shopping.model.ShoppingItem

class ShoppingAdapter(private var mShopping: List<ShoppingList>) : RecyclerView.Adapter<ShoppingAdapter.ViewHolder>(), Filterable {
    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access

    private val initialList: List<ShoppingList> = mShopping
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Your holder should contain and initialize a member variable
        // for any view that will be set as you render a row
        val nameView = itemView.findViewById<TextView>(R.id.name)
        val priceView = itemView.findViewById<TextView>(R.id.price)
        val extraView = itemView.findViewById<TextView>(R.id.extra)
        val itemImageView = itemView.findViewById<ImageView>(R.id.cardImageView)
    }
    
    // ... constructor and member variables
    // Usually involves inflating a layout from XML and returning the holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val contactView = inflater.inflate(R.layout.display_row, parent, false)
        // Return a new holder instance
        return ViewHolder(contactView)
    }
    // Involves populating data into the item through holder
    override fun onBindViewHolder(viewHolder: ShoppingAdapter.ViewHolder, position: Int) {
        // Get the data model based on position
        val shoppingList: ShoppingList = mShopping.get(position)
        // Set item views based on your views and data model
        val textView = viewHolder.nameView
        textView.text = shoppingList.name
        val textView2 = viewHolder.priceView
        textView2.text = shoppingList.price
        val textView3 = viewHolder.extraView
        textView3.text = if(shoppingList.extra!="null") shoppingList.extra else ""
        val imageView = viewHolder.itemImageView
        Glide.with(imageView.context).load(shoppingList.image_url).into(imageView)
    }
    // Returns the total count of items in the list
    override fun getItemCount(): Int {
        return mShopping.size
    }

    override fun getFilter(): Filter {
        return object: Filter(){
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val query = p0.toString().lowercase()
                mShopping = initialList.toList()
                val filtered = mShopping.filter {
                    it.name?.lowercase()?.contains(query)!!
                }
                val results = FilterResults()
                results.values = filtered
                Log.v("querytext2",results.toString())
                return results
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {

                mShopping = p1?.values as? List<ShoppingList> ?: emptyList()
                notifyDataSetChanged()
            }

        }
    }
}
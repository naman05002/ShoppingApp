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

class ShoppingAdapterCard (private var mShopping: List<ShoppingList>) : RecyclerView.Adapter<ShoppingAdapterCard.ViewHolder>() , Filterable{
    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access

    private val initialList: List<ShoppingList> = mShopping.toList()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Your holder should contain and initialize a member variable
        // for any view that will be set as you render a row
        val cardName = itemView.findViewById<TextView>(R.id.cardName)
        val cardImageView = itemView.findViewById<ImageView>(R.id.cardImageView)
        val cardPrice = itemView.findViewById<TextView>(R.id.cardPrice)
    }

    // ... constructor and member variables
    // Usually involves inflating a layout from XML and returning the holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingAdapterCard.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val contactView = inflater.inflate(R.layout.display_card, parent, false)
        // Return a new holder instance
        return ViewHolder(contactView)
    }
    // Involves populating data into the item through holder
    override fun onBindViewHolder(viewHolder: ShoppingAdapterCard.ViewHolder, position: Int) {
        // Get the data model based on position
        val shoppingList: ShoppingList = mShopping[position]
        // Set item views based on your views and data model
        val textView = viewHolder.cardName
        textView.text = shoppingList.name
        val textView2 = viewHolder.cardPrice
        textView2.text = shoppingList.price
        val imageView = viewHolder.cardImageView
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
                    it.name.lowercase().contains(query)
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
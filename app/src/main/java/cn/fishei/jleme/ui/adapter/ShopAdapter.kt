package cn.fishei.jleme.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cn.fishei.jleme.JilemeApplication.Companion.context
import cn.fishei.jleme.R
import cn.fishei.jleme.database.Database
import cn.fishei.jleme.model.ShopRecourse
import com.bumptech.glide.Glide

class ShopAdapter(val shopList: ArrayList<ShopRecourse>) :
    RecyclerView.Adapter<ShopAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val photo: ImageView = view.findViewById(R.id.shop_photo)
        val name: TextView = view.findViewById(R.id.shop_name)
        val number: TextView = view.findViewById(R.id.shop_number)
        val price: TextView = view.findViewById(R.id.shop_price)
        val deletePhote: ImageView = view.findViewById(R.id.delete_good)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.shopping_cart_recyclerview, parent, false)
        val viewHolder = ViewHolder(view)
        viewHolder.deletePhote.setOnClickListener {
            Database.dao.deleteShopResource(shopList.removeAt(viewHolder.adapterPosition));
            notifyItemRemoved(viewHolder.adapterPosition)
            notifyItemRangeChanged(viewHolder.adapterPosition, itemCount)
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val shop = shopList[position]

        Glide.with(context).load(shop.photo).into(holder.photo)
        holder.name.text = shop.name
        holder.number.text = shop.number.toString()
        holder.price.text = shop.price.toString()

    }

    override fun getItemCount(): Int {
        return shopList.size
    }


}
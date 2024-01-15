package cn.fishei.jleme.ui.adapter

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.DialogInterface.OnClickListener
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import cn.fishei.jleme.JilemeApplication.Companion.context
import cn.fishei.jleme.R
import cn.fishei.jleme.database.Database
import cn.fishei.jleme.model.Goods
import cn.fishei.jleme.model.ShopRecourse
import com.bumptech.glide.Glide

class MenuAdapter(private val goodsList: ArrayList<Goods>) :
    RecyclerView.Adapter<MenuAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val pic: ImageView = view.findViewById(R.id.goods_show_pic)
        val name: TextView = view.findViewById(R.id.goods_show_name)
        val price: TextView = view.findViewById(R.id.goods_show_price)
        val description: TextView = view.findViewById(R.id.goods_show_desc)
        val sales: TextView = view.findViewById(R.id.goods_show_sales)
        val tags: TextView = view.findViewById(R.id.goods_show_tags)
        val goodsId: TextView = view.findViewById(R.id.goods_id)
        val addPic: ImageView = view.findViewById(R.id.goods_add_pic)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.menu_item, parent, false)
        val viewHolder = ViewHolder(view)
        viewHolder.addPic.setOnClickListener {
            val position = viewHolder.adapterPosition
            val goods = goodsList[position]
            Database.dao.apply {
                val shopRecourse: ShopRecourse? = getMyShopByName(goods.name)
                if (shopRecourse == null) {
                    Database.dao.addShopResource(ShopRecourse().apply {
                        this.price = goods.price.toDouble()
                        this.name = goods.name;
                        this.photo = goods.pic;
                        this.number = 1;
                        this.goodsId = goods.id;
                    })
                } else {
                    shopRecourse.number += shopRecourse.number;
                    updateShopResource(shopRecourse);
                }
            }
        }
        viewHolder.itemView.setOnLongClickListener {
            AlertDialog.Builder(parent.context)
                .setTitle("notice")
                .setMessage("Are you sure you want to delete？")
                .setPositiveButton("SURE", object : OnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        Database.dao.deleteGoods(goodsList[viewHolder.adapterPosition])
                        goodsList.removeAt(viewHolder.adapterPosition)
                        notifyDataSetChanged()
                    }
                }).setNegativeButton("CANCEL", null)
                .show()
            true
        }
        return viewHolder
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val goods = goodsList[position]

        Glide.with(context).load(goods.pic).into(holder.pic)
        Log.d("图片地址：", goods.pic)
        holder.name.text = goods.name
        holder.price.text = "￥${goods.price}"
        holder.description.text = goods.description

    }

    override fun getItemCount() = goodsList.size
}
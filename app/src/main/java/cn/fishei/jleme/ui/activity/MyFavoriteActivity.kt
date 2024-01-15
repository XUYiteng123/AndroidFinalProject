package cn.fishei.jleme.ui.activity

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.fishei.jleme.R
import cn.fishei.jleme.database.Database
import cn.fishei.jleme.model.Favorite
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_me_order.*
import kotlinx.android.synthetic.main.activity_me_order.order_recyclerView
import kotlinx.android.synthetic.main.activity_my_favorite.*

class MyFavoriteActivity() : AppCompatActivity(R.layout.activity_my_favorite) {
    private val orderList: ArrayList<Favorite> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val layoutManager = LinearLayoutManager(this)
        order_recyclerView.layoutManager = layoutManager
        val adapter = OrderAdapter(orderList)
        order_recyclerView.adapter = adapter
        orderList.addAll(Database.dao.getFavorite())
        adapter.notifyDataSetChanged()
        iv_add.setOnClickListener {
            startActivity(Intent(this,AddFavoriteActivity::class.java))
        }

    }


    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()
        orderList.clear()
        val adapter = OrderAdapter(orderList)
        order_recyclerView.adapter = adapter
        orderList.addAll(Database.dao.getFavorite())
        adapter.notifyDataSetChanged()
    }

    private class OrderAdapter(val orderList: ArrayList<Favorite>) :
        RecyclerView.Adapter<OrderViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.item_map, parent, false)
            return OrderViewHolder(view)
        }

        @SuppressLint("SetTextI18n")
        override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
            Glide.with(holder.ivImage).load(orderList[position].image).into(holder.ivImage)
            holder.tvAddress.text ="${orderList[position].name}\n${ orderList[position].address}"
            holder.itemView.setOnClickListener {
                it.context.startActivity(Intent(it.context,MapActivity::class.java).putExtra("latLng",orderList[position].getLatLngData()))
            }

            holder.itemView.setOnLongClickListener {
                AlertDialog.Builder(holder.itemView.context)
                    .setTitle("notice")
                    .setMessage("Are you sure you want to delete？")
                    .setPositiveButton("SURE", object : DialogInterface.OnClickListener {
                        override fun onClick(dialog: DialogInterface?, which: Int) {
                            Database.dao.deleteFavorite(orderList.removeAt(holder.adapterPosition))
                            notifyDataSetChanged()
                        }
                    }).setNegativeButton("CANCEL", null)
                    .show()

                true
            }
        }

        override fun getItemCount(): Int {
            return orderList.size
        }

    }

    private class OrderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivImage: ImageView = view.findViewById(R.id.iv_image)
        val tvAddress: TextView = view.findViewById(R.id.tv_address)
    }


}
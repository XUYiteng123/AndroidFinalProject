package cn.fishei.jleme.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.fishei.jleme.model.FoodInfo
import cn.fishei.jleme.model.Order
import cn.fishei.jleme.R
import cn.fishei.jleme.database.Database
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_me_order.*

class MeOrderFragment() : Fragment(R.layout.activity_me_order) {
    private val orderList: ArrayList<Order> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(requireActivity())
        order_recyclerView.layoutManager = layoutManager
        val adapter = OrderAdapter(orderList)
        order_recyclerView.adapter = adapter
        orderList.addAll(Database.dao.getOrder())
        adapter.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
        orderList.clear()
        val layoutManager = LinearLayoutManager(requireActivity())
        order_recyclerView.layoutManager = layoutManager
        val adapter = OrderAdapter(orderList)
        order_recyclerView.adapter = adapter
        orderList.addAll(Database.dao.getOrder())
        adapter.notifyDataSetChanged()
    }

    private class OrderAdapter(val orderList: ArrayList<Order>) :
        RecyclerView.Adapter<OrderViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.item_order, parent, false)
            return OrderViewHolder(view)
        }

        override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
            holder.apply {
                recyclerView.adapter = FoodAdapter(orderList[position].getFoodList())
                tvDate.text = orderList[position].date
            }
        }

        override fun getItemCount(): Int {
            return orderList.size
        }

    }

    private class OrderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val recyclerView: RecyclerView = view.findViewById(R.id.rv_food)
        val tvDate: TextView = view.findViewById(R.id.tv_date)
    }

    private class FoodViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.food_image)
        val name: TextView = view.findViewById(R.id.food_name)
        val state: TextView = view.findViewById(R.id.food_state)
        val price: TextView = view.findViewById(R.id.food_price)
        val number: TextView = view.findViewById(R.id.food_number)
    }

    private class FoodAdapter(val foodList: List<FoodInfo>) :
        RecyclerView.Adapter<FoodViewHolder>() {
        override fun getItemCount(): Int {
            return foodList.size
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.recyclerview_order, parent, false)

            return FoodViewHolder(view)
        }

        @SuppressLint("SetTextI18n")
        override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
            val info = foodList[position]
            Glide.with(holder.itemView).load(info.image).into(holder.image)
            holder.name.text = info.name
            holder.state.text = info.food_state
            holder.price.text = "$" + info.price
            holder.number.text = "x" + info.number
        }
    }


}
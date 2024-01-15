package cn.fishei.jleme.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import cn.fishei.jleme.R
import cn.fishei.jleme.database.Database
import cn.fishei.jleme.model.FoodInfo
import cn.fishei.jleme.model.Order
import cn.fishei.jleme.model.ShopRecourse
import cn.fishei.jleme.ui.adapter.ShopAdapter
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_menu.*
import java.text.SimpleDateFormat


class MenuActivity : AppCompatActivity() {


    private val shopList = ArrayList<ShopRecourse>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_menu)
        shop_button.setOnClickListener {
            if (shopList.isNotEmpty()) {
                Database.dao.addOrder(Order().apply {
                    val foodInfoList = ArrayList<FoodInfo>()
                    for (shopRecourse in shopList) {
                        foodInfoList.add(
                            FoodInfo(
                                shopRecourse.photo,
                                shopRecourse.name,
                                "",
                                shopRecourse.price.toString(),
                                shopRecourse.number.toString()
                            )
                        )
                    }
                    this.foodInfoList = Gson().toJson(foodInfoList);
                    this.date =
                        SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis())
                })
                shopList.clear()

                shoppingId.adapter?.notifyDataSetChanged()
                Database.dao.deleteShopResource(shopList)
            }
        }
    }


    override fun onResume() {
        super.onResume()
        shopList.clear()
        shopList.addAll(Database.dao.getMyShop())
        val layoutManager = LinearLayoutManager(this)
        shoppingId.layoutManager = layoutManager
        val adapter = ShopAdapter(shopList)
        shoppingId.adapter = adapter
        if (shopList.isNotEmpty()) {
            menu_is_goods.visibility = View.GONE
        } else {
            menu_is_goods.visibility = View.VISIBLE
        }
        shop_all_price.text = shopList.map {
            it.price * it.number
        }.sum().toString()


    }


}

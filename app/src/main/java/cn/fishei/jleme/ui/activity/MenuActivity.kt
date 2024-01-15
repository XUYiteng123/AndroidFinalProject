package cn.fishei.jleme.ui.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.recyclerview.widget.GridLayoutManager
import cn.fishei.jleme.model.Goods
import cn.fishei.jleme.R
import cn.fishei.jleme.ui.adapter.MenuAdapter
import cn.fishei.jleme.database.Database
import kotlinx.android.synthetic.main.activity_menu.*

class MenuActivity : BaseActivity() {

    private lateinit var adapter: MenuAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        tvBox.setOnClickListener {
            startActivity(Intent(this,cn.fishei.jleme.ui.fragment.MenuActivity::class.java))
        }
        getMenuData("1")

        search_cancel.setOnClickListener { onBackPressed() }

        //设置显示商品
        category_out_pot.setOnClickListener {
            getMenuData("2")
            category_pot.setBackgroundColor(Color.parseColor("#ffffff"))
            category_homely.setBackgroundColor(Color.parseColor("#F2F3F8"))
            category_snack.setBackgroundColor(Color.parseColor("#F2F3F8"))
            category_drink.setBackgroundColor(Color.parseColor("#F2F3F8"))
        }

        category_homely.setOnClickListener {
            getMenuData("1")
            category_pot.setBackgroundColor(Color.parseColor("#F2F3F8"))
            category_homely.setBackgroundColor(Color.parseColor("#ffffff"))
            category_snack.setBackgroundColor(Color.parseColor("#F2F3F8"))
            category_drink.setBackgroundColor(Color.parseColor("#F2F3F8"))
        }

        category_snack.setOnClickListener {
            getMenuData("3")
            category_pot.setBackgroundColor(Color.parseColor("#F2F3F8"))
            category_homely.setBackgroundColor(Color.parseColor("#F2F3F8"))
            category_snack.setBackgroundColor(Color.parseColor("#ffffff"))
            category_drink.setBackgroundColor(Color.parseColor("#F2F3F8"))
        }

        category_drink.setOnClickListener {
            getMenuData("4")
            category_pot.setBackgroundColor(Color.parseColor("#F2F3F8"))
            category_homely.setBackgroundColor(Color.parseColor("#F2F3F8"))
            category_snack.setBackgroundColor(Color.parseColor("#F2F3F8"))
            category_drink.setBackgroundColor(Color.parseColor("#ffffff"))
        }

        search_edit_text.setOnKeyListener { v, keyCode, event ->
            category_pot.setBackgroundColor(Color.parseColor("#F2F3F8"))
            category_homely.setBackgroundColor(Color.parseColor("#F2F3F8"))
            category_snack.setBackgroundColor(Color.parseColor("#F2F3F8"))
            category_drink.setBackgroundColor(Color.parseColor("#F2F3F8"))
            Log.d("输出搜索", search_edit_text.text.toString())
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                Log.d("点击搜索", "开始搜索了")
                getSearchData(search_edit_text.text.toString())
            }
            return@setOnKeyListener false
        }
        tvAddFood.setOnClickListener {
            startActivity(Intent(this, AddFoodActivity::class.java))
        }
    }

    private fun getMenuData(id: String) {
        val goodsList = ArrayList<Goods>()
        val layoutManager = GridLayoutManager(this, 2)
        menu_lists.layoutManager = layoutManager
        adapter = MenuAdapter(goodsList)
        menu_lists.adapter = adapter
        category_homely.setBackgroundColor(Color.parseColor("#ffffff"))
        goodsList.clear()
        goodsList.addAll(Database.dao.getGoods(id))
        adapter.notifyDataSetChanged()
    }

    private fun getSearchData(key: String) {
        val goodsList = ArrayList<Goods>()
        val layoutManager = GridLayoutManager(this, 2)
        menu_lists.layoutManager = layoutManager
        adapter = MenuAdapter(goodsList)
        menu_lists.adapter = adapter
        goodsList.clear()
        goodsList.addAll(Database.dao.getGoodsByKey(key))
        adapter.notifyDataSetChanged()

    }
}
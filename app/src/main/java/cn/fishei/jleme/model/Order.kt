package cn.fishei.jleme.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Entity
class Order {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var foodInfoList: String = ""
    var date :String = ""
    fun getFoodList(): List<FoodInfo > {
        return Gson().fromJson(foodInfoList,object : TypeToken<List<FoodInfo>>() {}.type)
    }
}
package cn.fishei.jleme.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class ShopRecourse {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var goodsId: Int = 0
    var number: Int = 0
    var price: Double = 0.0
    var name: String = ""
    var photo: String = ""
}
package cn.fishei.jleme.model

import androidx.room.Entity
import androidx.room.PrimaryKey

//商品
@Entity
class Goods {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var price: String = ""
    var name: String = ""
    var description: String = ""
    var pic: String = ""
    var categoryId: String = ""
}
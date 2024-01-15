package cn.fishei.jleme.model

import androidx.room.Entity
import androidx.room.PrimaryKey

// 用户信息
@Entity
class User {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0;
    var username: String = "";
    var password: String = ""
}
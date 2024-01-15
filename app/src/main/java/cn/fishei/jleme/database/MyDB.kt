package cn.fishei.jleme.database

import androidx.room.Database
import androidx.room.RoomDatabase
import cn.fishei.jleme.model.*

@Database(
    entities = [User::class, Order::class, Goods::class, Favorite::class, ShopRecourse::class],
    version = 1,
    exportSchema = false
)
abstract class MyDB : RoomDatabase() {
    abstract val dao: DatabaseDao
}
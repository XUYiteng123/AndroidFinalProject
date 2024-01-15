package cn.fishei.jleme.database

import androidx.room.*
import cn.fishei.jleme.model.*

@Dao
interface DatabaseDao {
    @Insert
    fun register(user: User?): Long

    @Update
    fun updateUserInfo(user: User?)

    @Query("select * from user where username=:username and password = :password")
    fun queryUser(username: String?, password: String?): User?

    @Query("select * from user where username=:username")
    fun queryUser(username: String?): User?

    @Query("select * from user where id=:id")
    fun queryUser(id: Int): User?

    @Query("select * from shoprecourse ")
    fun getMyShop(): List<ShopRecourse>

    @Insert
    fun add(apply: Goods)

    @Query("select * from goods where categoryId = :id")
    fun getGoods(id: String): List<Goods>

    @Query("select * from goods where name like '%'||:key||'%'")
    fun getGoodsByKey(key: String): List<Goods>

    @Insert
    fun addShopResource(apply: ShopRecourse)

    @Query("select * from shoprecourse where name = :name")
    fun getMyShopByName(name: String): ShopRecourse?

    @Update
    fun updateShopResource(shopRecourse: ShopRecourse)

    @Delete
    fun deleteShopResource(removeAt: ShopRecourse)

    @Query("select * from  `order`")
    fun getOrder(): List<Order>

    @Insert
    fun addOrder(apply: Order)

    @Delete
    fun deleteShopResource(removeAt: ArrayList<ShopRecourse>)

    @Delete
    fun deleteGoods(goods: Goods)

    @Insert
    fun addFavorite(apply: Favorite)

    @Query("select * from  favorite")
    fun getFavorite(): List<Favorite>

    @Delete
    fun deleteFavorite(removeAt: Favorite)

}
package cn.fishei.jleme.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.amap.api.maps.model.LatLng
@Entity
class Favorite {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var name: String = ""
    var address: String = ""
    var latLng: String = ""
    var image: String = ""

    fun getLatLngData(): LatLng {
        return LatLng(latLng.split(",")[0].toDouble(), latLng.split(",")[1].toDouble())
    }
}
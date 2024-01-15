package cn.fishei.jleme.ui.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import cn.fishei.jleme.R
import com.amap.api.maps.AMap
import com.amap.api.maps.AMap.OnMyLocationChangeListener
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.MapView
import com.amap.api.maps.MapsInitializer
import com.amap.api.maps.model.CameraPosition
import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.MarkerOptions
import com.amap.api.maps.model.MyLocationStyle
import com.amap.api.services.core.LatLonPoint
import com.amap.api.services.core.ServiceSettings
import com.amap.api.services.geocoder.GeocodeResult
import com.amap.api.services.geocoder.GeocodeSearch
import com.amap.api.services.geocoder.GeocodeSearch.OnGeocodeSearchListener
import com.amap.api.services.geocoder.RegeocodeQuery
import com.amap.api.services.geocoder.RegeocodeResult
import kotlinx.android.synthetic.main.activity_map.*


class MapActivity : AppCompatActivity() {
    lateinit var mMapView: MapView
    lateinit var aMap: AMap
    var latLng: LatLng? = null;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        setPrivacyAgaree()
        requestPermission()
        latLng = intent.getParcelableExtra("latLng")

        mMapView = mapView
        mMapView.onCreate(savedInstanceState)
        aMap = mMapView.getMap()
        aMap.setMapLanguage(AMap.ENGLISH)
        if (latLng != null) {
            val mCameraUpdate =
                CameraUpdateFactory.newCameraPosition(CameraPosition(latLng, 18f, 30f, 0f))
            aMap.moveCamera(mCameraUpdate)
            val marker = aMap.addMarker(
                MarkerOptions().position(latLng)
                    .snippet("DefaultMarker")
            )
        }else{
            val myLocationStyle =
                MyLocationStyle()

            myLocationStyle.strokeWidth(0f)
            myLocationStyle.strokeColor(Color.TRANSPARENT)
            myLocationStyle.radiusFillColor(Color.TRANSPARENT)
            myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE)

            myLocationStyle.interval(2000)

            aMap.myLocationStyle = myLocationStyle

            aMap.isMyLocationEnabled = true
        }


        requestPermission()
        aMap.addOnMarkerClickListener { true }
        aMap.setOnMapClickListener { latLng ->
            val geocoderSearch = GeocodeSearch(this);
            geocoderSearch.setOnGeocodeSearchListener(object : OnGeocodeSearchListener {
                override fun onRegeocodeSearched(p0: RegeocodeResult?, p1: Int) {
                    if (this@MapActivity.latLng === null) {
                        setResult(RESULT_OK, Intent().apply {
                            putExtra("address", p0?.regeocodeAddress?.formatAddress)
                            putExtra("latLng", "${latLng.latitude},${latLng.longitude}")
                        })
                        finish()
                    }
                }

                override fun onGeocodeSearched(p0: GeocodeResult?, p1: Int) {

                }
            });

            val query = RegeocodeQuery(
                LatLonPoint(latLng.latitude, latLng.longitude),
                200f,
                GeocodeSearch.AMAP
            )
            geocoderSearch.getFromLocationAsyn(query)
        }

        aMap.setOnMyLocationChangeListener(OnMyLocationChangeListener {
            aMap.isMyLocationEnabled = false
        })


    }

    private fun setPrivacyAgaree() {
        MapsInitializer.updatePrivacyShow(this, true, true)
        MapsInitializer.updatePrivacyAgree(this, true)
        ServiceSettings.updatePrivacyShow(this, true, true)
        ServiceSettings.updatePrivacyAgree(this, true)
    }


    override fun onDestroy() {
        super.onDestroy()
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState!!)
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState)
    }

    private fun requestPermission() {
        val permissions: MutableList<String> = ArrayList()
        permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION)
        permissions.add(Manifest.permission.READ_PHONE_STATE)
        permissions.add(Manifest.permission.ACCESS_NETWORK_STATE)
        permissions.add(Manifest.permission.ACCESS_WIFI_STATE)
        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION)
        permissions.add(Manifest.permission.CHANGE_WIFI_STATE)
        permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        permissions.add(Manifest.permission.WRITE_SETTINGS)
        permissions.add(Manifest.permission.WAKE_LOCK)
        permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        val iterator = permissions.iterator()
        while (iterator.hasNext()) {
            val next = iterator.next()
            if (ContextCompat.checkSelfPermission(
                    this, next
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                iterator.remove()
            }
        }
        if (permissions.isEmpty()) {
            return
        }
        ActivityCompat.requestPermissions(this, permissions.toTypedArray(), 100)
    }
}
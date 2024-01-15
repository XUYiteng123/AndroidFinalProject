package cn.fishei.jleme

import android.annotation.SuppressLint
import android.app.Application
import android.app.Service
import android.content.Context
import cn.fishei.jleme.database.Database
import com.amap.api.services.core.ServiceSettings

//提供一个全局获取context的方法，参考P615
class JilemeApplication : Application() {


    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        Database.init(this)
        ServiceSettings.getInstance().setLanguage(ServiceSettings.ENGLISH)
    }

}
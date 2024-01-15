package cn.fishei.jleme.database

import android.app.Application
import androidx.room.Room

object Database {
    private var db: MyDB? = null

    @JvmStatic
    fun init(application: Application?) {
        if (db == null) {
            db = Room.databaseBuilder(application!!, MyDB::class.java, "database2")
                .allowMainThreadQueries().build()
        }
    }

    @JvmStatic
    val dao: DatabaseDao
        get() = db!!.dao
}
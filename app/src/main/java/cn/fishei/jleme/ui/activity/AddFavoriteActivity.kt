package cn.fishei.jleme.ui.activity

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cn.fishei.jleme.R
import cn.fishei.jleme.database.Database
import cn.fishei.jleme.model.Favorite
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_add_favorite.*
import kotlinx.android.synthetic.main.activity_add_food.btnSubmit
import kotlinx.android.synthetic.main.activity_add_food.etName
import kotlinx.android.synthetic.main.activity_add_food.iv_image
import java.io.File
import java.io.FileOutputStream

class AddFavoriteActivity : AppCompatActivity() {
    private var image: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_favorite)
        iv_image.setOnClickListener {
            startActivityForResult(Intent(Intent.ACTION_GET_CONTENT).setType("image/*"), 100)
        }
        etAddress.setOnClickListener {
            startActivityForResult(Intent(this,MapActivity::class.java),101)
        }
        btnSubmit.setOnClickListener {
            val address = etAddress.text.toString()
            val name = etName.text.toString()
            if (address.isEmpty() || name.isEmpty() || image.isEmpty()) {
                return@setOnClickListener
            }
            Database.dao.addFavorite(Favorite().apply {
                this.name = name
                this.address = address
                this.latLng = this@AddFavoriteActivity.latLng
                this.image = this@AddFavoriteActivity.image
            })
            finish()
        }
    }

    private var latLng = "";
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == 100) {
                image = getImagePath(this, data?.data)
                Glide.with(this).load(image).into(iv_image)
            } else {
                latLng = data?.getStringExtra("latLng") ?: "";
                etAddress.text = data?.getStringExtra("address")
            }
        }

    }

    fun getImagePath(context: Context, uri: Uri?): String {
        try {
            val inputStream = context.contentResolver.openInputStream(uri!!)
            val bytes = ByteArray(inputStream!!.available())
            inputStream.read(bytes)
            val file =
                File(context.externalCacheDir, System.currentTimeMillis().toString() + ".jpg")
            val fileOutputStream = FileOutputStream(file)
            fileOutputStream.write(bytes)
            fileOutputStream.close()
            inputStream.close()
            return file.path
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }
}
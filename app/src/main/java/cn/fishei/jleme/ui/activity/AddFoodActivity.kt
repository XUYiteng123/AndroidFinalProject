package cn.fishei.jleme.ui.activity

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cn.fishei.jleme.model.Goods
import cn.fishei.jleme.R
import cn.fishei.jleme.database.Database
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_add_food.*
import java.io.File
import java.io.FileOutputStream

class AddFoodActivity : AppCompatActivity() {
    private var image: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_food)
        iv_image.setOnClickListener {
            startActivityForResult(Intent(Intent.ACTION_GET_CONTENT).setType("image/*"), 100)
        }
        btnSubmit.setOnClickListener {
            val description = etDetail.text.toString()
            val price = etPrice.text.toString()
            val name = etName.text.toString()
            if (description.isEmpty() || price.isEmpty() || name.isEmpty() || image.isEmpty()) {
                return@setOnClickListener
            }
            Database.dao.add(Goods().apply {
                this.name = name
                this.description = description
                this.price = price
                this.pic = image
                this.categoryId = (spinnerType.selectedItemPosition + 1).toString()
            })
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            image = getImagePath(this,data?.data)
            Glide.with(this).load(image).into(iv_image)
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
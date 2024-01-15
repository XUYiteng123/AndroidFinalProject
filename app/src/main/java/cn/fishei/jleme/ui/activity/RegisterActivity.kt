package cn.fishei.jleme.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import cn.fishei.jleme.MainActivity
import cn.fishei.jleme.R
import cn.fishei.jleme.database.Database
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login_password.*

//密码登录页面
class RegisterActivity : BaseActivity() {

    @SuppressLint("CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_password)
        login_password_delete.setOnClickListener {
            onBackPressed()
        }

        //点击登录
        agree_login.setOnClickListener {
            val username = entered_phone.text.toString()
            val password = entered_password.text.toString()
            val user = Database.dao.queryUser(username, password)
            if (user == null) {
                Toast.makeText(this, "密码无效", Toast.LENGTH_SHORT).show()
            } else {
                startActivity(Intent(this, MainActivity::class.java))
            }
        }

    }
}
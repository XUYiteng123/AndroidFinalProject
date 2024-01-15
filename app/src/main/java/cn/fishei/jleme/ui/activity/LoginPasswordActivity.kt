package cn.fishei.jleme.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import cn.fishei.jleme.model.User
import cn.fishei.jleme.MainActivity
import cn.fishei.jleme.R
import cn.fishei.jleme.database.Database
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login_password.*

//密码登录页面
class LoginPasswordActivity : BaseActivity() {

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
            val user = Database.dao.queryUser(username)
            if (user == null) {
                Database.dao.register(User().apply {
                    this.username = username;
                    this.password = password;
                })
                startActivity(Intent(this, MainActivity::class.java))
                Toast.makeText(
                    this,
                    "Automatic registration successful, you have been logged in",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                if (user.password != password) {
                    Toast.makeText(this, "Invalid password", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                startActivity(Intent(this, MainActivity::class.java))
                Toast.makeText(this, "Login succeeded！", Toast.LENGTH_SHORT).show()
            }
        }

    }
}
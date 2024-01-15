package cn.fishei.jleme.ui.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import cn.fishei.jleme.JilemeApplication
import cn.fishei.jleme.R
import cn.fishei.jleme.ui.activity.*
import kotlinx.android.synthetic.main.activity_me.*


class MeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.activity_me, container, false)
    }

    @SuppressLint("WrongConstant")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        layout_me_about.setOnClickListener {
            startActivity(
                Intent(
                    requireActivity(), MyFavoriteActivity::class.java
                )
            )
        }

        login_out.setOnClickListener {
            val intent = Intent(JilemeApplication.context, LoginPasswordActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }

    override fun onResume() {
        super.onResume()
        text_me_words.text = "Welcome！"
        login_out_card.visibility = View.VISIBLE
    }

}
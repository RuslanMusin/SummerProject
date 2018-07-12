package com.summer.itis.summerproject.ui.cards.cards_info

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.summer.itis.summerproject.R
import com.summer.itis.summerproject.model.Card

/**
 * Created by Home on 11.07.2018.
 */
class CardFragment : Fragment(),OnClickListener{

    override fun onClick(v: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    lateinit var tv_name: TextView
    lateinit var iv_port: ImageView
    lateinit var btn_state: Button
    lateinit var btn_wiki: Button
    lateinit var btn_test: Button
    lateinit var tv_description: TextView

    companion object {
        fun newInstance(card: Card): CardFragment {
            val args = Bundle()
            args.putParcelable("CARD", card)
            val fragment = CardFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_card, container, false)

        initViews(view)
        initOnClickListeners()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val arg = arguments
        val card = arg?.getParcelable<Card>("CARD")
        tv_name.setText(card?.abstractCard?.name)
        tv_description.setText(card?.abstractCard?.description)
    }


//    override fun onClick(v: View?) {
//        when(v?.id){
//            R.id.btn_state -> CardStatesActivity.start(activity!!)
//            R.id.btn_test -> println()// TODO redirect
//            R.id.btn_wiki -> println()// TODO WebView
//        }
//    }

    private fun initViews(view: View){
        tv_name = view.findViewById(R.id.tv_name)
        tv_description = view.findViewById(R.id.tv_description)
        iv_port = view.findViewById(R.id.iv_portrait)
        btn_state = view.findViewById(R.id.btn_state)
        btn_test = view.findViewById(R.id.btn_test)
        btn_wiki = view.findViewById(R.id.btn_wiki)
    }

    private fun initOnClickListeners(){
        btn_state.setOnClickListener(this)
        btn_test.setOnClickListener(this)
        btn_wiki.setOnClickListener(this)
    }
}
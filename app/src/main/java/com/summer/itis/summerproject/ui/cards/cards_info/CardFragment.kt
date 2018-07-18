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
import android.widget.Toast
import com.bumptech.glide.Glide
import com.summer.itis.summerproject.R
import com.summer.itis.summerproject.model.AbstractCard
import com.summer.itis.summerproject.ui.cards.card_states.CardStatesActivity
import com.summer.itis.summerproject.utils.ImageLoadHelper

/**
 * Created by Home on 11.07.2018.
 */
class CardFragment : Fragment(), OnClickListener {

    private lateinit var tv_name: TextView
    private lateinit var iv_photo: ImageView
    private lateinit var btn_state: Button
    private lateinit var btn_wiki: Button
    private lateinit var btn_test: Button
    private lateinit var tv_description: TextView
    private var card: AbstractCard? = null
    private var tagInput: String = ""

    companion object {
        fun newInstance(card: AbstractCard,tag: String): CardFragment {
            val args = Bundle()
            args.putParcelable("CARD", card)
            args.putString("TAG", tag)
            val fragment = CardFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_card, container, false)
        initViews(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val arg = arguments
        card = arg?.getParcelable("CARD")
        tagInput = arg?.getString("TAG") ?: "All"
        initOnClickListeners()
        if (tagInput == "All"){
            btn_state.visibility = View.GONE
        }
        tv_name.text = card?.name
        tv_description.text = card?.description
        if(card?.photoUrl != null) {
            Glide.with(context!!)
                    .load(card?.photoUrl)
                    .into(iv_photo)
        }
    }


    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_state -> CardStatesActivity.start(activity!!,card!!)
            R.id.btn_test -> println()// TODO redirect
            R.id.btn_wiki -> getWikiUrl()
        }
    }

    private fun getWikiUrl() {
        if(card?.wikiUrl != null){
            WebViewActivity.start(activity!!, card?.wikiUrl!!)
        }else{
            Toast.makeText(activity!!,"Wiki не существует",Toast.LENGTH_SHORT).show()
        }
    }

    private fun initViews(view: View){
        tv_name = view.findViewById(R.id.tv_name)
        tv_description = view.findViewById(R.id.tv_description)
        iv_photo = view.findViewById(R.id.iv_portrait)
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
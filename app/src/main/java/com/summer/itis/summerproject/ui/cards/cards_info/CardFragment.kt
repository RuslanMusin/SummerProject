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
import com.summer.itis.summerproject.R
import com.summer.itis.summerproject.model.AbstractCard
import com.summer.itis.summerproject.ui.cards.card_states.CardStatesActivity

/**
 * Created by Home on 11.07.2018.
 */
class CardFragment : Fragment(), OnClickListener {

    lateinit var tv_name: TextView
    lateinit var iv_port: ImageView
    lateinit var btn_state: Button
    lateinit var btn_wiki: Button
    lateinit var btn_test: Button
    lateinit var tv_description: TextView
    var card: AbstractCard? = null
    var pos: Int = 0

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
        card = arg?.getParcelable<AbstractCard>("CARD")

        initOnClickListeners()
        tv_name.text = card?.name
        tv_description.text = card?.description
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
            print(card?.wikiUrl)
            WebViewActivity.start(activity!!, card?.wikiUrl!!)
        }else{
            Toast.makeText(activity!!,"Wiki не существует",Toast.LENGTH_SHORT).show()
        }
    }

    private fun initViews(view: View){
        tv_name = view.findViewById(R.id.tv_name)
        tv_description = view.findViewById(R.id.tv_description)
        iv_port = view.findViewById(R.id.iv_portrait)
        btn_state = view.findViewById(R.id.btn_state)
        if ("All" == tag){
            btn_state.visibility = View.GONE
        }
        btn_test = view.findViewById(R.id.btn_test)
        btn_wiki = view.findViewById(R.id.btn_wiki)
    }

    private fun initOnClickListeners(){
        btn_state.setOnClickListener(this)
        btn_test.setOnClickListener(this)
        btn_wiki.setOnClickListener(this)
    }
}
package com.summer.itis.summerproject.ui.cards.card_states

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.summer.itis.summerproject.R
import com.summer.itis.summerproject.model.AbstractCard
import com.summer.itis.summerproject.model.Card
import com.summer.itis.summerproject.utils.ImageLoadHelper

/**
 * Created by Home on 11.07.2018.
 */
class CardStatesFragment : Fragment(){

    lateinit var iv_portrait: ImageView
    lateinit var tv_name: TextView
    lateinit var tv_strength: TextView
    lateinit var tv_intelligence: TextView
    lateinit var tv_prestige: TextView
    lateinit var tv_hp: TextView
    lateinit var tv_support: TextView

    companion object {
        fun newInstance(card: Card, aCard: AbstractCard): CardStatesFragment {
            val args = Bundle()
            args.putParcelable("CARD", card)
            args.putParcelable("ACARD", aCard)
            val fragment = CardStatesFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_card_state, container, false)

        initViews(view)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val arg = arguments
        val card = arg?.getParcelable<Card>("CARD")
        val aCard = arg?.getParcelable<AbstractCard>("ACARD")
        tv_name.setText(aCard?.name)
        tv_strength.setText("" + card?.strength?: "0")
        tv_intelligence.setText("" + card?.intelligence?: "0")
        tv_prestige.setText("" + card?.prestige?: "0")
        tv_hp.setText("" + card?.hp?: "0")
        tv_support.setText("" + card?.support?: "0")
        if(aCard?.photoUrl != null){
            Glide.with(context!!)
                    .load(aCard?.photoUrl)
                    .into(iv_portrait)
        }
    }

    private fun initViews(view: View){
        iv_portrait = view.findViewById(R.id.iv_portrait)
        tv_name = view.findViewById(R.id.tv_name)
        tv_strength = view.findViewById(R.id.tv_strength)
        tv_intelligence = view.findViewById(R.id.tv_intelligence)
        tv_prestige = view.findViewById(R.id.tv_prestige)
        tv_hp = view.findViewById(R.id.tv_hp)
        tv_support = view.findViewById(R.id.tv_support)
    }
}
package com.summer.itis.summerproject.ui.tests.add_test.fragments.main;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.summer.itis.summerproject.R;
import com.summer.itis.summerproject.api.Card;
import com.summer.itis.summerproject.model.Test;
import com.summer.itis.summerproject.ui.cards.add_card_list.AddCardListListActivity;
import com.summer.itis.summerproject.ui.tests.add_test.AddTestView;
import com.summer.itis.summerproject.ui.tests.add_test.fragments.question.AddQuestionFragment;

import static com.summer.itis.summerproject.ui.cards.add_card.AddCardActivity.CARD_EXTRA;
import static com.summer.itis.summerproject.utils.Const.COMA;
import static com.summer.itis.summerproject.utils.Const.gsonConverter;

public class AddTestFragment extends Fragment implements View.OnClickListener {

    private static final int RESULT_LOAD_IMG = 0;
    private static final int ADD_CARD = 1;

    private Uri imageUri;

    private TextInputLayout tiTestName;
    private TextInputLayout tiTestDesc;
    private LinearLayout liAddPhoto;
    private Button btnAddCard;
    private TextView tvAddedCards;
    private TextView tvAddPhoto;
    private Button btnCreateQuestions;

    private EditText etTestName;
    private EditText etTestDesc;

    private AddTestView addTestView;
    private Test test;

    public static Fragment newInstance() {
        AddTestFragment fragment =  new AddTestFragment();
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_add_test, container, false);
        addTestView = ((AddTestView) getActivity());
        test = new Test();
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initViews(view);
        setListeners();

        super.onViewCreated(view, savedInstanceState);
    }

    private void initViews(View view) {
        etTestDesc = view.findViewById(R.id.et_test_desc);
        etTestName = view.findViewById(R.id.et_test_name);
        tiTestName = view.findViewById(R.id.ti_test_name);
        tiTestDesc = view.findViewById(R.id.ti_test_desc);
        liAddPhoto = view.findViewById(R.id.li_add_photo);
        btnAddCard = view.findViewById(R.id.btn_add_card);
        btnCreateQuestions = view.findViewById(R.id.btn_create_questions);
        tvAddedCards = view.findViewById(R.id.tv_added_cards);
    }

    private void setListeners(){
        liAddPhoto.setOnClickListener(this);
        btnCreateQuestions.setOnClickListener(this);
        btnAddCard.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_create_questions:


//                test.setPhotoUrl(imageUri.getPath());

                test.setTitle(etTestName.getText().toString());
                test.setDesc(etTestDesc.getText().toString());

                addTestView.setTest(test);

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, AddQuestionFragment.newInstance())
                        .addToBackStack("AddTestFragment")
                        .commit();
                break;

            case R.id.btn_add_card :
                Intent intent = new Intent(getActivity(), AddCardListListActivity.class);
                startActivityForResult(intent,ADD_CARD);
                break;

            case R.id.li_add_photo :
//                addPhoto();
                break;
        }
    }

    private void addPhoto(){
        Intent photoPickerIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        photoPickerIntent.addCategory(Intent.CATEGORY_OPENABLE);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
        tvAddPhoto.setText(R.string.photo_uploaded);
    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        if (reqCode == RESULT_LOAD_IMG && resultCode == Activity.RESULT_OK) {
            imageUri = data.getData();
        }

        if (reqCode == ADD_CARD && resultCode == Activity.RESULT_OK) {
            Card card = gsonConverter.fromJson(data.getStringExtra(CARD_EXTRA),Card.class);
            tvAddedCards.setText(tvAddedCards.getText() + COMA + card.getName());
            test.setCard(card);
        }
    }
}

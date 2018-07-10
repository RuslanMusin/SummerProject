package com.summer.itis.summerproject.ui.tests.add_test.fragments.question;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.summer.itis.summerproject.R;
import com.summer.itis.summerproject.model.Answer;
import com.summer.itis.summerproject.model.Question;
import com.summer.itis.summerproject.model.Test;
import com.summer.itis.summerproject.repository.RepositoryProvider;
import com.summer.itis.summerproject.ui.member.member_item.PersonalActivity;
import com.summer.itis.summerproject.ui.tests.add_test.AddTestView;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.summer.itis.summerproject.utils.Const.STUB_PATH;
import static com.summer.itis.summerproject.utils.Const.TEST_MANY_TYPE;
import static com.summer.itis.summerproject.utils.Const.TEST_ONE_TYPE;

public class AddQuestionFragment extends Fragment implements View.OnClickListener {

    private static final int RESULT_LOAD_IMG = 0;

    private Uri imageUri;

    private Question question;
    private AddTestView addTestView;

    private TextInputLayout tiQuestion;
    private LinearLayout liAnswers;
    private Button btnAddAnswer;
    private Button btnNextQuestion;
    private Button btnFinish;
    private EditText etQuestion;
    private MaterialSpinner spinner;

    private List<Answer> answers;

    private List<EditText> editTexts;
    private List<CheckBox> checkBoxes;
    private List<RadioButton> radioButtons;

    private String testType;

    public static Fragment newInstance() {
        AddQuestionFragment fragment =  new AddQuestionFragment();
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_add_question, container, false);

        question = new Question();
        addTestView = ((AddTestView) getActivity());

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initViews(view);
        setListeners();
        super.onViewCreated(view, savedInstanceState);
    }

    private void initViews(View view) {
        etQuestion = view.findViewById(R.id.et_question);
        tiQuestion = view.findViewById(R.id.ti_question);
        liAnswers = view.findViewById(R.id.li_answers);
        btnAddAnswer = view.findViewById(R.id.btn_add_answer);
        btnNextQuestion = view.findViewById(R.id.btn_next_question);
        btnFinish = view.findViewById(R.id.btn_finish_questions);
        spinner = view.findViewById(R.id.spinner);
        spinner.setItems(getString(R.string.test_type_one),getString(R.string.test_type_many));

        answers = new ArrayList<>();
        editTexts = new ArrayList<>();
        radioButtons = new ArrayList<>();
        checkBoxes = new ArrayList<>();

        setStartAnswers();
    }

    private void setStartAnswers() {
        RadioGroup radioGroup = new RadioGroup(getActivity());
        LinearLayout.LayoutParams rgParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        radioGroup.setLayoutParams(rgParams);


        LinearLayout.LayoutParams liParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        liParams.setMargins(0,8,0,8);

        LinearLayout.LayoutParams tiParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);


        LinearLayout.LayoutParams etParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);


        LinearLayout.LayoutParams rbParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        for(int i = 0; i < 3; i++) {
            LinearLayout liRadio = new LinearLayout(getActivity());
            liRadio.setOrientation(LinearLayout.HORIZONTAL);
            liRadio.setLayoutParams(liParams);

            TextInputLayout textInputLayout = new TextInputLayout(getActivity());
            textInputLayout.setWeightSum(20);
            textInputLayout.setLayoutParams(tiParams);

            EditText editText = new EditText(getActivity());
            editText.setLayoutParams(etParams);

            RadioButton radioButton = new RadioButton(getActivity());
            radioButton.setLayoutParams(rbParams);

            textInputLayout.addView(editText);
            liRadio.addView(textInputLayout);
            liRadio.addView(radioButton);

            radioGroup.addView(liRadio);

            radioButtons.add(radioButton);
            editTexts.add(editText);
        }

        liAnswers.addView(radioGroup);
    }


    private void setListeners(){
        btnAddAnswer.setOnClickListener(this);
        btnNextQuestion.setOnClickListener(this);
        btnFinish.setOnClickListener(this);
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
                switch (position) {
                    case 0:
                        testType = TEST_ONE_TYPE;
                        changeToOneType();
                        break;

                    case 1:
                        testType = TEST_MANY_TYPE;
                        changeToManyType();
                        break;

                }
            }
        });
    }

    private void changeToManyType(){

    }

    private void changeToOneType(){
      /*  RadioGroup radioGroup = new RadioGroup(getActivity());


        for(String answer : answers) {
            RadioButton radioButton = new RadioButton(getActivity());
            radioButton.setText(answer);
            radioGroup.addView(radioButton,wrapParams);
        }

        liAnswers.addView(radioGroup,wrapParams);*/

    }

    @Override
    public void onClick(View v) {


        switch (v.getId()) {

            case R.id.btn_finish_questions:

                prepareQuestion();
                addTestView.createTest();
                PersonalActivity.Companion.start(getActivity());
                break;

            case R.id.btn_next_question :
                prepareQuestion();
                  getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, AddQuestionFragment.newInstance())
                        .addToBackStack("AddQuestionFragment")
                        .commit();
                break;

            case R.id.btn_add_answer :

                EditText editText = new EditText(this.getActivity());
                LinearLayout.LayoutParams leftMarginParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                leftMarginParams.setMargins(10,10,10,10);
                liAnswers.addView(editText);


            case R.id.li_add_photo :
//                addPhoto();
                break;
        }
    }


    private void prepareQuestion() {

        radioButtons.clear();
        editTexts.clear();
        answers = new ArrayList<>();
        for(int i = 0; i < radioButtons.size(); i++) {
            Answer answer = new Answer();
            answer.setText(editTexts.get(i).getText().toString());
            if(radioButtons.get(i).isChecked()) {
                answer.setRight(true);
            }
            answers.add(answer);
        }

        question.setQuestion(etQuestion.getText().toString());
//        question.setPhotoUrl(imageUri.getPath());
        question.setAnswers(answers);
        addTestView.setQuestion(question);

    }

    private void addPhoto(){
        Intent photoPickerIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        photoPickerIntent.addCategory(Intent.CATEGORY_OPENABLE);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            imageUri = data.getData();
        }
    }
}

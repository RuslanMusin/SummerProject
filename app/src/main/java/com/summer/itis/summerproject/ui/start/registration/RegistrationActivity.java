package com.summer.itis.summerproject.ui.start.registration;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.common.api.Status;

import com.google.firebase.auth.FirebaseAuth;
import com.summer.itis.summerproject.R;
import com.summer.itis.summerproject.model.User;
import com.summer.itis.summerproject.ui.base.BaseActivity;
import com.summer.itis.summerproject.ui.member.member_item.PersonalActivity;
import com.summer.itis.summerproject.ui.start.login.LoginActivity;

import java.util.Calendar;


public class RegistrationActivity extends BaseActivity implements View.OnClickListener{

    private static final int RESULT_LOAD_IMG = 0;

    private AppCompatButton btnRegistrNewReader;
    private TextView tvAddPhoto;
    private LinearLayout liAddPhoto;
    private TextView tvLogin;
    private TextInputLayout tiEmail;
    private TextInputLayout tiPassword;
    private TextInputLayout tiUsername;
    private EditText etEmail;
    private EditText etPassword;
    private EditText etUsername;

    private User user;
    private FirebaseAuth fireAuth;

    private Uri imageUri;

    private RegistrationPresenter presenter;

    public static void start(@NonNull Activity activity) {
        Intent intent = new Intent(activity, RegistrationActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        etUsername = findViewById(R.id.et_username);

        tiEmail = findViewById(R.id.ti_email);
        tiPassword = findViewById(R.id.ti_password);
        tiUsername = findViewById(R.id.ti_username);

        btnRegistrNewReader = findViewById(R.id.btn_sign_up);
        liAddPhoto = findViewById(R.id.li_add_photo);
        tvAddPhoto = findViewById(R.id.tv_add_photo);
        tvLogin = findViewById(R.id.link_login);

        btnRegistrNewReader.setOnClickListener(this);
        liAddPhoto.setOnClickListener(this);
        tvLogin.setOnClickListener(this);

        presenter = new RegistrationPresenter(this);

        fireAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_sign_up:
                String username = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                presenter.createAccount(username,password);
                break;

            case R.id.li_add_photo:
                addPhoto();
                break;

            case R.id.link_login:
                LoginActivity.start(this);
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
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            imageUri = data.getData();
        }else {
            imageUri = null;
            Toast.makeText(RegistrationActivity.this, "You haven't picked Image", Toast.LENGTH_LONG).show();
        }
    }

    void goToBookList(){
        PersonalActivity.start(this);
    }

    //set-get

    public FirebaseAuth getFireAuth() {
        return fireAuth;
    }

    public void setFireAuth(FirebaseAuth fireAuth) {
        this.fireAuth = fireAuth;
    }

    public AppCompatButton getBtnRegistrNewReader() {
        return btnRegistrNewReader;
    }

    public void setBtnRegistrNewReader(AppCompatButton btnRegistrNewReader) {
        this.btnRegistrNewReader = btnRegistrNewReader;
    }

    public TextView getTvAddPhoto() {
        return tvAddPhoto;
    }

    public void setTvAddPhoto(TextView tvAddPhoto) {
        this.tvAddPhoto = tvAddPhoto;
    }

    public LinearLayout getLiAddPhoto() {
        return liAddPhoto;
    }

    public void setLiAddPhoto(LinearLayout liAddPhoto) {
        this.liAddPhoto = liAddPhoto;
    }

    public TextView getTvLogin() {
        return tvLogin;
    }

    public void setTvLogin(TextView tvLogin) {
        this.tvLogin = tvLogin;
    }

    public TextInputLayout getTiEmail() {
        return tiEmail;
    }

    public void setTiEmail(TextInputLayout tiEmail) {
        this.tiEmail = tiEmail;
    }

    public TextInputLayout getTiPassword() {
        return tiPassword;
    }

    public void setTiPassword(TextInputLayout tiPassword) {
        this.tiPassword = tiPassword;
    }

    public TextInputLayout getTiUsername() {
        return tiUsername;
    }

    public void setTiUsername(TextInputLayout tiUsername) {
        this.tiUsername = tiUsername;
    }

    public EditText getEtEmail() {
        return etEmail;
    }

    public void setEtEmail(EditText etEmail) {
        this.etEmail = etEmail;
    }

    public EditText getEtPassword() {
        return etPassword;
    }

    public void setEtPassword(EditText etPassword) {
        this.etPassword = etPassword;
    }

    public EditText getEtUsername() {
        return etUsername;
    }

    public void setEtUsername(EditText etUsername) {
        this.etUsername = etUsername;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }
}

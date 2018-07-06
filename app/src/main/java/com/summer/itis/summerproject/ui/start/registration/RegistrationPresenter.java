package com.summer.itis.summerproject.ui.start.registration;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.summer.itis.summerproject.R;
import com.summer.itis.summerproject.model.User;
import com.summer.itis.summerproject.model.repository.RepositoryProvider;
import com.summer.itis.summerproject.ui.base.NavigationPresenter;
import com.summer.itis.summerproject.utils.ApplicationHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static com.summer.itis.summerproject.utils.Const.IMAGE_START_PATH;
import static com.summer.itis.summerproject.utils.Const.SEP;
import static com.summer.itis.summerproject.utils.Const.TAG_LOG;

public class RegistrationPresenter {

    private RegistrationActivity regView;

    String myFormat = "dd.MM.yyyy"; //In which you need put here
    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());

    public RegistrationPresenter(RegistrationActivity regView) {
        this.regView = regView;
    }

    void createAccount(String email, String password) {
        Log.d(TAG_LOG, "createAccount:" + email);
        if (!validateForm()) {
            return;
        }

        regView.showProgressDialog();

        regView.getFireAuth().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(regView, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG_LOG, "createUserWithEmail:success");
                            FirebaseUser user = regView.getFireAuth().getCurrentUser();
                            createInDatabase(user);
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG_LOG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(regView, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                        regView.hideProgressDialog();
                    }
                });
    }

    private void createInDatabase(FirebaseUser firebaseUser) {
        User user = new User();

        String username = regView.getEtUsername().getText().toString();
        String email = regView.getEtEmail().getText().toString();

        user.setEmail(email);
        user.setUsername(username);

        user.setId(firebaseUser.getUid());

        Uri uri = regView.getImageUri();
        String path;
        if(uri != null) {
            path = IMAGE_START_PATH + user.getId() + SEP
                    + uri.getLastPathSegment();
        } else {
            path = "path";
        }

        user.setPhotoUrl(path);

        regView.setUser(user);

        if(!path.equals("path")) {
            StorageReference childRef = ApplicationHelper.getStorageReference().child(user.getPhotoUrl());

            //uploading the image
            UploadTask uploadTask = childRef.putFile(regView.getImageUri());

            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(regView, "Upload successful", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
//                pd.dismiss();
                    Toast.makeText(regView, "Upload Failed -> " + e, Toast.LENGTH_SHORT).show();
                }
            });
        }

        RepositoryProvider.getUserRepository().createUser(user);
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = regView.getEtEmail().getText().toString();
        if (TextUtils.isEmpty(email)) {
            regView.getTiEmail().setError(regView.getString(R.string.enter_correct_name));
            valid = false;
        } else {
            regView.getTiEmail().setError(null);
        }

        String password = regView.getEtPassword().getText().toString();
        if (TextUtils.isEmpty(password)) {
            regView.getTiPassword().setError(regView.getString(R.string.enter_correct_password));
            valid = false;
        } else {
            regView.getTiPassword().setError(null);
        }

        return valid;
    }

    private void updateUI(FirebaseUser firebaseUser) {
        regView.hideProgressDialog();
        if (firebaseUser != null) {
            NavigationPresenter.setCurrentUser(regView.getUser());
            regView.goToBookList();
        }
    }

    public String formatDate(Calendar calendar){
        return sdf.format(calendar.getTime());
    }
}

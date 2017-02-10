package com.example.franois.gouiranlinkproject;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.franois.gouiranlinkproject.ToolsClasses.PostRequest;

import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.R.string.cancel;

public class SignUp extends AppCompatActivity {

    String json = null;
    String name = null;
    String surname = null;
    String email = null;
    String password = null;
    String passwordConfirmation = null;
    String phoneNumber = null;
    PostRequest postRequest = null;

    EditText surnameEditText;
    EditText nameEditText;
    EditText emailEditText;
    EditText passwordEditText;
    EditText passwordConfirmationEditText;
    EditText phoneNumberEditText;

    boolean isEmailValid(String email) {
        String EmailPattern = "[_A-Za-z0-9-\\\\+.]+@[A-Za-z0-9-]+\\.[A-Za-z]+";
        final Pattern pattern = Pattern.compile(EmailPattern);
        final Matcher matcher = pattern.matcher(email);

        return (matcher.matches());
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        surnameEditText = (EditText)findViewById(R.id.surname);
        nameEditText = (EditText)findViewById(R.id.name);
        emailEditText = (EditText)findViewById(R.id.email_address);
        passwordEditText = (EditText)findViewById(R.id.password);
        passwordConfirmationEditText = (EditText)findViewById(R.id.password_confirmation);
        phoneNumberEditText = (EditText)findViewById(R.id.phone_number);
    }

    public void onClick(View v) {
        View focusView = null;
        Boolean cancel = false;
        String resp = null;

        surname = surnameEditText.getText().toString();
        name = nameEditText.getText().toString();
        email = emailEditText.getText().toString();
        password = passwordEditText.getText().toString();
        passwordConfirmation = passwordConfirmationEditText.getText().toString();
        phoneNumber = phoneNumberEditText.getText().toString();

        if (!isEmailValid(email)) {
            emailEditText.setError(getString(R.string.invalid_email));
            focusView = emailEditText;
            cancel = true;
            focusView.requestFocus();
        }
        if (!password.equals(passwordConfirmation)) {
            passwordEditText.setError(getString(R.string.non_equal_password));
            focusView = passwordEditText;
            cancel = true;
            focusView.requestFocus();
        }
        if (password == null || password.equals("")) {
            passwordEditText.setError(getString(R.string.necessary_field));
            cancel = true;
            focusView = passwordEditText;
            focusView.requestFocus();
        }
        if (passwordConfirmation == null || passwordConfirmation.equals("")) {
            passwordConfirmationEditText.setError(getString(R.string.necessary_field));
            cancel = true;
            focusView = passwordConfirmationEditText;
            focusView.requestFocus();
        }
        if (name == null || name.equals("")) {
            nameEditText.setError(getString(R.string.necessary_field));
            cancel = true;
            focusView = nameEditText;
            focusView.requestFocus();
        }
        if (surname == null || surname.equals("")) {
            surnameEditText.setError(getString(R.string.necessary_field));
            cancel = true;
            focusView = surnameEditText;
            focusView.requestFocus();
        }
        if (phoneNumber == null || phoneNumber.equals("")) {
            phoneNumberEditText.setError(getString(R.string.necessary_field));
            cancel = true;
            focusView = phoneNumberEditText;
            focusView.requestFocus();
        }

        if (!cancel) {
            json = "{\n" +
                    "\"name\":\"" + name + "\",\n" +
                    "\"surname\":\"" + surname + "\",\n" +
                    "\"email\":\"" + email + "\",\n" +
                    "\"password\":\"" + password + "\"\n" +
                    "}\n";
            postRequest = new PostRequest("https://www.gouiran-beaute.com/link/api/v1/customer/", json);
            try {
                resp = postRequest.execute().get();
                //Toast.makeText(this, resp, Toast.LENGTH_LONG).show();
                //TODO : Vérifier retour requête 500 Internal Server Error

            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }

            DisplayMetrics displayMetrics = new DisplayMetrics();

            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
            ViewGroup popUpLayout = (ViewGroup) layoutInflater.inflate(R.layout.popup, null);
            TextView textView = new TextView(this);
            textView = (TextView)popUpLayout.findViewById(R.id.textView);
            textView.setText(R.string.confirmation_mail_sentence);
            textView.setBackgroundColor(getResources().getColor(R.color.GouiranLightPink));
            textView.setTextSize(20);
            Typeface font;
            font = Typeface.createFromAsset(getAssets(), "fonts/Acrom W00 Medium.ttf");
            textView.setTypeface(font);
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int width = displayMetrics.widthPixels;
            int height = displayMetrics.heightPixels;
            Dialog dialog = new Dialog(SignUp.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(popUpLayout);
            dialog.getWindow().setLayout((int)(width * 0.75), (int)(height * 0.25));
            dialog.show();
        }

    }
}

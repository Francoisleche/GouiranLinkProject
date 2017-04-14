package com.gouiranlink.franois.gouiranlinkproject.InsciptionConnexion;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.gouiranlink.franois.gouiranlinkproject.R;
import com.gouiranlink.franois.gouiranlinkproject.ToolsClasses.PostRequest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {

    private String json = null;
    private String name = null;
    private String surname = null;
    private String email = null;
    private String password = null;
    private String passwordConfirmation = null;
    private String phoneNumber = null;
    private PostRequest postRequest = null;

    private EditText surnameEditText;
    private EditText nameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText passwordConfirmationEditText;
    private EditText phoneNumberEditText;

    private boolean isEmailValid(String email) {
        String EmailPattern = "[_A-Za-z0-9-\\\\+.]+@[A-Za-z0-9-]+\\.[A-Za-z]+";
        final Pattern pattern = Pattern.compile(EmailPattern);
        final Matcher matcher = pattern.matcher(email);

        return (matcher.matches());
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        surnameEditText = (EditText) findViewById(R.id.surname);
        nameEditText = (EditText) findViewById(R.id.name);
        emailEditText = (EditText) findViewById(R.id.email_address);
        passwordEditText = (EditText) findViewById(R.id.password);
        passwordConfirmationEditText = (EditText) findViewById(R.id.password_confirmation);
        phoneNumberEditText = (EditText) findViewById(R.id.phone_number);
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

        /*if (!isEmailValid(email)) {
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
            }*/

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.confirmation_mail_sentence).setNeutralButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

}
//}

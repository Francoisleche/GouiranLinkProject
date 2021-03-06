package com.gouiranlink.franois.gouiranlinkproject.InsciptionConnexion;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.gouiranlink.franois.gouiranlinkproject.R;
import com.gouiranlink.franois.gouiranlinkproject.ToolsClasses.PostRequest;

import java.util.concurrent.ExecutionException;
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
    private String codepostal = null;
    private PostRequest postRequest = null;

    private EditText surnameEditText;
    private EditText nameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText passwordConfirmationEditText;
    private EditText phoneNumberEditText;
    private EditText codepostalEditText;

    private boolean isEmailValid(String email) {
        String EmailPattern = "[_A-Za-z0-9-\\\\+.]+@[A-Za-z0-9-]+\\.[A-Za-z]+";
        final Pattern pattern = Pattern.compile(EmailPattern);
        final Matcher matcher = pattern.matcher(email);

        return (matcher.matches());
    }

    private boolean isCodePostalValid(String codepostal) {
        String CodePostalPattern = "[0-9]+";
        final Pattern pattern = Pattern.compile(CodePostalPattern);
        final Matcher matcher = pattern.matcher(codepostal);

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
        codepostalEditText = (EditText) findViewById(R.id.post_code);
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
        codepostal = codepostalEditText.getText().toString();

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
        if (password.length()<4) {
            passwordEditText.setError("Pas moins de 4 caractères");
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
        if (!isCodePostalValid(codepostal)){
            codepostalEditText.setError("Ce code postal est invalide.");
            focusView = codepostalEditText;
            cancel = true;
            focusView.requestFocus();
        }
        if (codepostal.length()!=5){
            codepostalEditText.setError("Ce code postal est invalide.");
            focusView = codepostalEditText;
            cancel = true;
            focusView.requestFocus();
        }




        String gender ="";
        RadioGroup radioSexGroup = (RadioGroup) findViewById(R.id.radioSex);
        int selectedId = radioSexGroup.getCheckedRadioButtonId();
        RadioButton radioSexButton = (RadioButton) findViewById(selectedId);
        if(radioSexButton.getText().equals("Une femme")){
            gender = "F";
        }else{
            gender = "M";
        }


        if (!cancel) {
            json = "{\n" +
                    "\"name\":\"" + name + "\",\n" +
                    "\"surname\":\"" + surname + "\",\n" +
                    "\"gender\":\"" + gender + "\",\n" +
                    "\"email\":\"" + email + "\",\n" +
                    "\"password\":\"" + password + "\",\n" +
                    "\"mobilephone\":\""+ phoneNumber + "\"\n" +
                    "}\n";
            postRequest = new PostRequest("https://www.gouiran-beaute.com/link/api/v1/customer/", json);
            try {
                resp = postRequest.execute().get();
                System.out.println("ON PASSSSSSSE PAR LA ?????");
                System.out.println(resp);
                //Toast.makeText(this, resp, Toast.LENGTH_LONG).show();
                //TODO : Vérifier retour requête 500 Internal Server Error

            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.confirmation_mail_sentence).setNeutralButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(SignUp.this, LoginActivity.class);
                startActivity(intent);
                finish();

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }else{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Erreur, un champ n'est pas renseigné !").setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
            AlertDialog dialog = builder.create();
        dialog.show();
    }



}


    //Retour arrière géré
    @Override
    public boolean dispatchKeyEvent(KeyEvent KEvent)
    {
        System.out.println("Pourquoijepassepasparla");
        int keyaction = KEvent.getAction();

        if(keyaction == KeyEvent.KEYCODE_BACK)
        {
            int keycode = KEvent.getKeyCode();
            int keyunicode = KEvent.getUnicodeChar(KEvent.getMetaState() );
            char character = (char) keyunicode;

            System.out.println("DEBUG MESSAGE KEY=" + character + " KEYCODE=" +  keycode);

            //finish();
        }


        return super.dispatchKeyEvent(KEvent);
    }





}

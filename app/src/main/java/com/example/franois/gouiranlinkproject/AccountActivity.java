package com.example.franois.gouiranlinkproject;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import static com.example.franois.gouiranlinkproject.BaseFragment.ARGS_INSTANCE;

public class AccountActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        ImageView modification_compte = (ImageView) findViewById(R.id.edit_profile);
        Button bouton_parametre = (Button) findViewById(R.id.settings_button);
        Button bouton_appli_info = (Button) findViewById(R.id.appli_info);
        Button bouton_donner_avis = (Button) findViewById(R.id.appli_comment);
        Button bouton_invite_friends = (Button) findViewById(R.id.invite_friends);
        Button bouton_deconnexion = (Button) findViewById(R.id.deconnexion);



        modification_compte.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setContentView(R.layout.activity_account_modify);
            }
        });


        bouton_parametre.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setContentView(R.layout.fragment_settings);
            }
        });


        bouton_appli_info.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setContentView(R.layout.activity_apropos);
            }
        });


        bouton_donner_avis.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setContentView(R.layout.activity_error_application);
            }
        });


        bouton_invite_friends.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setContentView(R.layout.activity_account_modify);
            }
        });


        bouton_deconnexion.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setContentView(R.layout.activity_account_modify);
            }
        });


    }




    //SERT A RIEN
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edit_profile:
                break;
            case R.id.settings_button:
                break;
            case R.id.appli_comment:
                break;
            case R.id.invite_friends:
                break;
        }
    }
}

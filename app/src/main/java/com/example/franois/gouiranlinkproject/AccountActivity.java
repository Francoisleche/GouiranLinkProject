package com.example.franois.gouiranlinkproject;

import android.content.Context;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import static com.example.franois.gouiranlinkproject.BaseFragment.ARGS_INSTANCE;

public class AccountActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edit_profile:
                break;
            case R.id.settings_button:
                break;
            case R.id.settings_text:
                break;
            case R.id.invite_friends:
                break;
        }
    }
}

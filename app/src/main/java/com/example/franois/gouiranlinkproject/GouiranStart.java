package com.example.franois.gouiranlinkproject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.franois.gouiranlinkproject.InsciptionConnexion.LoginActivity;

public class GouiranStart extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gouiran_start);

        ImageView background = (ImageView) findViewById(R.id.background);
        final DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        final int width = displayMetrics.widthPixels;
        final int height = displayMetrics.heightPixels;
        final Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Acrom W00 Medium.ttf");

        float heightF = (float) height;
        float twoThird = (float) 2 / 3;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, (int) (heightF * twoThird));
        background.setLayoutParams(params);

        TextView text = (TextView) findViewById(R.id.presentation_text);
        String content = getString(R.string.presentation_text);
        String[] each = content.split(" ");
        SpannableString ss1 = new SpannableString(each[0]);
        ss1.setSpan(new RelativeSizeSpan(3f), 0, each[0].length(), 0); // set size
        ss1.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getApplicationContext(), R.color.GouiranDarkPink)), 0, each[0].length(), 0);// set color
        SpannableString ss2 = new SpannableString(each[1]);
        ss2.setSpan(new RelativeSizeSpan(3f), 0, each[1].length(), 0); // set size
        ss2.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getApplicationContext(), R.color.GouiranDarkPink)), 0, each[1].length(), 0);// set color
        SpannableString ss3 = new SpannableString(each[2]);
        ss3.setSpan(new RelativeSizeSpan(2f), 0, each[2].length(), 0); // set size
        ss3.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getApplicationContext(), R.color.GouiranDarkPink)), 0, each[2].length(), 0);// set color
        SpannableString ss4 = new SpannableString(each[3]);
        ss4.setSpan(new RelativeSizeSpan(2f), 0, each[3].length(), 0); // set size
        ss4.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getApplicationContext(), R.color.GouiranDarkPink)), 0, each[3].length(), 0);// set color
        SpannableString ss5 = new SpannableString(each[4]);
        ss5.setSpan(new RelativeSizeSpan(2f), 0, each[4].length(), 0); // set size
        ss5.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getApplicationContext(), R.color.GouiranDarkPink)), 0, each[4].length(), 0);// set color
        SpannableString ss6 = new SpannableString(each[5]);
        ss6.setSpan(new RelativeSizeSpan(5f), 0, each[5].length(), 0); // set size
        ss6.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getApplicationContext(), R.color.GouiranDarkPink)), 0, each[5].length(), 0);// set color

        CharSequence res = TextUtils.concat(ss1, "  ", ss2, "\n", ss3, "  ", ss4, "  ", ss5, "\n", ss6);
        text.setText(res);
        text.setTypeface(font);

        final Context context = this;

        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage(R.string.notification_push)
                        .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(GouiranStart.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                AlertDialog dialog = builder.create();
                isFirstTime(dialog);
            }
        }, 1500);
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        finish();
    }

    private boolean isFirstTime(AlertDialog dialog) {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        boolean ranBefore = preferences.getBoolean("RanBefore", false);
        if (!ranBefore) {
            //show dialog if app never launch
            dialog.show();
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("RanBefore", true);
            editor.commit();
        }
        else
        {
            Intent intent = new Intent(GouiranStart.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        return !ranBefore;
    }
}

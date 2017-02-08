package com.example.franois.gouiranlinkproject;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.franois.gouiranlinkproject.ToolsClasses.DownloadImageTask;


public class DoneFragment extends Fragment{

    public DoneFragment() {
        // Required empty public constructor
    }

    LinearLayout[] linearLayouts = new LinearLayout[10];
    LinearLayout[] separatorParts = new LinearLayout[10];
    LinearLayout[] firstParts = new LinearLayout[10];
    LinearLayout[] secondParts = new LinearLayout[10];
    LinearLayout[] thirdParts = new LinearLayout[10];
    Button[] buttons = new Button[10];
    RelativeLayout layout;
    Typeface font;
    TextView instituteName, type, date, hour, slash;
    ImageView imageView;
    LinearLayout.LayoutParams LLParams;
    View separatorView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root;
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_done, null);
        for (int i = 0; i < 10; i++) {
            int resID = getResources().getIdentifier("reservation" + (i + 1), "id", getActivity().getPackageName());
            linearLayouts[i] = (LinearLayout)root.findViewById(resID);
            resID = getResources().getIdentifier("reservation" + (i + 1) + "_part0", "id", getActivity().getPackageName());
            separatorParts[i] = (LinearLayout)root.findViewById(resID);
            resID = getResources().getIdentifier("reservation" + (i + 1) + "_part1", "id", getActivity().getPackageName());
            firstParts[i] = (LinearLayout)root.findViewById(resID);
            resID = getResources().getIdentifier("reservation" + (i + 1) + "_part2", "id", getActivity().getPackageName());
            secondParts[i] = (LinearLayout)root.findViewById(resID);
            resID = getResources().getIdentifier("reservation" + (i + 1) + "_part3", "id", getActivity().getPackageName());
            thirdParts[i] = (LinearLayout)root.findViewById(resID);
            buttons[i] = new Button(getActivity());
        }
        layout = (RelativeLayout) root.findViewById(R.id.done_relative);
        font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Acrom W00 Medium.ttf");
        LLParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        return (root);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // TODO List of brut links of pictures
        String[] pictures = {"http://d1o5dtyz8r69kc.cloudfront.net/206_thumb.jpg",
                "http://cdn.dealerspike.com/imglib/v1/400x300/imglib/trimsdb/3029321-3229921-10086451.jpg",
                "http://www.cyclesofjacksonville.com/images/SEO/New-Motorcycle-For-Sale.jpg",
                "http://www.snowcity.com/fckimages/toronto-gta-motorcycle-shops.jpg",
                "http://d1o5dtyz8r69kc.cloudfront.net/206_thumb.jpg",
                "http://cdn.dealerspike.com/imglib/v1/400x300/imglib/trimsdb/3029321-3229921-10086451.jpg",
                "http://www.cyclesofjacksonville.com/images/SEO/New-Motorcycle-For-Sale.jpg",
                "http://www.snowcity.com/fckimages/toronto-gta-motorcycle-shops.jpg",
                "http://d1o5dtyz8r69kc.cloudfront.net/206_thumb.jpg",
                "http://cdn.dealerspike.com/imglib/v1/400x300/imglib/trimsdb/3029321-3229921-10086451.jpg"};
        String[] institute = {"Beauté Romaine", "Beauté Languedoc", "Beauté Héraultaise", "Coiffure Yannick", "Beauté Romaine", "Beauté Languedoc", "Beauté Héraultaise", "Coiffure Yannick", "Beauté Romaine", "Beauté Languedoc"};
        String[] types = {"Epilation", "Epilation", "Epilation", "Lissage Brésilien", "Epilation", "Epilation", "Epilation", "Lissage Brésilien", "Epilation", "Epilation"};
        String[] dates = {"Mardi 21 Décembre 2016", "Jeudi 03 Mars 2017", "Vendredi 18 Mai 2017", "Lundi 30 Juin 2017", "Mardi 21 Décembre 2016", "Jeudi 03 Mars 2017", "Vendredi 18 Mai 2017", "Lundi 30 Juin 2017", "Mardi 21 Décembre 2016", "Jeudi 03 Mars 2017"};
        String[] hours = {"14h30", "11h00", "08h30", "16h00", "14h30", "11h00", "08h30", "16h00", "14h30", "11h30"};
        for (int i = 0; i < 10; i++) {

            LinearLayout.LayoutParams weightParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
            LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
            LinearLayout.LayoutParams imageViewParams = new LinearLayout.LayoutParams(250, 250);
            LinearLayout.LayoutParams hourParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
            LinearLayout.LayoutParams dateParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);

            separatorView = new View(getActivity());
            separatorView.setBackgroundColor(getResources().getColor(R.color.black));
            separatorView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1));
            separatorParts[i].addView(separatorView);

            instituteName = new TextView(getActivity());
            instituteName.setText(institute[i]);
            instituteName.setTypeface(font);
            instituteName.setTextSize(20);
            instituteName.setGravity(Gravity.CENTER_HORIZONTAL);
            instituteName.setPaintFlags(instituteName.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
            firstParts[i].addView(instituteName, weightParams);

            slash = new TextView(getActivity());
            slash.setText("/");
            slash.setTypeface(font);
            slash.setTextSize(20);
            slash.setGravity(Gravity.CENTER_HORIZONTAL);
            firstParts[i].addView(slash, weightParams);

            type = new TextView(getActivity());
            type.setText(types[i]);
            type.setTypeface(font);
            type.setTextSize(20);
            type.setGravity(Gravity.CENTER_HORIZONTAL);
            firstParts[i].addView(type, weightParams);

            imageView = new ImageView(getActivity());
            imageView.setLayoutParams(new android.view.ViewGroup.LayoutParams(80,60));
            new DownloadImageTask(imageView).execute(pictures[i]);
            imageViewParams.gravity = Gravity.CENTER_HORIZONTAL;
            secondParts[i].addView(imageView, imageViewParams);

            date = new TextView(getActivity());
            date.setText(dates[i]);
            date.setTypeface(font);
            date.setTextSize(17);
            date.setGravity(Gravity.CENTER_HORIZONTAL);
            dateParams.setMargins(0, 50, 0, 25);
            thirdParts[i].addView(date, dateParams);

            hour = new TextView(getActivity());
            hour.setText(hours[i]);
            hour.setTypeface(font);
            hour.setTextSize(17);
            hour.setGravity(Gravity.CENTER_HORIZONTAL);
            hourParams.setMargins(0, 25, 0, 50);
            thirdParts[i].addView(hour, hourParams);

            buttons[i].setText("Voir");
            buttonParams.setMargins(20, 100, 0, 0);
            buttons[i].setBackgroundColor(buttons[i].getContext().getResources().getColor(R.color.GouiranLightBlue));
            secondParts[i].addView(buttons[i], buttonParams);

        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

}
package com.example.franois.gouiranlinkproject;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class MyCrushes extends Fragment{

    private LinearLayout coiffureTitleLayout;
    private LinearLayout coiffureTitleContent;
    private LinearLayout beauteTitleLayout;
    private LinearLayout beauteTitleContent;
    private LinearLayout bienEtreTitleLayout;
    private LinearLayout bienEtreTitleContent;
    private LinearLayout hommeTitleLayout;
    private LinearLayout hommeTitleContent;
    private Typeface font;
    private TextView coiffureTitle;
    private TextView beauteTitle;
    private TextView bienEtreTitle;
    private TextView hommeTitle;

    public MyCrushes() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root;
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_my_crushes, null);
        coiffureTitleLayout = (LinearLayout) root.findViewById(R.id.coiffure_crushes_title);
        coiffureTitleContent = (LinearLayout) root.findViewById(R.id.coiffure_crushes_content);
        beauteTitleLayout = (LinearLayout) root.findViewById(R.id.beaute_crushes_title);
        beauteTitleContent = (LinearLayout) root.findViewById(R.id.beaute_crushes_content);
        bienEtreTitleLayout = (LinearLayout) root.findViewById(R.id.bien_etre_crushes_title);
        bienEtreTitleContent = (LinearLayout) root.findViewById(R.id.bien_etre_crushes_content);
        hommeTitleLayout = (LinearLayout) root.findViewById(R.id.homme_crushes_title);
        hommeTitleContent = (LinearLayout) root.findViewById(R.id.homme_crushes_content);
        font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Acrom W00 Medium.ttf");
        coiffureTitle = new TextView(getActivity());
        beauteTitle = new TextView(getActivity());
        bienEtreTitle = new TextView(getActivity());
        hommeTitle = new TextView(getActivity());



        return (root);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        // COIFFURE
        coiffureTitle.setText("Coiffure");
        coiffureTitle.setTypeface(font);
        coiffureTitle.setTextSize(30);
        coiffureTitle.setGravity(Gravity.CENTER_HORIZONTAL);
        coiffureTitleLayout.addView(coiffureTitle);
        for (int i = 0; i < 20; i++) {
            ImageView coiffureImage = new ImageView(getActivity());
            coiffureImage.setLayoutParams(new android.view.ViewGroup.LayoutParams(80,60));
            if (i % 2 == 0)
                coiffureImage.setImageResource(R.drawable.h2r);
            else if (i % 3 == 0)
                coiffureImage.setImageResource(R.drawable.gsxr);
            else if (i % 4 == 0)
                coiffureImage.setImageResource(R.drawable.kawa);
            else if (i % 5 == 0)
                coiffureImage.setImageResource(R.drawable.er6n);
            else
                coiffureImage.setImageResource(R.drawable.tiger);
            coiffureImage.getLayoutParams().height = 150;
            coiffureImage.getLayoutParams().width = 150;
            coiffureTitleContent.addView(coiffureImage);
        }

        //BEAUTÉ
        beauteTitle.setText("Beauté");
        beauteTitle.setTypeface(font);
        beauteTitle.setTextSize(30);
        beauteTitle.setGravity(Gravity.CENTER_HORIZONTAL);
        beauteTitleLayout.addView(beauteTitle);
        for (int i = 0; i < 20; i++) {
            ImageView beauteImage = new ImageView(getActivity());
            beauteImage.setLayoutParams(new android.view.ViewGroup.LayoutParams(80,60));
            if (i % 2 == 0)
                beauteImage.setImageResource(R.drawable.tiger);
            else if (i % 3 == 0)
                beauteImage.setImageResource(R.drawable.h2r);
            else if (i % 4 == 0)
                beauteImage.setImageResource(R.drawable.gsxr);
            else if (i % 5 == 0)
                beauteImage.setImageResource(R.drawable.kawa);
            else
                beauteImage.setImageResource(R.drawable.er6n);
            beauteImage.getLayoutParams().height = 150;
            beauteImage.getLayoutParams().width = 150;
            beauteTitleContent.addView(beauteImage);
        }

        //BIEN-ÊTRE
        bienEtreTitle.setText("Bien-être");
        bienEtreTitle.setTypeface(font);
        bienEtreTitle.setTextSize(30);
        bienEtreTitle.setGravity(Gravity.CENTER_HORIZONTAL);
        bienEtreTitleLayout.addView(bienEtreTitle);
        for (int i = 0; i < 20; i++) {
            ImageView bienEtreImage = new ImageView(getActivity());
            bienEtreImage.setLayoutParams(new android.view.ViewGroup.LayoutParams(80,60));
            if (i % 2 == 0)
                bienEtreImage.setImageResource(R.drawable.er6n);
            else if (i % 3 == 0)
                bienEtreImage.setImageResource(R.drawable.tiger);
            else if (i % 4 == 0)
                bienEtreImage.setImageResource(R.drawable.h2r);
            else if (i % 5 == 0)
                bienEtreImage.setImageResource(R.drawable.gsxr);
            else
                bienEtreImage.setImageResource(R.drawable.kawa);
            bienEtreImage.getLayoutParams().height = 150;
            bienEtreImage.getLayoutParams().width = 150;
            bienEtreTitleContent.addView(bienEtreImage);
        }

        //HOMME
        hommeTitle.setText("Homme");
        hommeTitle.setTypeface(font);
        hommeTitle.setTextSize(30);
        hommeTitle.setGravity(Gravity.CENTER_HORIZONTAL);
        hommeTitleLayout.addView(hommeTitle);
        for (int i = 0; i < 20; i++) {
            ImageView hommeImage = new ImageView(getActivity());
            hommeImage.setLayoutParams(new android.view.ViewGroup.LayoutParams(80,60));
            if (i % 2 == 0)
                hommeImage.setImageResource(R.drawable.kawa);
            else if (i % 3 == 0)
                hommeImage.setImageResource(R.drawable.er6n);
            else if (i % 4 == 0)
                hommeImage.setImageResource(R.drawable.tiger);
            else if (i % 5 == 0)
                hommeImage.setImageResource(R.drawable.h2r);
            else
                hommeImage.setImageResource(R.drawable.gsxr);
            hommeImage.getLayoutParams().height = 150;
            hommeImage.getLayoutParams().width = 150;
            hommeTitleContent.addView(hommeImage);
        }

    }


}
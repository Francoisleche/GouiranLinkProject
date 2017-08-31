package com.gouiranlink.franois.gouiranlinkproject.Homepage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gouiranlink.franois.gouiranlinkproject.Object.Customer;
import com.gouiranlink.franois.gouiranlinkproject.R;
import com.gouiranlink.franois.gouiranlinkproject.ToolsClasses.GetRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import static android.content.ContentValues.TAG;
import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by François on 20/07/2017.
 */

public class ArticleFragment extends Fragment{

    public static final String LOG_TAG = "HttpGet";
    private Customer customer;
    private int numero;
    String[] type_article = new String[0];



    ImageView image1_article,image2_article,image3_article,image4_article,image5_article,image6_article,image_editeur;
    TextView titre_article,text1_article,text2_article,text3_article,text4_article,text5_article,text6_article;
    TextView soustitre1_article,soustitre2_article,soustitre3_article,soustitre4_article,soustitre5_article;
    TextView credit_article,nom_prenom_editeur,poste_editeur;

    public ArticleFragment(){

    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            customer = (Customer) getArguments().getSerializable("Customer");
            numero = (int) getArguments().getSerializable("numero");
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = null;
        // Inflate the layout for this fragment




        String[] tableau_fichier = null;
        tableau_fichier = json_parser_article(article_api());
        String CurrentString = article_api2(tableau_fichier[numero]);
        String[] separated = CurrentString.split("////");

        System.out.println("type_article length : "+type_article.length);
        System.out.println("type_article : "+type_article[numero]);

        if(type_article[numero].equals("A0")){
            root = inflater.inflate(R.layout.gabarit_a0_article, null);

            image1_article = (ImageView) root.findViewById(R.id.image1_article);
            image2_article = (ImageView) root.findViewById(R.id.image2_article);
            image3_article = (ImageView) root.findViewById(R.id.image3_article);
            image_editeur = (ImageView) root.findViewById(R.id.image_editeur);

            titre_article = (TextView) root.findViewById(R.id.titre_article);

            text1_article = (TextView) root.findViewById(R.id.text1_article);
            text2_article = (TextView) root.findViewById(R.id.text2_article);
            text3_article = (TextView) root.findViewById(R.id.text3_article);
            text4_article = (TextView) root.findViewById(R.id.text4_article);

            soustitre1_article = (TextView) root.findViewById(R.id.soustitre1_article);
            soustitre2_article = (TextView) root.findViewById(R.id.soustitre2_article);
            soustitre3_article = (TextView) root.findViewById(R.id.soustitre3_article);

            credit_article = (TextView) root.findViewById(R.id.credit_article);
            nom_prenom_editeur = (TextView) root.findViewById(R.id.nom_prenom_editeur);
            poste_editeur = (TextView) root.findViewById(R.id.poste_editeur);
            article_A0(separated);
        }if(type_article[numero].equals("A")){
            root = inflater.inflate(R.layout.gabarit_a_article, null);

            image1_article = (ImageView) root.findViewById(R.id.image1_article);
            image2_article = (ImageView) root.findViewById(R.id.image2_article);
            image3_article = (ImageView) root.findViewById(R.id.image3_article);
            image_editeur = (ImageView) root.findViewById(R.id.image_editeur);

            titre_article = (TextView) root.findViewById(R.id.titre_article);

            text1_article = (TextView) root.findViewById(R.id.text1_article);
            text2_article = (TextView) root.findViewById(R.id.text2_article);
            text3_article = (TextView) root.findViewById(R.id.text3_article);
            text4_article = (TextView) root.findViewById(R.id.text4_article);

            soustitre1_article = (TextView) root.findViewById(R.id.soustitre1_article);
            soustitre2_article = (TextView) root.findViewById(R.id.soustitre2_article);
            soustitre3_article = (TextView) root.findViewById(R.id.soustitre3_article);

            credit_article = (TextView) root.findViewById(R.id.credit_article);
            nom_prenom_editeur = (TextView) root.findViewById(R.id.nom_prenom_editeur);
            poste_editeur = (TextView) root.findViewById(R.id.poste_editeur);
            article_A(separated);
        }if(type_article[numero].equals("B")){
            root = inflater.inflate(R.layout.gabarit_b_article, null);

            image1_article = (ImageView) root.findViewById(R.id.image1_article);
            image2_article = (ImageView) root.findViewById(R.id.image2_article);
            image3_article = (ImageView) root.findViewById(R.id.image3_article);
            image_editeur = (ImageView) root.findViewById(R.id.image_editeur);

            titre_article = (TextView) root.findViewById(R.id.titre_article);

            text1_article = (TextView) root.findViewById(R.id.text1_article);
            text2_article = (TextView) root.findViewById(R.id.text2_article);
            text3_article = (TextView) root.findViewById(R.id.text3_article);
            text4_article = (TextView) root.findViewById(R.id.text4_article);

            soustitre1_article = (TextView) root.findViewById(R.id.soustitre1_article);
            soustitre2_article = (TextView) root.findViewById(R.id.soustitre2_article);
            soustitre3_article = (TextView) root.findViewById(R.id.soustitre3_article);

            credit_article = (TextView) root.findViewById(R.id.credit_article);
            nom_prenom_editeur = (TextView) root.findViewById(R.id.nom_prenom_editeur);
            poste_editeur = (TextView) root.findViewById(R.id.poste_editeur);
            article_B(separated);
        }if(type_article[numero].equals("C")){
            root = inflater.inflate(R.layout.gabarit_c_article, null);

            image1_article = (ImageView) root.findViewById(R.id.image1_article);
            image2_article = (ImageView) root.findViewById(R.id.image2_article);
            image3_article = (ImageView) root.findViewById(R.id.image3_article);
            image_editeur = (ImageView) root.findViewById(R.id.image_editeur);

            titre_article = (TextView) root.findViewById(R.id.titre_article);

            text1_article = (TextView) root.findViewById(R.id.text1_article);
            text2_article = (TextView) root.findViewById(R.id.text2_article);
            text3_article = (TextView) root.findViewById(R.id.text3_article);
            text4_article = (TextView) root.findViewById(R.id.text4_article);

            soustitre1_article = (TextView) root.findViewById(R.id.soustitre1_article);
            soustitre2_article = (TextView) root.findViewById(R.id.soustitre2_article);
            soustitre3_article = (TextView) root.findViewById(R.id.soustitre3_article);

            credit_article = (TextView) root.findViewById(R.id.credit_article);
            nom_prenom_editeur = (TextView) root.findViewById(R.id.nom_prenom_editeur);
            poste_editeur = (TextView) root.findViewById(R.id.poste_editeur);
            article_C(separated);
        }if(type_article[numero].equals("D")){
            root = inflater.inflate(R.layout.gabarit_d_article, null);

            image1_article = (ImageView) root.findViewById(R.id.image1_article);
            image2_article = (ImageView) root.findViewById(R.id.image2_article);
            image3_article = (ImageView) root.findViewById(R.id.image3_article);
            image_editeur = (ImageView) root.findViewById(R.id.image_editeur);

            titre_article = (TextView) root.findViewById(R.id.titre_article);

            text1_article = (TextView) root.findViewById(R.id.text1_article);
            text2_article = (TextView) root.findViewById(R.id.text2_article);
            text3_article = (TextView) root.findViewById(R.id.text3_article);
            text4_article = (TextView) root.findViewById(R.id.text4_article);

            soustitre1_article = (TextView) root.findViewById(R.id.soustitre1_article);
            soustitre2_article = (TextView) root.findViewById(R.id.soustitre2_article);
            soustitre3_article = (TextView) root.findViewById(R.id.soustitre3_article);

            credit_article = (TextView) root.findViewById(R.id.credit_article);
            nom_prenom_editeur = (TextView) root.findViewById(R.id.nom_prenom_editeur);
            poste_editeur = (TextView) root.findViewById(R.id.poste_editeur);
            article_D(separated);
        }if(type_article[numero].equals("E")){
            root = inflater.inflate(R.layout.gabarit_e_article, null);

            image1_article = (ImageView) root.findViewById(R.id.image1_article);
            image2_article = (ImageView) root.findViewById(R.id.image2_article);
            image3_article = (ImageView) root.findViewById(R.id.image3_article);
            image_editeur = (ImageView) root.findViewById(R.id.image_editeur);

            titre_article = (TextView) root.findViewById(R.id.titre_article);

            text1_article = (TextView) root.findViewById(R.id.text1_article);
            text2_article = (TextView) root.findViewById(R.id.text2_article);
            text3_article = (TextView) root.findViewById(R.id.text3_article);
            text4_article = (TextView) root.findViewById(R.id.text4_article);

            soustitre1_article = (TextView) root.findViewById(R.id.soustitre1_article);
            soustitre2_article = (TextView) root.findViewById(R.id.soustitre2_article);
            soustitre3_article = (TextView) root.findViewById(R.id.soustitre3_article);

            credit_article = (TextView) root.findViewById(R.id.credit_article);
            nom_prenom_editeur = (TextView) root.findViewById(R.id.nom_prenom_editeur);
            poste_editeur = (TextView) root.findViewById(R.id.poste_editeur);
            article_E(separated);
        }if(type_article[numero].equals("F")){
            root = inflater.inflate(R.layout.gabarit_f_article, null);

            image1_article = (ImageView) root.findViewById(R.id.image1_article);
            image2_article = (ImageView) root.findViewById(R.id.image2_article);
            image3_article = (ImageView) root.findViewById(R.id.image3_article);
            image4_article = (ImageView) root.findViewById(R.id.image4_article);
            image_editeur = (ImageView) root.findViewById(R.id.image_editeur);

            titre_article = (TextView) root.findViewById(R.id.titre_article);

            text1_article = (TextView) root.findViewById(R.id.text1_article);
            text2_article = (TextView) root.findViewById(R.id.text2_article);
            text3_article = (TextView) root.findViewById(R.id.text3_article);
            text4_article = (TextView) root.findViewById(R.id.text4_article);

            soustitre1_article = (TextView) root.findViewById(R.id.soustitre1_article);
            soustitre2_article = (TextView) root.findViewById(R.id.soustitre2_article);
            soustitre3_article = (TextView) root.findViewById(R.id.soustitre3_article);

            credit_article = (TextView) root.findViewById(R.id.credit_article);
            nom_prenom_editeur = (TextView) root.findViewById(R.id.nom_prenom_editeur);
            poste_editeur = (TextView) root.findViewById(R.id.poste_editeur);
            article_F(separated);
        }if(type_article[numero].equals("G")){
            root = inflater.inflate(R.layout.gabarit_g_article, null);

            image1_article = (ImageView) root.findViewById(R.id.image1_article);
            image2_article = (ImageView) root.findViewById(R.id.image2_article);
            image3_article = (ImageView) root.findViewById(R.id.image3_article);
            image4_article = (ImageView) root.findViewById(R.id.image4_article);
            image_editeur = (ImageView) root.findViewById(R.id.image_editeur);

            titre_article = (TextView) root.findViewById(R.id.titre_article);

            text1_article = (TextView) root.findViewById(R.id.text1_article);
            text2_article = (TextView) root.findViewById(R.id.text2_article);
            text3_article = (TextView) root.findViewById(R.id.text3_article);
            text4_article = (TextView) root.findViewById(R.id.text4_article);
            text5_article = (TextView) root.findViewById(R.id.text5_article);

            soustitre1_article = (TextView) root.findViewById(R.id.soustitre1_article);
            soustitre2_article = (TextView) root.findViewById(R.id.soustitre2_article);
            soustitre3_article = (TextView) root.findViewById(R.id.soustitre3_article);
            soustitre4_article = (TextView) root.findViewById(R.id.soustitre4_article);

            credit_article = (TextView) root.findViewById(R.id.credit_article);
            nom_prenom_editeur = (TextView) root.findViewById(R.id.nom_prenom_editeur);
            poste_editeur = (TextView) root.findViewById(R.id.poste_editeur);
            article_G(separated);
        }if(type_article[numero].equals("H")){
            root = inflater.inflate(R.layout.gabarit_h_article, null);

            image1_article = (ImageView) root.findViewById(R.id.image1_article);
            image2_article = (ImageView) root.findViewById(R.id.image2_article);
            image3_article = (ImageView) root.findViewById(R.id.image3_article);
            image4_article = (ImageView) root.findViewById(R.id.image4_article);
            image_editeur = (ImageView) root.findViewById(R.id.image_editeur);

            titre_article = (TextView) root.findViewById(R.id.titre_article);

            text1_article = (TextView) root.findViewById(R.id.text1_article);
            text2_article = (TextView) root.findViewById(R.id.text2_article);
            text3_article = (TextView) root.findViewById(R.id.text3_article);
            text4_article = (TextView) root.findViewById(R.id.text4_article);
            text5_article = (TextView) root.findViewById(R.id.text5_article);

            soustitre1_article = (TextView) root.findViewById(R.id.soustitre1_article);
            soustitre2_article = (TextView) root.findViewById(R.id.soustitre2_article);
            soustitre3_article = (TextView) root.findViewById(R.id.soustitre3_article);
            soustitre4_article = (TextView) root.findViewById(R.id.soustitre4_article);

            credit_article = (TextView) root.findViewById(R.id.credit_article);
            nom_prenom_editeur = (TextView) root.findViewById(R.id.nom_prenom_editeur);
            poste_editeur = (TextView) root.findViewById(R.id.poste_editeur);
            article_H(separated);
        }if(type_article[numero].equals("I")){
            root = inflater.inflate(R.layout.gabarit_i_article, null);

            image1_article = (ImageView) root.findViewById(R.id.image1_article);
            image2_article = (ImageView) root.findViewById(R.id.image2_article);
            image3_article = (ImageView) root.findViewById(R.id.image3_article);
            image4_article = (ImageView) root.findViewById(R.id.image4_article);
            image_editeur = (ImageView) root.findViewById(R.id.image_editeur);

            titre_article = (TextView) root.findViewById(R.id.titre_article);

            text1_article = (TextView) root.findViewById(R.id.text1_article);
            text2_article = (TextView) root.findViewById(R.id.text2_article);
            text3_article = (TextView) root.findViewById(R.id.text3_article);
            text4_article = (TextView) root.findViewById(R.id.text4_article);
            text5_article = (TextView) root.findViewById(R.id.text5_article);

            soustitre1_article = (TextView) root.findViewById(R.id.soustitre1_article);
            soustitre2_article = (TextView) root.findViewById(R.id.soustitre2_article);
            soustitre3_article = (TextView) root.findViewById(R.id.soustitre3_article);
            soustitre4_article = (TextView) root.findViewById(R.id.soustitre4_article);

            credit_article = (TextView) root.findViewById(R.id.credit_article);
            nom_prenom_editeur = (TextView) root.findViewById(R.id.nom_prenom_editeur);
            poste_editeur = (TextView) root.findViewById(R.id.poste_editeur);
            article_I(separated);
        }if(type_article[numero].equals("J")){
            root = inflater.inflate(R.layout.gabarit_j_article, null);

            image1_article = (ImageView) root.findViewById(R.id.image1_article);
            image2_article = (ImageView) root.findViewById(R.id.image2_article);
            image3_article = (ImageView) root.findViewById(R.id.image3_article);
            image4_article = (ImageView) root.findViewById(R.id.image4_article);
            image5_article = (ImageView) root.findViewById(R.id.image5_article);
            image_editeur = (ImageView) root.findViewById(R.id.image_editeur);

            titre_article = (TextView) root.findViewById(R.id.titre_article);

            text1_article = (TextView) root.findViewById(R.id.text1_article);
            text2_article = (TextView) root.findViewById(R.id.text2_article);
            text3_article = (TextView) root.findViewById(R.id.text3_article);
            text4_article = (TextView) root.findViewById(R.id.text4_article);
            text5_article = (TextView) root.findViewById(R.id.text5_article);

            soustitre1_article = (TextView) root.findViewById(R.id.soustitre1_article);
            soustitre2_article = (TextView) root.findViewById(R.id.soustitre2_article);
            soustitre3_article = (TextView) root.findViewById(R.id.soustitre3_article);
            soustitre4_article = (TextView) root.findViewById(R.id.soustitre4_article);

            credit_article = (TextView) root.findViewById(R.id.credit_article);
            nom_prenom_editeur = (TextView) root.findViewById(R.id.nom_prenom_editeur);
            poste_editeur = (TextView) root.findViewById(R.id.poste_editeur);
            article_J(separated);
        }if(type_article[numero].equals("K")){
            root = inflater.inflate(R.layout.gabarit_k_article, null);

            image1_article = (ImageView) root.findViewById(R.id.image1_article);
            image2_article = (ImageView) root.findViewById(R.id.image2_article);
            image3_article = (ImageView) root.findViewById(R.id.image3_article);
            image4_article = (ImageView) root.findViewById(R.id.image4_article);
            image5_article = (ImageView) root.findViewById(R.id.image5_article);
            image_editeur = (ImageView) root.findViewById(R.id.image_editeur);

            titre_article = (TextView) root.findViewById(R.id.titre_article);

            text1_article = (TextView) root.findViewById(R.id.text1_article);
            text2_article = (TextView) root.findViewById(R.id.text2_article);
            text3_article = (TextView) root.findViewById(R.id.text3_article);
            text4_article = (TextView) root.findViewById(R.id.text4_article);
            text5_article = (TextView) root.findViewById(R.id.text5_article);
            text6_article = (TextView) root.findViewById(R.id.text6_article);

            soustitre1_article = (TextView) root.findViewById(R.id.soustitre1_article);
            soustitre2_article = (TextView) root.findViewById(R.id.soustitre2_article);
            soustitre3_article = (TextView) root.findViewById(R.id.soustitre3_article);
            soustitre4_article = (TextView) root.findViewById(R.id.soustitre4_article);
            soustitre5_article = (TextView) root.findViewById(R.id.soustitre5_article);

            credit_article = (TextView) root.findViewById(R.id.credit_article);
            nom_prenom_editeur = (TextView) root.findViewById(R.id.nom_prenom_editeur);
            poste_editeur = (TextView) root.findViewById(R.id.poste_editeur);
            article_K(separated);
        }if(type_article[numero].equals("L")){
            root = inflater.inflate(R.layout.gabarit_l_article, null);

            image1_article = (ImageView) root.findViewById(R.id.image1_article);
            image2_article = (ImageView) root.findViewById(R.id.image2_article);
            image3_article = (ImageView) root.findViewById(R.id.image3_article);
            image4_article = (ImageView) root.findViewById(R.id.image4_article);
            image5_article = (ImageView) root.findViewById(R.id.image5_article);
            image_editeur = (ImageView) root.findViewById(R.id.image_editeur);

            titre_article = (TextView) root.findViewById(R.id.titre_article);

            text1_article = (TextView) root.findViewById(R.id.text1_article);
            text2_article = (TextView) root.findViewById(R.id.text2_article);
            text3_article = (TextView) root.findViewById(R.id.text3_article);
            text4_article = (TextView) root.findViewById(R.id.text4_article);
            text5_article = (TextView) root.findViewById(R.id.text5_article);
            text6_article = (TextView) root.findViewById(R.id.text6_article);

            soustitre1_article = (TextView) root.findViewById(R.id.soustitre1_article);
            soustitre2_article = (TextView) root.findViewById(R.id.soustitre2_article);
            soustitre3_article = (TextView) root.findViewById(R.id.soustitre3_article);
            soustitre4_article = (TextView) root.findViewById(R.id.soustitre4_article);
            soustitre5_article = (TextView) root.findViewById(R.id.soustitre5_article);

            credit_article = (TextView) root.findViewById(R.id.credit_article);
            nom_prenom_editeur = (TextView) root.findViewById(R.id.nom_prenom_editeur);
            poste_editeur = (TextView) root.findViewById(R.id.poste_editeur);
            article_L(separated);
        }if(type_article[numero].equals("M")){
            root = inflater.inflate(R.layout.gabarit_m_article, null);

            image1_article = (ImageView) root.findViewById(R.id.image1_article);
            image2_article = (ImageView) root.findViewById(R.id.image2_article);
            image3_article = (ImageView) root.findViewById(R.id.image3_article);
            image4_article = (ImageView) root.findViewById(R.id.image4_article);
            image5_article = (ImageView) root.findViewById(R.id.image5_article);
            image_editeur = (ImageView) root.findViewById(R.id.image_editeur);

            titre_article = (TextView) root.findViewById(R.id.titre_article);

            text1_article = (TextView) root.findViewById(R.id.text1_article);
            text2_article = (TextView) root.findViewById(R.id.text2_article);
            text3_article = (TextView) root.findViewById(R.id.text3_article);
            text4_article = (TextView) root.findViewById(R.id.text4_article);
            text5_article = (TextView) root.findViewById(R.id.text5_article);
            text6_article = (TextView) root.findViewById(R.id.text6_article);

            soustitre1_article = (TextView) root.findViewById(R.id.soustitre1_article);
            soustitre2_article = (TextView) root.findViewById(R.id.soustitre2_article);
            soustitre3_article = (TextView) root.findViewById(R.id.soustitre3_article);
            soustitre4_article = (TextView) root.findViewById(R.id.soustitre4_article);
            soustitre5_article = (TextView) root.findViewById(R.id.soustitre5_article);

            credit_article = (TextView) root.findViewById(R.id.credit_article);
            nom_prenom_editeur = (TextView) root.findViewById(R.id.nom_prenom_editeur);
            poste_editeur = (TextView) root.findViewById(R.id.poste_editeur);
            article_M(separated);
        }if(type_article[numero].equals("N")){
            root = inflater.inflate(R.layout.gabarit_n_article, null);

            image1_article = (ImageView) root.findViewById(R.id.image1_article);
            image2_article = (ImageView) root.findViewById(R.id.image2_article);
            image3_article = (ImageView) root.findViewById(R.id.image3_article);
            image4_article = (ImageView) root.findViewById(R.id.image4_article);
            image5_article = (ImageView) root.findViewById(R.id.image5_article);
            image_editeur = (ImageView) root.findViewById(R.id.image_editeur);

            titre_article = (TextView) root.findViewById(R.id.titre_article);

            text1_article = (TextView) root.findViewById(R.id.text1_article);
            text2_article = (TextView) root.findViewById(R.id.text2_article);
            text3_article = (TextView) root.findViewById(R.id.text3_article);
            text4_article = (TextView) root.findViewById(R.id.text4_article);
            text5_article = (TextView) root.findViewById(R.id.text5_article);
            text6_article = (TextView) root.findViewById(R.id.text6_article);

            soustitre1_article = (TextView) root.findViewById(R.id.soustitre1_article);
            soustitre2_article = (TextView) root.findViewById(R.id.soustitre2_article);
            soustitre3_article = (TextView) root.findViewById(R.id.soustitre3_article);
            soustitre4_article = (TextView) root.findViewById(R.id.soustitre4_article);
            soustitre5_article = (TextView) root.findViewById(R.id.soustitre5_article);

            credit_article = (TextView) root.findViewById(R.id.credit_article);
            nom_prenom_editeur = (TextView) root.findViewById(R.id.nom_prenom_editeur);
            poste_editeur = (TextView) root.findViewById(R.id.poste_editeur);
            article_N(separated);
        }if(type_article[numero].equals("O")){
            root = inflater.inflate(R.layout.gabarit_o_article, null);

            image1_article = (ImageView) root.findViewById(R.id.image1_article);
            image2_article = (ImageView) root.findViewById(R.id.image2_article);
            image3_article = (ImageView) root.findViewById(R.id.image3_article);
            image4_article = (ImageView) root.findViewById(R.id.image4_article);
            image5_article = (ImageView) root.findViewById(R.id.image5_article);
            image6_article = (ImageView) root.findViewById(R.id.image6_article);
            image_editeur = (ImageView) root.findViewById(R.id.image_editeur);

            titre_article = (TextView) root.findViewById(R.id.titre_article);

            text1_article = (TextView) root.findViewById(R.id.text1_article);
            text2_article = (TextView) root.findViewById(R.id.text2_article);
            text3_article = (TextView) root.findViewById(R.id.text3_article);
            text4_article = (TextView) root.findViewById(R.id.text4_article);
            text5_article = (TextView) root.findViewById(R.id.text5_article);
            text6_article = (TextView) root.findViewById(R.id.text6_article);

            soustitre1_article = (TextView) root.findViewById(R.id.soustitre1_article);
            soustitre2_article = (TextView) root.findViewById(R.id.soustitre2_article);
            soustitre3_article = (TextView) root.findViewById(R.id.soustitre3_article);
            soustitre4_article = (TextView) root.findViewById(R.id.soustitre4_article);
            soustitre5_article = (TextView) root.findViewById(R.id.soustitre5_article);

            credit_article = (TextView) root.findViewById(R.id.credit_article);
            nom_prenom_editeur = (TextView) root.findViewById(R.id.nom_prenom_editeur);
            poste_editeur = (TextView) root.findViewById(R.id.poste_editeur);
            article_O(separated);
        }




        return root;
    }






    public String article_api(){
        GetRequest getRequest = new GetRequest("http://gouiran.link.free.fr/getallphotos.php");
        String resp = null;
        try {
            resp = getRequest.execute().get();
            System.out.println("Reeeeeeeeeeeeeeeeeeeeeeesp");
            System.out.println(resp.toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return resp;
    }

    public String[] json_parser_article(String str){
        String[] articlefichier = null;

        if (str != null) {
            try {

                JSONObject jsonObj = new JSONObject(str);
                JSONArray nb_article = jsonObj.getJSONArray("data");
                articlefichier = new String[nb_article.length()];
                type_article = new String[nb_article.length()];

                for (int i = 0; i < nb_article.length(); i++) {

                    JSONObject c = nb_article.getJSONObject(i);

                    articlefichier[i]=c.getString("fichier");

                    type_article[i] = c.getString("type_article");
                }

            } catch (final JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());
            }
        }

        return articlefichier;
    }

    public String article_api2(String s){
        GetRequest getRequest = new GetRequest(s);
        String resp = null;
        try {
            resp = getRequest.execute().get();
            System.out.println("Reeeeeeeeeeeeeeeeeeeeeeesp");
            System.out.println(resp.toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return resp;
    }







    public void article_A0(String[] separated){
        titre_article.setText(separated[0]);
        if (separated[1] == "") {
            image1_article.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.unknown));
        } else {
            separated[1].replace(" ","");
            separated[1].replace("\n","");
            System.out.println("imaaaaaage : "+separated[1]);
            Picasso.with(getContext()).load(separated[1])
                    .into(image1_article);
        }
        text1_article.setText(separated[2]);
        soustitre1_article.setText(separated[3]);
        text2_article.setText(separated[4]);
        soustitre2_article.setText(separated[5]);
        text3_article.setText(separated[6]);

        if (separated[7] == "") {
            image2_article.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.unknown));
        } else {
            separated[7].replace(" ","");
            separated[7].replace("\n","");
            Picasso.with(getContext()).load(separated[7])
                    .into(image2_article);
        }

        soustitre3_article.setText(separated[8]);
        text4_article.setText(separated[9]);

        if (separated[10] == "") {
            image_editeur.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.unknown));
        } else {
            separated[10].replace(" ","");
            separated[10].replace("\n","");
            System.out.println("imaaaaaage : "+separated[10]);
            Picasso.with(getContext()).load(separated[10])
                    .into(image_editeur);
        }

        nom_prenom_editeur.setText(separated[11]);
        poste_editeur.setText(separated[12]);

    }

    public void article_A(String[] separated){
        titre_article.setText(separated[0]);
        if (separated[1] == "") {
            image1_article.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.unknown));
        } else {
            separated[1].replace(" ","");
            separated[1].replace("\n","");
            System.out.println("imaaaaaage : "+separated[1]);
            Picasso.with(getContext()).load(separated[1])
                    .into(image1_article);
        }
        text1_article.setText(separated[2]);
        soustitre1_article.setText(separated[3]);
        text2_article.setText(separated[4]);


        if (separated[5] == "") {
            image2_article.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.unknown));
        } else {
            separated[5].replace(" ","");
            separated[5].replace("\n","");
            Picasso.with(getContext()).load(separated[5])
                    .into(image2_article);
        }

        if (separated[6] == "") {
            image_editeur.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.unknown));
        } else {
            separated[6].replace(" ","");
            separated[6].replace("\n","");
            System.out.println("imaaaaaage : "+separated[6]);
            Picasso.with(getContext()).load(separated[6])
                    .into(image_editeur);
        }

        nom_prenom_editeur.setText(separated[7]);
        poste_editeur.setText(separated[8]);

    }

    public void article_B(String[] separated){
        titre_article.setText(separated[0]);
        if (separated[1] == "") {
            image1_article.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.unknown));
        } else {
            separated[1].replace(" ","");
            separated[1].replace("\n","");
            System.out.println("imaaaaaage : "+separated[1]);
            Picasso.with(getContext()).load(separated[1])
                    .into(image1_article);
        }
        text1_article.setText(separated[2]);
        soustitre1_article.setText(separated[3]);
        text2_article.setText(separated[4]);


        if (separated[5] == "") {
            image2_article.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.unknown));
        } else {
            separated[5].replace(" ","");
            separated[5].replace("\n","");
            Picasso.with(getContext()).load(separated[5])
                    .into(image2_article);
        }

        soustitre2_article.setText(separated[6]);
        text3_article.setText(separated[7]);


        if (separated[8] == "") {
            image_editeur.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.unknown));
        } else {
            separated[8].replace(" ","");
            separated[8].replace("\n","");
            System.out.println("imaaaaaage : "+separated[8]);
            Picasso.with(getContext()).load(separated[8])
                    .into(image_editeur);
        }

        nom_prenom_editeur.setText(separated[9]);
        poste_editeur.setText(separated[10]);

    }

    public void article_C(String[] separated){
        titre_article.setText(separated[0]);
        if (separated[1] == "") {
            image1_article.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.unknown));
        } else {
            separated[1].replace(" ","");
            separated[1].replace("\n","");
            System.out.println("imaaaaaage : "+separated[1]);
            Picasso.with(getContext()).load(separated[1])
                    .into(image1_article);
        }
        text1_article.setText(separated[2]);
        soustitre1_article.setText(separated[3]);
        text2_article.setText(separated[4]);


        if (separated[5] == "") {
            image2_article.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.unknown));
        } else {
            separated[5].replace(" ","");
            separated[5].replace("\n","");
            Picasso.with(getContext()).load(separated[5])
                    .into(image2_article);
        }

        soustitre2_article.setText(separated[6]);
        text3_article.setText(separated[7]);

        if (separated[8] == "") {
            image3_article.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.unknown));
        } else {
            separated[8].replace(" ","");
            separated[8].replace("\n","");
            Picasso.with(getContext()).load(separated[8])
                    .into(image3_article);
        }


        if (separated[9] == "") {
            image_editeur.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.unknown));
        } else {
            separated[9].replace(" ","");
            separated[9].replace("\n","");
            System.out.println("imaaaaaage : "+separated[9]);
            Picasso.with(getContext()).load(separated[9])
                    .into(image_editeur);
        }

        nom_prenom_editeur.setText(separated[10]);
        poste_editeur.setText(separated[11]);

    }

    public void article_D(String[] separated){
        titre_article.setText(separated[0]);
        if (separated[1] == "") {
            image1_article.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.unknown));
        } else {
            separated[1].replace(" ","");
            separated[1].replace("\n","");
            System.out.println("imaaaaaage : "+separated[1]);
            Picasso.with(getContext()).load(separated[1])
                    .into(image1_article);
        }
        text1_article.setText(separated[2]);
        soustitre1_article.setText(separated[3]);
        text2_article.setText(separated[4]);


        if (separated[5] == "") {
            image2_article.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.unknown));
        } else {
            separated[5].replace(" ","");
            separated[5].replace("\n","");
            Picasso.with(getContext()).load(separated[5])
                    .into(image2_article);
        }

        soustitre2_article.setText(separated[6]);
        text3_article.setText(separated[7]);

        soustitre3_article.setText(separated[8]);
        text4_article.setText(separated[9]);



        if (separated[10] == "") {
            image_editeur.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.unknown));
        } else {
            separated[10].replace(" ","");
            separated[10].replace("\n","");
            System.out.println("imaaaaaage : "+separated[10]);
            Picasso.with(getContext()).load(separated[10])
                    .into(image_editeur);
        }

        nom_prenom_editeur.setText(separated[11]);
        poste_editeur.setText(separated[12]);

    }

    public void article_E(String[] separated){
        titre_article.setText(separated[0]);
        if (separated[1] == "") {
            image1_article.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.unknown));
        } else {
            separated[1].replace(" ","");
            separated[1].replace("\n","");
            System.out.println("imaaaaaage : "+separated[1]);
            Picasso.with(getContext()).load(separated[1])
                    .into(image1_article);
        }
        text1_article.setText(separated[2]);
        soustitre1_article.setText(separated[3]);
        text2_article.setText(separated[4]);


        if (separated[5] == "") {
            image2_article.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.unknown));
        } else {
            separated[5].replace(" ","");
            separated[5].replace("\n","");
            Picasso.with(getContext()).load(separated[5])
                    .into(image2_article);
        }

        soustitre2_article.setText(separated[6]);
        text3_article.setText(separated[7]);

        if (separated[8] == "") {
            image3_article.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.unknown));
        } else {
            separated[8].replace(" ","");
            separated[8].replace("\n","");
            Picasso.with(getContext()).load(separated[8])
                    .into(image3_article);
        }

        soustitre3_article.setText(separated[9]);
        text4_article.setText(separated[10]);



        if (separated[11] == "") {
            image_editeur.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.unknown));
        } else {
            separated[11].replace(" ","");
            separated[11].replace("\n","");
            System.out.println("imaaaaaage : "+separated[11]);
            Picasso.with(getContext()).load(separated[11])
                    .into(image_editeur);
        }

        nom_prenom_editeur.setText(separated[12]);
        poste_editeur.setText(separated[13]);

    }

    public void article_F(String[] separated){
        titre_article.setText(separated[0]);
        if (separated[1] == "") {
            image1_article.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.unknown));
        } else {
            separated[1].replace(" ","");
            separated[1].replace("\n","");
            System.out.println("imaaaaaage : "+separated[1]);
            Picasso.with(getContext()).load(separated[1])
                    .into(image1_article);
        }
        text1_article.setText(separated[2]);
        soustitre1_article.setText(separated[3]);
        text2_article.setText(separated[4]);

        if (separated[5] == "") {
            image2_article.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.unknown));
        } else {
            separated[5].replace(" ","");
            separated[5].replace("\n","");
            Picasso.with(getContext()).load(separated[5])
                    .into(image2_article);
        }

        soustitre2_article.setText(separated[6]);
        text3_article.setText(separated[7]);

        if (separated[8] == "") {
            image3_article.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.unknown));
        } else {
            separated[8].replace(" ","");
            separated[8].replace("\n","");
            Picasso.with(getContext()).load(separated[8])
                    .into(image3_article);
        }

        soustitre3_article.setText(separated[9]);
        text4_article.setText(separated[10]);

        if (separated[11] == "") {
            image4_article.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.unknown));
        } else {
            separated[11].replace(" ","");
            separated[11].replace("\n","");
            Picasso.with(getContext()).load(separated[11])
                    .into(image4_article);
        }


        if (separated[12] == "") {
            image_editeur.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.unknown));
        } else {
            separated[12].replace(" ","");
            separated[12].replace("\n","");
            System.out.println("imaaaaaage : "+separated[12]);
            Picasso.with(getContext()).load(separated[12])
                    .into(image_editeur);
        }

        nom_prenom_editeur.setText(separated[13]);
        poste_editeur.setText(separated[14]);



    }

    public void article_G(String[] separated){
        titre_article.setText(separated[0]);
        if (separated[1] == "") {
            image1_article.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.unknown));
        } else {
            separated[1].replace(" ","");
            separated[1].replace("\n","");
            System.out.println("imaaaaaage : "+separated[1]);
            Picasso.with(getContext()).load(separated[1])
                    .into(image1_article);
        }
        text1_article.setText(separated[2]);
        soustitre1_article.setText(separated[3]);
        text2_article.setText(separated[4]);

        if (separated[5] == "") {
            image2_article.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.unknown));
        } else {
            separated[5].replace(" ","");
            separated[5].replace("\n","");
            Picasso.with(getContext()).load(separated[5])
                    .into(image2_article);
        }

        soustitre2_article.setText(separated[6]);
        text3_article.setText(separated[7]);

        soustitre3_article.setText(separated[8]);
        text4_article.setText(separated[9]);

        soustitre4_article.setText(separated[10]);
        text5_article.setText(separated[11]);


        if (separated[12] == "") {
            image_editeur.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.unknown));
        } else {
            separated[12].replace(" ","");
            separated[12].replace("\n","");
            System.out.println("imaaaaaage : "+separated[12]);
            Picasso.with(getContext()).load(separated[12])
                    .into(image_editeur);
        }

        nom_prenom_editeur.setText(separated[13]);
        poste_editeur.setText(separated[14]);



    }

    public void article_H(String[] separated){
        titre_article.setText(separated[0]);
        if (separated[1] == "") {
            image1_article.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.unknown));
        } else {
            separated[1].replace(" ","");
            separated[1].replace("\n","");
            System.out.println("imaaaaaage : "+separated[1]);
            Picasso.with(getContext()).load(separated[1])
                    .into(image1_article);
        }
        text1_article.setText(separated[2]);
        soustitre1_article.setText(separated[3]);
        text2_article.setText(separated[4]);

        if (separated[5] == "") {
            image2_article.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.unknown));
        } else {
            separated[5].replace(" ","");
            separated[5].replace("\n","");
            Picasso.with(getContext()).load(separated[5])
                    .into(image2_article);
        }

        soustitre2_article.setText(separated[6]);
        text3_article.setText(separated[7]);

        if (separated[8] == "") {
            image3_article.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.unknown));
        } else {
            separated[8].replace(" ","");
            separated[8].replace("\n","");
            Picasso.with(getContext()).load(separated[8])
                    .into(image3_article);
        }

        soustitre3_article.setText(separated[9]);
        text4_article.setText(separated[10]);

        soustitre4_article.setText(separated[11]);
        text5_article.setText(separated[12]);


        if (separated[13] == "") {
            image_editeur.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.unknown));
        } else {
            separated[13].replace(" ","");
            separated[13].replace("\n","");
            System.out.println("imaaaaaage : "+separated[13]);
            Picasso.with(getContext()).load(separated[13])
                    .into(image_editeur);
        }

        nom_prenom_editeur.setText(separated[14]);
        poste_editeur.setText(separated[15]);

    }

    public void article_I(String[] separated){
        titre_article.setText(separated[0]);
        if (separated[1] == "") {
            image1_article.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.unknown));
        } else {
            separated[1].replace(" ","");
            separated[1].replace("\n","");
            System.out.println("imaaaaaage : "+separated[1]);
            Picasso.with(getContext()).load(separated[1])
                    .into(image1_article);
        }
        text1_article.setText(separated[2]);
        soustitre1_article.setText(separated[3]);
        text2_article.setText(separated[4]);

        if (separated[5] == "") {
            image2_article.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.unknown));
        } else {
            separated[5].replace(" ","");
            separated[5].replace("\n","");
            Picasso.with(getContext()).load(separated[5])
                    .into(image2_article);
        }

        soustitre2_article.setText(separated[6]);
        text3_article.setText(separated[7]);

        if (separated[8] == "") {
            image3_article.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.unknown));
        } else {
            separated[8].replace(" ","");
            separated[8].replace("\n","");
            Picasso.with(getContext()).load(separated[8])
                    .into(image3_article);
        }

        soustitre3_article.setText(separated[9]);
        text4_article.setText(separated[10]);

        if (separated[11] == "") {
            image4_article.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.unknown));
        } else {
            separated[11].replace(" ","");
            separated[11].replace("\n","");
            Picasso.with(getContext()).load(separated[11])
                    .into(image4_article);
        }

        soustitre4_article.setText(separated[12]);
        text5_article.setText(separated[13]);


        if (separated[14] == "") {
            image_editeur.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.unknown));
        } else {
            separated[14].replace(" ","");
            separated[14].replace("\n","");
            System.out.println("imaaaaaage : "+separated[14]);
            Picasso.with(getContext()).load(separated[14])
                    .into(image_editeur);
        }

        nom_prenom_editeur.setText(separated[15]);
        poste_editeur.setText(separated[16]);

    }

    public void article_J(String[] separated){
        titre_article.setText(separated[0]);
        if (separated[1] == "") {
            image1_article.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.unknown));
        } else {
            separated[1].replace(" ","");
            separated[1].replace("\n","");
            System.out.println("imaaaaaage : "+separated[1]);
            Picasso.with(getContext()).load(separated[1])
                    .into(image1_article);
        }
        text1_article.setText(separated[2]);
        soustitre1_article.setText(separated[3]);
        text2_article.setText(separated[4]);

        if (separated[5] == "") {
            image2_article.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.unknown));
        } else {
            separated[5].replace(" ","");
            separated[5].replace("\n","");
            Picasso.with(getContext()).load(separated[5])
                    .into(image2_article);
        }

        soustitre2_article.setText(separated[6]);
        text3_article.setText(separated[7]);

        if (separated[8] == "") {
            image3_article.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.unknown));
        } else {
            separated[8].replace(" ","");
            separated[8].replace("\n","");
            Picasso.with(getContext()).load(separated[8])
                    .into(image3_article);
        }

        soustitre3_article.setText(separated[9]);
        text4_article.setText(separated[10]);

        if (separated[11] == "") {
            image4_article.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.unknown));
        } else {
            separated[11].replace(" ","");
            separated[11].replace("\n","");
            Picasso.with(getContext()).load(separated[11])
                    .into(image4_article);
        }

        soustitre4_article.setText(separated[12]);
        text5_article.setText(separated[13]);

        if (separated[14] == "") {
            image5_article.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.unknown));
        } else {
            separated[14].replace(" ","");
            separated[14].replace("\n","");
            Picasso.with(getContext()).load(separated[14])
                    .into(image5_article);
        }


        if (separated[15] == "") {
            image_editeur.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.unknown));
        } else {
            separated[15].replace(" ","");
            separated[15].replace("\n","");
            System.out.println("imaaaaaage : "+separated[15]);
            Picasso.with(getContext()).load(separated[15])
                    .into(image_editeur);
        }

        nom_prenom_editeur.setText(separated[16]);
        poste_editeur.setText(separated[17]);

    }

    public void article_K(String[] separated){
        titre_article.setText(separated[0]);
        if (separated[1] == "") {
            image1_article.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.unknown));
        } else {
            separated[1].replace(" ","");
            separated[1].replace("\n","");
            System.out.println("imaaaaaage : "+separated[1]);
            Picasso.with(getContext()).load(separated[1])
                    .into(image1_article);
        }
        text1_article.setText(separated[2]);
        soustitre1_article.setText(separated[3]);
        text2_article.setText(separated[4]);

        if (separated[5] == "") {
            image2_article.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.unknown));
        } else {
            separated[5].replace(" ","");
            separated[5].replace("\n","");
            Picasso.with(getContext()).load(separated[5])
                    .into(image2_article);
        }

        soustitre2_article.setText(separated[6]);
        text3_article.setText(separated[7]);

        soustitre3_article.setText(separated[8]);
        text4_article.setText(separated[9]);

        soustitre4_article.setText(separated[10]);
        text5_article.setText(separated[11]);

        soustitre5_article.setText(separated[12]);
        text6_article.setText(separated[13]);



        if (separated[14] == "") {
            image_editeur.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.unknown));
        } else {
            separated[14].replace(" ","");
            separated[14].replace("\n","");
            System.out.println("imaaaaaage : "+separated[14]);
            Picasso.with(getContext()).load(separated[14])
                    .into(image_editeur);
        }

        nom_prenom_editeur.setText(separated[15]);
        poste_editeur.setText(separated[16]);

    }

    public void article_L(String[] separated){
        titre_article.setText(separated[0]);
        if (separated[1] == "") {
            image1_article.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.unknown));
        } else {
            separated[1].replace(" ","");
            separated[1].replace("\n","");
            System.out.println("imaaaaaage : "+separated[1]);
            Picasso.with(getContext()).load(separated[1])
                    .into(image1_article);
        }
        text1_article.setText(separated[2]);
        soustitre1_article.setText(separated[3]);
        text2_article.setText(separated[4]);

        if (separated[5] == "") {
            image2_article.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.unknown));
        } else {
            separated[5].replace(" ","");
            separated[5].replace("\n","");
            Picasso.with(getContext()).load(separated[5])
                    .into(image2_article);
        }

        soustitre2_article.setText(separated[6]);
        text3_article.setText(separated[7]);

        if (separated[8] == "") {
            image3_article.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.unknown));
        } else {
            separated[8].replace(" ","");
            separated[8].replace("\n","");
            Picasso.with(getContext()).load(separated[8])
                    .into(image3_article);
        }

        soustitre3_article.setText(separated[9]);
        text4_article.setText(separated[10]);

        soustitre4_article.setText(separated[11]);
        text5_article.setText(separated[12]);

        soustitre5_article.setText(separated[13]);
        text6_article.setText(separated[14]);



        if (separated[15] == "") {
            image_editeur.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.unknown));
        } else {
            separated[15].replace(" ","");
            separated[15].replace("\n","");
            System.out.println("imaaaaaage : "+separated[15]);
            Picasso.with(getContext()).load(separated[15])
                    .into(image_editeur);
        }

        nom_prenom_editeur.setText(separated[16]);
        poste_editeur.setText(separated[17]);

    }

    public void article_M(String[] separated){
        titre_article.setText(separated[0]);
        if (separated[1] == "") {
            image1_article.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.unknown));
        } else {
            separated[1].replace(" ","");
            separated[1].replace("\n","");
            System.out.println("imaaaaaage : "+separated[1]);
            Picasso.with(getContext()).load(separated[1])
                    .into(image1_article);
        }
        text1_article.setText(separated[2]);
        soustitre1_article.setText(separated[3]);
        text2_article.setText(separated[4]);

        if (separated[5] == "") {
            image2_article.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.unknown));
        } else {
            separated[5].replace(" ","");
            separated[5].replace("\n","");
            Picasso.with(getContext()).load(separated[5])
                    .into(image2_article);
        }

        soustitre2_article.setText(separated[6]);
        text3_article.setText(separated[7]);

        if (separated[8] == "") {
            image3_article.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.unknown));
        } else {
            separated[8].replace(" ","");
            separated[8].replace("\n","");
            Picasso.with(getContext()).load(separated[8])
                    .into(image3_article);
        }

        soustitre3_article.setText(separated[9]);
        text4_article.setText(separated[10]);

        if (separated[11] == "") {
            image4_article.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.unknown));
        } else {
            separated[11].replace(" ","");
            separated[11].replace("\n","");
            Picasso.with(getContext()).load(separated[11])
                    .into(image4_article);
        }

        soustitre4_article.setText(separated[12]);
        text5_article.setText(separated[13]);

        soustitre5_article.setText(separated[14]);
        text6_article.setText(separated[15]);



        if (separated[16] == "") {
            image_editeur.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.unknown));
        } else {
            separated[16].replace(" ","");
            separated[16].replace("\n","");
            System.out.println("imaaaaaage : "+separated[16]);
            Picasso.with(getContext()).load(separated[16])
                    .into(image_editeur);
        }

        nom_prenom_editeur.setText(separated[17]);
        poste_editeur.setText(separated[18]);

    }

    public void article_N(String[] separated){
        titre_article.setText(separated[0]);
        if (separated[1] == "") {
            image1_article.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.unknown));
        } else {
            separated[1].replace(" ","");
            separated[1].replace("\n","");
            System.out.println("imaaaaaage : "+separated[1]);
            Picasso.with(getContext()).load(separated[1])
                    .into(image1_article);
        }
        text1_article.setText(separated[2]);
        soustitre1_article.setText(separated[3]);
        text2_article.setText(separated[4]);

        if (separated[5] == "") {
            image2_article.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.unknown));
        } else {
            separated[5].replace(" ","");
            separated[5].replace("\n","");
            Picasso.with(getContext()).load(separated[5])
                    .into(image2_article);
        }

        soustitre2_article.setText(separated[6]);
        text3_article.setText(separated[7]);

        if (separated[8] == "") {
            image3_article.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.unknown));
        } else {
            separated[8].replace(" ","");
            separated[8].replace("\n","");
            Picasso.with(getContext()).load(separated[8])
                    .into(image3_article);
        }

        soustitre3_article.setText(separated[9]);
        text4_article.setText(separated[10]);

        if (separated[11] == "") {
            image4_article.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.unknown));
        } else {
            separated[11].replace(" ","");
            separated[11].replace("\n","");
            Picasso.with(getContext()).load(separated[11])
                    .into(image4_article);
        }

        soustitre4_article.setText(separated[12]);
        text5_article.setText(separated[13]);

        if (separated[14] == "") {
            image5_article.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.unknown));
        } else {
            separated[14].replace(" ","");
            separated[14].replace("\n","");
            Picasso.with(getContext()).load(separated[14])
                    .into(image5_article);
        }

        soustitre5_article.setText(separated[15]);
        text6_article.setText(separated[16]);

        if (separated[17] == "") {
            image_editeur.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.unknown));
        } else {
            separated[17].replace(" ","");
            separated[17].replace("\n","");
            System.out.println("imaaaaaage : "+separated[17]);
            Picasso.with(getContext()).load(separated[17])
                    .into(image_editeur);
        }

        nom_prenom_editeur.setText(separated[18]);
        poste_editeur.setText(separated[19]);

    }

    public void article_O(String[] separated){
        titre_article.setText(separated[0]);
        if (separated[1] == "") {
            image1_article.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.unknown));
        } else {
            separated[1].replace(" ","");
            separated[1].replace("\n","");
            System.out.println("imaaaaaage : "+separated[1]);
            Picasso.with(getContext()).load(separated[1])
                    .into(image1_article);
        }
        text1_article.setText(separated[2]);
        soustitre1_article.setText(separated[3]);
        text2_article.setText(separated[4]);

        if (separated[5] == "") {
            image2_article.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.unknown));
        } else {
            separated[5].replace(" ","");
            separated[5].replace("\n","");
            Picasso.with(getContext()).load(separated[5])
                    .into(image2_article);
        }

        soustitre2_article.setText(separated[6]);
        text3_article.setText(separated[7]);

        if (separated[8] == "") {
            image3_article.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.unknown));
        } else {
            separated[8].replace(" ","");
            separated[8].replace("\n","");
            Picasso.with(getContext()).load(separated[8])
                    .into(image3_article);
        }

        soustitre3_article.setText(separated[9]);
        text4_article.setText(separated[10]);

        if (separated[11] == "") {
            image4_article.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.unknown));
        } else {
            separated[11].replace(" ","");
            separated[11].replace("\n","");
            Picasso.with(getContext()).load(separated[11])
                    .into(image4_article);
        }

        soustitre4_article.setText(separated[12]);
        text5_article.setText(separated[13]);

        if (separated[14] == "") {
            image5_article.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.unknown));
        } else {
            separated[14].replace(" ","");
            separated[14].replace("\n","");
            Picasso.with(getContext()).load(separated[14])
                    .into(image5_article);
        }

        soustitre5_article.setText(separated[15]);
        text6_article.setText(separated[16]);

        if (separated[17] == "") {
            image6_article.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.unknown));
        } else {
            separated[17].replace(" ","");
            separated[17].replace("\n","");
            Picasso.with(getContext()).load(separated[17])
                    .into(image6_article);
        }


        if (separated[18] == "") {
            image_editeur.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.unknown));
        } else {
            separated[18].replace(" ","");
            separated[18].replace("\n","");
            System.out.println("imaaaaaage : "+separated[18]);
            Picasso.with(getContext()).load(separated[18])
                    .into(image_editeur);
        }

        nom_prenom_editeur.setText(separated[19]);
        poste_editeur.setText(separated[20]);

    }


}

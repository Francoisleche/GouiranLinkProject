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
        View root;
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.gabarit_a0_article, null);


        ImageView image1_article = (ImageView) root.findViewById(R.id.image1_article);
        ImageView image2_article = (ImageView) root.findViewById(R.id.image2_article);
        ImageView image3_article = (ImageView) root.findViewById(R.id.image3_article);

        TextView titre_article = (TextView) root.findViewById(R.id.titre_article);

        TextView text1_article = (TextView) root.findViewById(R.id.text1_article);
        TextView text2_article = (TextView) root.findViewById(R.id.text2_article);
        TextView text3_article = (TextView) root.findViewById(R.id.text3_article);
        TextView text4_article = (TextView) root.findViewById(R.id.text4_article);

        TextView soustitre1_article = (TextView) root.findViewById(R.id.soustitre1_article);
        TextView soustitre2_article = (TextView) root.findViewById(R.id.soustitre2_article);
        TextView soustitre3_article = (TextView) root.findViewById(R.id.soustitre3_article);

        TextView credit_article = (TextView) root.findViewById(R.id.credit_article);
        TextView nom_prenom_editeur = (TextView) root.findViewById(R.id.nom_prenom_editeur);



        String[] tableau_fichier = null;
        tableau_fichier = json_parser_article(article_api());

        String CurrentString = article_api2(tableau_fichier[numero]);

        String[] separated = CurrentString.split("////");
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

                for (int i = 0; i < nb_article.length(); i++) {

                    JSONObject c = nb_article.getJSONObject(i);

                    articlefichier[i]=c.getString("fichier");
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






}

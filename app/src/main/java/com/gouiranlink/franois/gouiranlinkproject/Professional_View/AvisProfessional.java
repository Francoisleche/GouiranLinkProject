package com.gouiranlink.franois.gouiranlinkproject.Professional_View;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.analytics.ecommerce.Product;
import com.gouiranlink.franois.gouiranlinkproject.Object.Comment;
import com.gouiranlink.franois.gouiranlinkproject.Object.Customer;
import com.gouiranlink.franois.gouiranlinkproject.Object.Professional;
import com.gouiranlink.franois.gouiranlinkproject.Object.Professional_Product;
import com.gouiranlink.franois.gouiranlinkproject.Object.Professional_Schedule;
import com.gouiranlink.franois.gouiranlinkproject.R;
import com.gouiranlink.franois.gouiranlinkproject.Recherche.ResearchFragment;
import com.gouiranlink.franois.gouiranlinkproject.ToolsClasses.GetRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import static android.content.ContentValues.TAG;

/**
 * Created by François on 01/02/2017.
 */

public class AvisProfessional extends Fragment{

    private GetRequest getRequest;
    private ResearchFragment.ResearchTask mAuthTask = null;
    private Professional professional;
    private Customer customer;
    private String accessToken;
    public ListView lstview_comment;
    private Comment[] commentaires = null;

    public AvisProfessional(){
// Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Récupération de l'objet Professionnal
        if (getArguments() != null) {
            professional = (Professional)getArguments().getSerializable("Professionnal");
            customer = (Customer) getArguments().getSerializable("Customer");
            accessToken = (String)getArguments().getString("token");
            System.out.println("Toooooooooooooken"+accessToken);
        }
        System.out.println("Maaaaaaaaaaaaaaaarche bien :"+professional.toString());
        //
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_avis_professional, container, false);

        avis_jsonparser(recherche_avis(String.valueOf(professional.getId())));



        RatingBar rtbProductRating = (RatingBar) view.findViewById(R.id.rtbProductRating);
        TextView moyenne_avis = (TextView) view.findViewById(R.id.moyenne_avis);

        lstview_comment=(ListView) view.findViewById(R.id.malistecommentaires);

        LinearLayout commentaire_existant = (LinearLayout) view.findViewById(R.id.commentaire_existant);
        LinearLayout pas_de_commentaire = (LinearLayout) view.findViewById(R.id.pas_de_commentaire);


        //Si le prestataire possède des commentaires
        if(commentaires.length!=1){
            commentaire_existant.setVisibility(true ? View.VISIBLE : View.GONE);
            pas_de_commentaire.setVisibility(true ? View.GONE : View.VISIBLE);

            int layout = R.layout.services3_avis;
            int id = R.id.comment_text;
            String[] items = new String[commentaires.length];
            for(int i =0;i<commentaires.length;i++){
                items[i]=commentaires[i].getCustomer().getName();
            }
            AvisAdapter adapter=new AvisAdapter(getActivity(),layout,id,items,commentaires,professional);
            lstview_comment.setAdapter(adapter);


            //Moyenne des avis
            double cal = 0;
            for(int i =1;i<commentaires.length;i++){
                System.out.println("Oooooooooooh avis : "+commentaires[i].getGrade());
                cal = cal+ (double)commentaires[i].getGrade();
            }
            cal = cal / (commentaires.length-1);
            System.out.println("moyeeeeeeennne : "+cal);
            rtbProductRating.setRating(Float.parseFloat(moyenne(String.valueOf(cal))));
            System.out.println("moyeeeeeeennne : "+Float.parseFloat(moyenne(String.valueOf(cal))));
            moyenne_avis.setText(moyenne(String.valueOf(cal))+"/5");


        }else{
            pas_de_commentaire.setVisibility(true ? View.VISIBLE : View.GONE);
            commentaire_existant.setVisibility(true ? View.GONE : View.VISIBLE);
        }




        return view;
    }



    public String recherche_avis(String query) {
        getRequest = new GetRequest("https://www.gouiran-beaute.com/link/api/v1/comment/professional/"+query+"/");
        String resp = null;
        try {
            resp = getRequest.execute().get();
            System.out.println("Rechercheeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
            System.out.println(resp.toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return resp;
    }



    public Comment[] avis_jsonparser(String str) {


        try {

            /*String recup_schedule = "{" + '"' + "data" + '"' + " : [{";
            String schedule1 = jsonStr.replace("[{", recup_schedule);
            String schedule2 = schedule1.replace("}]", "}]}");*/

            if (!str.equals("[]")) {
                JSONObject jsonObj = new JSONObject(str);
                //JSONArray contacts = jsonObj.getJSONArray("data");
                JSONArray tab_comment = jsonObj.getJSONArray("data");



                commentaires = new Comment[tab_comment.length()+1];

                //Remplir le premier commentaire à vide
                int iterateur = 1;
                Comment com1 = new Comment();
                Customer c1 = new Customer();
                c1.setName("");
                com1.setCustomer(c1);
                commentaires[0]=com1;

                //Remplir la liste des commentaires
                for (int j = 0; j < tab_comment.length(); j++) {
                    Comment com = new Comment();
                    JSONObject p2 = tab_comment.getJSONObject(j);

                    String comment_id = p2.getJSONObject("booking").getJSONObject("comment").getString("id");
                    String comment_text = p2.getJSONObject("booking").getJSONObject("comment").getString("text");
                    String comment_grade = p2.getJSONObject("booking").getJSONObject("comment").getString("grade");
                    String comment_surname = p2.getJSONObject("booking").getJSONObject("comment").getJSONObject("customer").getString("surname");
                    String comment_created_at = p2.getJSONObject("booking").getJSONObject("comment").getString("created_at");
                    String professional_response = p2.getString("professional_response");

                    //String comment_created_at = p2.getJSONObject("booking").getString("created_at");
                    JSONArray tab_comment_products = p2.getJSONObject("booking").getJSONArray("products");
                    Professional_Product[] prof = new Professional_Product[tab_comment_products.length()];
                    for (int g = 0; g < tab_comment_products.length(); g++) {
                        Professional_Product p = new Professional_Product();
                        JSONObject p3 = tab_comment_products.getJSONObject(g);
                        String comment_product = p3.getString("name");
                        System.out.println("Produit :"+comment_product);
                        p.setName(comment_product);
                        prof[g]=p;
                    }

                    Customer c = new Customer();
                    c.setName(comment_surname);

                    com.setId(Integer.parseInt(comment_id));
                    com.setText(comment_text);
                    com.setGrade(Integer.parseInt(comment_grade));
                    com.setCustomer(c);
                    com.setCreated_at(comment_created_at);
                    com.setProfessional_products(prof);
                    com.setProfessional_response(professional_response);

                    commentaires[iterateur]=com;
                    iterateur++;
                }
            }else{
                commentaires=null;
            }

        } catch (final JSONException e) {
            Log.e(TAG, "Json parsing error: " + e.getMessage());
        }
        return commentaires;
    }


    public String moyenne(String moy){
        String moyenn = null;

        System.out.println(moy.substring(0,1));
        System.out.println(moy.substring(1,2));
        System.out.println(moy.substring(2,3));
        if(Integer.parseInt(moy.substring(2,3)) <= 3){
            moyenn = moy.substring(0,1);
        }else if(Integer.parseInt(moy.substring(2,3)) >= 4 && Integer.parseInt(moy.substring(2,3)) <= 7){
            moyenn = moy.substring(0,1) + ".5";
        }else if(Integer.parseInt(moy.substring(2,3)) >= 8){
            moyenn = String.valueOf(Integer.parseInt(moy.substring(0,1))+1);
        }


        return moyenn;
    }



}

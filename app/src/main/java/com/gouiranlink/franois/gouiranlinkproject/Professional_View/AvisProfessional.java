package com.gouiranlink.franois.gouiranlinkproject.Professional_View;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

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

        lstview_comment=(ListView) view.findViewById(R.id.malistecommentaires);

        //Si le prestataire possède des commentaires
        if(commentaires != null){
            int layout = R.layout.services3;
            int id = R.id.comment_text;
            String[] items = new String[commentaires.length];
            for(int i =0;i<commentaires.length;i++){
                items[i]=commentaires[i].getCustomer().getName();
            }
            AvisAdapter adapter=new AvisAdapter(getActivity(),layout,id,items,commentaires);
            lstview_comment.setAdapter(adapter);



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



                commentaires = new Comment[tab_comment.length()];
                for (int j = 0; j < tab_comment.length(); j++) {
                    Comment com = new Comment();
                    JSONObject p2 = tab_comment.getJSONObject(j);

                    String comment_id = p2.getJSONObject("booking").getJSONObject("comment").getString("id");
                    String comment_text = p2.getJSONObject("booking").getJSONObject("comment").getString("text");
                    String comment_grade = p2.getJSONObject("booking").getJSONObject("comment").getString("grade");
                    String comment_surname = p2.getJSONObject("booking").getJSONObject("comment").getJSONObject("customer").getString("surname");
                    String comment_created_at = p2.getJSONObject("booking").getJSONObject("comment").getString("created_at");

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

                    commentaires[j]=com;
                    //Remplissage des horaires d'ouverture
                    /*pro_schedule.setId(Integer.parseInt(id_schedule));
                    pro_schedule.setWeekday(Integer.parseInt(weekeday_schedule));
                    pro_schedule.setBegin_time(begin_time_schedule);
                    pro_schedule.setEnd_time(end_time_schedule);
                    commentaires[j] = pro_schedule;*/
                }
            }

        } catch (final JSONException e) {
            Log.e(TAG, "Json parsing error: " + e.getMessage());
        }
        return commentaires;
    }


}

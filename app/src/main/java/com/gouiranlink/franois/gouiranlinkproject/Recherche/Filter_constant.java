package com.gouiranlink.franois.gouiranlinkproject.Recherche;

import android.util.Log;

import com.gouiranlink.franois.gouiranlinkproject.ToolsClasses.GetRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import static android.content.ContentValues.TAG;

/**
 * Created by François on 24/08/2017.
 */

public class Filter_constant {

    private String[] filterIdUniversList = null;
    public String[] filterNameUniversList = null;

    private String[] filterIdCategorieList = null;
    public String[] filterNameCategorieList = null;

    private String[] filterIdPrestatationList = null;
    public String[] filterNamePrestationList = null;


    public String[] filtreTitretitreList = null;
    public String[][] filtreTitreList = null;
    public String[][][] filtreTexteList = null;


    //static String[] state = faqTitretitreList;
    //static  String[][] parent = faqTitreList;
    //static  String[][][] child = faqTexteList;


    public Filter_constant(){
        json_parser_filtre1(filtre1_api());

        int nombre_plus_grand = 0;
        for(int i =0;i<filterIdUniversList.length;i++){
            json_parser_filtre2(filtre2_api(Integer.parseInt(filterIdUniversList[i])));
            if(filterIdCategorieList.length > nombre_plus_grand){
                nombre_plus_grand = filterIdCategorieList.length;
            }
        }
        System.out.println("nombre_plus_grand : "+nombre_plus_grand);

        int nombre_plus_grand2 = 0;
        for(int i =0;i<filterIdCategorieList.length;i++){
            json_parser_filtre3(filtre3_api(Integer.parseInt(filterIdCategorieList[i])));
            if(filterIdPrestatationList.length > nombre_plus_grand2){
                nombre_plus_grand2 = filterIdPrestatationList.length;
            }
        }


        filtreTitretitreList = new String[filterIdUniversList.length];
        filtreTitreList = new String[filterIdUniversList.length][nombre_plus_grand];
        filtreTexteList = new String[filterIdUniversList.length][nombre_plus_grand][nombre_plus_grand2];

        for(int i =0; i<filterIdUniversList.length;i++){
            filtreTitretitreList[i]=filterNameUniversList[i];
            System.out.println("Nombres1 : "+i);
            json_parser_filtre2(filtre2_api(Integer.parseInt(filterIdUniversList[i])));
            for(int y = 0; y<filterIdCategorieList.length-1;y++){
                System.out.println("Nombres2 : "+i +"    " + y);
                filtreTitreList[i][y]=filterNameCategorieList[y];
                json_parser_filtre3(filtre3_api(Integer.parseInt(filterIdCategorieList[y])));
                for(int j = 0;j<filterIdPrestatationList.length-1;j++){
                    filtreTexteList[i][y][j]=filterNamePrestationList[j];
                    System.out.println("Nombres3 : "+i +"    " + y + "    " + j);
                }

            }

        }

        /*for(int i = 0;i<faqTitretitreList.length;i++){
            System.out.println("state : "+faqTitretitreList[i]);
        }
        for(int i = 0;i<faqTitretitreList.length;i++){
            for(int y = 0;y<faqTitreList.length;y++){
                //if(faqTitreList[i][y].equals("null")){
                //    faqTitreList[i][y]="";
                //}
                if(faqTitreList[i][y]==null){
                    faqTitreList[i][y]="";
                }
                System.out.println("parent : "+faqTitreList[i][y]);
            }
        }
        for(int i = 0;i<faqTitretitreList.length;i++){
            for(int y = 0;y<faqTitreList.length;y++){
                if(faqTexteList[i][y][0]==null){
                    faqTexteList[i][y][0]="";
                }
                System.out.println("child : "+faqTexteList[i][y][0]);
            }
        }*/


    }



    //static String[] state = {"A","B","C"};
    /*static  String[][] parent = {
            {"aa","bb","cc","dd","ee"},
            {"ff","gg","hh","ii","jj"},
            {"kk","ll","mm","nn","oo"}
    };*/

    /*static  String[][][] child = {
            {
                    {"aaa","aab","aac","aad","aae"},
                    {"bba","bbb","bbc","bbd","bbe"},
                    {"cca","ccb","ccc","ccd","cce","ccf","ccg"},
                    {"dda","ddb","dddc","ddd","dde","ddf"},
                    {"eea","eeb","eec"}
            },
            {
                    {"ffa","ffb","ffc","ffd","ffe"},
                    {"gga","ggb","ggc","ggd","gge"},
                    {"hha","hhb","hhc","hhd","hhe","hhf","hhg"},
                    {"iia","iib","iic","iid","iie","ii"},
                    {"jja","jjb","jjc","jjd"}
            },
            {
                    {"kka","kkb","kkc","kkd","kke"},
                    {"lla","llb","llc","lld","lle"},
                    {"mma","mmb","mmc","mmd","mme","mmf","mmg"},
                    {"nna","nnb","nnc","nnd","nne","nnf"},
                    {"ooa","oob"}
            }
    };*/


    public String filtre1_api(){
        GetRequest getRequest = new GetRequest("https://www.gouiran-beaute.com/link/api/v1/product-category/");
        String resp = null;
        try {
            resp = getRequest.execute().get();
            System.out.println("Reeeeeeeeeeeeeeeeeeeeeeesp filtre : ");
            System.out.println(resp.toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return resp;
    }


    public void json_parser_filtre1(String str){

        if (str != null) {
            try {

                str = "{\"data\" : " + str + "}";

                JSONObject jsonObj = new JSONObject(str);
                JSONArray nb_article = jsonObj.getJSONArray("data");
                filterIdUniversList=new String[nb_article.length()];
                filterNameUniversList=new String[nb_article.length()];




                for (int i = 0; i < nb_article.length(); i++) {

                    JSONObject c = nb_article.getJSONObject(i);

                    String s = c.getString("id");
                    String ss = c.getString("name");

                    filterIdUniversList[i] = s;
                    filterNameUniversList[i] = ss;

                    System.out.println(" ID : "+ s+" , NAME : " + ss);

                }

            } catch (final JSONException e) {
            Log.e(TAG, "Json parsing error: " + e.getMessage());
            }
        }
    }


    public String filtre2_api(int id){
        GetRequest getRequest = new GetRequest("https://www.gouiran-beaute.com/link/api/v1/product-category/product-category/"+id+"/");
        String resp = null;
        try {
            resp = getRequest.execute().get();
            System.out.println("Reeeeeeeeeeeeeeeeeeeeeeesp filtre2 : ");
            System.out.println(resp.toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return resp;
    }

    public void json_parser_filtre2(String str){

        if (str != null) {
            try {

                str = "{\"data\" : " + str + "}";

                JSONObject jsonObj = new JSONObject(str);
                JSONArray nb_article = jsonObj.getJSONArray("data");
                filterIdCategorieList=new String[nb_article.length()];
                filterNameCategorieList=new String[nb_article.length()];

                for (int i = 0; i < nb_article.length(); i++) {

                    JSONObject c = nb_article.getJSONObject(i);

                    String s = c.getString("id");
                    String ss = c.getString("name");

                    filterIdCategorieList[i] = s;
                    filterNameCategorieList[i] = ss;
                    System.out.println(" ID : "+ s+" , NAME : " + ss);

                }


            } catch (final JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());
            }
        }
    }

    public String filtre3_api(int id){
        GetRequest getRequest = new GetRequest("https://www.gouiran-beaute.com/link/api/v1/product/?query[product_category_id]="+id);
        String resp = null;
        try {
            resp = getRequest.execute().get();
            System.out.println("Reeeeeeeeeeeeeeeeeeeeeeesp filtre2 : ");
            System.out.println(resp.toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return resp;
    }

    public void json_parser_filtre3(String str){

        if (str != null) {
            try {

                //str = "{\"data\" : " + str + "}";

                JSONObject jsonObj = new JSONObject(str);
                JSONArray nb_article = jsonObj.getJSONArray("data");
                filterIdPrestatationList=new String[nb_article.length()];
                filterNamePrestationList=new String[nb_article.length()];

                for (int i = 0; i < nb_article.length(); i++) {

                    JSONObject c = nb_article.getJSONObject(i);

                    String s = c.getString("id");
                    String ss = c.getString("name");

                    filterIdPrestatationList[i] = s;
                    filterNamePrestationList[i] = ss;
                    System.out.println(" ID : "+ s+" , NAME : " + ss);

                }


            } catch (final JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());
            }
        }
    }




    /*public void json_parser_filtre2(String str){


                int[] nombre = new int[nb_article.length()];
                int nombre_plus_grand = 0;
                faqIdTitreList = new String[nb_article.length()];
                faqTitretitreList = new String[nb_article.length()];

                for (int i = 0; i < nb_article.length(); i++) {
                    JSONObject c = nb_article.getJSONObject(i);
                    JSONArray nb_faq = new JSONArray();
                    nb_faq = c.getJSONArray("data");
                    nombre[i] = nb_faq.length();
                    if(nombre_plus_grand < nb_faq.length()){
                        nombre_plus_grand = nb_faq.length();
                    }
                }


                faqTitreList = new String[nb_article.length()][nombre_plus_grand];
                faqTexteList = new String[nb_article.length()][nombre_plus_grand][1];

                for (int i = 0; i < nb_article.length(); i++) {

                    JSONObject c = nb_article.getJSONObject(i);

                    faqIdTitreList[i]=c.getString("id_titre");
                    faqTitretitreList[i]=c.getString("Titre_titre");
                    JSONArray nb_faq = new JSONArray();
                    nb_faq = c.getJSONArray("data");

                    //ArrayList<String> list_data = new ArrayList<>();


                    for (int y = 0; y < nb_faq.length(); y++) {
                        //JSONObject c2 = nb_faq.getJSONObject(y);
                        String s = nb_faq.getString(y);
                        System.out.println("sssssssssssssssssssss : "+s);

                        JSONObject jsonObj2 = nb_faq.getJSONObject(y);
                        String Titre = jsonObj2.getString("Titre_texte");
                        String Text = jsonObj2.getString("Texte");

                        System.out.println("sssssssssssssssssssss2 : "+Titre +" //// "+Text);


                        faqTitreList[i][y] = Titre;
                        faqTexteList[i][y][0] = Text;
                        //list_data.add(s);

                        System.out.println("faqTitreList 1eeeeeeeeer : "+i+" *** "+y+" *** "+faqTitreList[0][0]);

                    }

                }
            } catch (final JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());
            }
        }
    }*/




}

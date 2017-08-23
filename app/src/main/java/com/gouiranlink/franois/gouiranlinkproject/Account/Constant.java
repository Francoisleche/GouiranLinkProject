package com.gouiranlink.franois.gouiranlinkproject.Account;

import android.util.Log;

import com.gouiranlink.franois.gouiranlinkproject.ToolsClasses.GetRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import static android.content.ContentValues.TAG;

/**
 * Created by François on 22/08/2017.
 */

public class Constant {

    private String[] faqIdTitreList = new String[10];
    public String[] faqTitretitreList = null;
    public String[][] faqTitreList = null;
    public String[][][] faqTexteList = null;


    //static String[] state = faqTitretitreList;
    //static  String[][] parent = faqTitreList;
    //static  String[][][] child = faqTexteList;


    public Constant(){
        json_parser_article(faq_api());

        for(int i = 0;i<faqTitretitreList.length;i++){
            System.out.println("state : "+faqTitretitreList[i]);
        }
        for(int i = 0;i<faqTitretitreList.length;i++){
            for(int y = 0;y<faqTitreList.length;y++){
                /*if(faqTitreList[i][y].equals("null")){
                    faqTitreList[i][y]="";
                }*/
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
        }


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


    public String faq_api(){
        GetRequest getRequest = new GetRequest("http://gouiran.link.free.fr/getallfaq3.php");
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


    public void json_parser_article(String str){

        if (str != null) {
            try {

                JSONObject jsonObj = new JSONObject(str);
                JSONArray nb_article = jsonObj.getJSONArray("datas");


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
    }




}
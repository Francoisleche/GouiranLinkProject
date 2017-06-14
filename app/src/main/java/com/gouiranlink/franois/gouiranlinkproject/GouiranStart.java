package com.gouiranlink.franois.gouiranlinkproject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.gouiranlink.franois.gouiranlinkproject.InsciptionConnexion.LoginActivity;
import com.gouiranlink.franois.gouiranlinkproject.ToolsClasses.GetRequest;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

public class GouiranStart extends RxAppCompatActivity {

    private GetRequest getRequest;
    private String[] tab_autocomplete_place;
    private ArrayList list_ville = new ArrayList();



    private AtomicInteger executedTasksCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gouiran_start);

        ImageView background = (ImageView) findViewById(R.id.background);
        //final DisplayMetrics displayMetrics = new DisplayMetrics();
        //getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        //final int width = displayMetrics.widthPixels;
        //final int height = displayMetrics.heightPixels;
        final Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Acrom W00 Medium.ttf");

        /*float heightF = (float) height;
        //float twoThird = (float) 2 / 3;
        float twoThird = (float) 3 / 5;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, (int) (heightF * twoThird));
        background.setLayoutParams(params);*/

        /*TextView text = (TextView) findViewById(R.id.presentation_text);
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
        text.setTypeface(font);*/



        TextView text1 = (TextView) findViewById(R.id.presentation_text1);
        TextView text2 = (TextView) findViewById(R.id.presentation_text2);
        TextView text3 = (TextView) findViewById(R.id.presentation_text3);

        /*text1.setTypeface(font);
        text2.setTypeface(font);
        text3.setTypeface(font);*/






        final Context context = this;

        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage(R.string.notification_push)
                        .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //webservice_autocomplete();
                                Intent intent = new Intent(GouiranStart.this, LoginActivity.class);
                                intent.putExtra("place", tab_autocomplete_place);
                                startActivity(intent);
                                finish();
                            }
                        });
                AlertDialog dialog = builder.create();
                isFirstTime(dialog);
            }
        }, 2500);
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
            //webservice_autocomplete();
            /*executedTasksCount = new AtomicInteger(0);
            String[] tableau = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
            for (int i = 0; i < tableau.length; i++) {
                String taskId = tableau[i];
                startTask(taskId, 30, true);
            }*/

            String essai = Uri.parse("file:///android_asset/ville.json").toString();
            System.out.println("essai :"+essai);


            StringBuilder builder = new StringBuilder();
            AssetManager assetManager=getAssets();
            InputStream inputStream = null;
            try {
                System.out.println("gg");
                inputStream = assetManager.open("ville.json");
            } catch (IOException e) {
                System.out.println("non");
                e.printStackTrace();
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            try {
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                    builder.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            tab_autocomplete_place = place_jsonparser(builder.toString());
            for(int i=0;i<tab_autocomplete_place.length;i++){
                System.out.println("Viiiiiiiiiiille :"+tab_autocomplete_place[i]);

            }


            /*for(int i=0;i<list_ville.size();i++){

                System.out.println("Viiiiiiiiiiille :"+list_ville.get(i));
                System.out.println("Taille ville :"+list_ville.size());
            }*/


            Intent intent = new Intent(GouiranStart.this, LoginActivity.class);
            intent.putExtra("place", tab_autocomplete_place);
            startActivity(intent);
            finish();
        }
        return !ranBefore;
    }






    public void webservice_autocomplete() {
        String[] A = place_jsonparser(recherche_place("A"));
        String[] B = place_jsonparser(recherche_place("B"));
        String[] C = place_jsonparser(recherche_place("C"));
        String[] D = place_jsonparser(recherche_place("D"));
        String[] E = place_jsonparser(recherche_place("E"));
        String[] F = place_jsonparser(recherche_place("F"));
        String[] G = place_jsonparser(recherche_place("G"));
        String[] H = place_jsonparser(recherche_place("H"));
        String[] I = place_jsonparser(recherche_place("I"));
        String[] J = place_jsonparser(recherche_place("J"));
        String[] K = place_jsonparser(recherche_place("K"));
        String[] L = place_jsonparser(recherche_place("L"));
        String[] M = place_jsonparser(recherche_place("M"));
        String[] N = place_jsonparser(recherche_place("N"));
        String[] O = place_jsonparser(recherche_place("O"));
        String[] P = place_jsonparser(recherche_place("P"));
        String[] Q = place_jsonparser(recherche_place("Q"));
        String[] R = place_jsonparser(recherche_place("R"));
        String[] S = place_jsonparser(recherche_place("S"));
        String[] T = place_jsonparser(recherche_place("T"));
        String[] U = place_jsonparser(recherche_place("U"));
        String[] V = place_jsonparser(recherche_place("V"));
        String[] W = place_jsonparser(recherche_place("W"));
        String[] X = place_jsonparser(recherche_place("X"));
        String[] Y = place_jsonparser(recherche_place("Y"));
        String[] Z = place_jsonparser(recherche_place("Z"));

        tab_autocomplete_place = new String[A.length + B.length + C.length + D.length + E.length + F.length + G.length + H.length +
                I.length + J.length + K.length + L.length + M.length + N.length + O.length + P.length + Q.length + R.length +
                S.length + T.length + U.length + V.length + W.length + X.length + Y.length + Z.length];

        ArrayList list = new ArrayList();
        for (Object object : A) {
            list.add(object);
        }
        for (Object object : B) {
            list.add(object);
        }
        for (Object object : C) {
            list.add(object);
        }
        for (Object object : D) {
            list.add(object);
        }
        for (Object object : E) {
            list.add(object);
        }
        for (Object object : F) {
            list.add(object);
        }
        for (Object object : G) {
            list.add(object);
        }
        for (Object object : H) {
            list.add(object);
        }
        for (Object object : I) {
            list.add(object);
        }
        for (Object object : J) {
            list.add(object);
        }

        for (Object object : K) {
            list.add(object);
        }
        for (Object object : L) {
            list.add(object);
        }
        for (Object object : M) {
            list.add(object);
        }
        for (Object object : N) {
            list.add(object);
        }
        for (Object object : O) {
            list.add(object);
        }

        for (Object object : P) {
            list.add(object);
        }
        for (Object object : Q) {
            list.add(object);
        }
        for (Object object : R) {
            list.add(object);
        }
        for (Object object : S) {
            list.add(object);
        }
        for (Object object : T) {
            list.add(object);
        }

        for (Object object : U) {
            list.add(object);
        }
        for (Object object : V) {
            list.add(object);
        }
        for (Object object : W) {
            list.add(object);
        }
        for (Object object : X) {
            list.add(object);
        }
        for (Object object : Y) {
            list.add(object);
        }
        for (Object object : Z) {
            list.add(object);
        }


        list.toArray(tab_autocomplete_place);


        for (int g = 0; g < tab_autocomplete_place.length; g++) {
            System.out.println("AFFFFICHE tab_autocomplete_name " + g + " : " + tab_autocomplete_place[g]);
        }

    }


    public String recherche_place(String query) {
        getRequest = new GetRequest("https://www.gouiran-beaute.com/link/api/v1/autocomplete/professional/place/?query=" + query + "&limit=100");
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


    public String[] place_jsonparser(String str){
        String ls = str;
        String[] recup2 = new String[]{};
        //ls = researchTask.getResponse();
        //recup = jsonparser(ls);

        /*String recup = "{" + '"' + "Recup" + '"' + " : [";
        String ahbon = ls.replace("[", recup);
        String ahbon2 = ahbon.replace("]", "]}");*/

        try {
            JSONObject jsonObj = null;
            jsonObj = new JSONObject(str);
            JSONArray contacts = null;
            contacts = jsonObj.getJSONArray("data");
            recup2 = new String[contacts.length()];
            for (int i = 0; i < contacts.length(); i++) {
                JSONObject c = null;
                c = contacts.getJSONObject(i);
                String id = c.getString("value");
                recup2[i]=id;
                list_ville.add(id);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return recup2;
    }


/*
    public void webservices2(String date){
        Observable<Notification<NewsService.News>> newsListOb =
                RetrofitProvider.getInstance().create(NewsService.class)
                        .getNewsList(date)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .compose(bindToLifecycle())
                        .materialize().share();

        newsListOb.filter(Notification::isOnNext)
                .map(n -> n.getValue())
                .filter(m -> !m.getStories().isEmpty())
                .flatMap(m -> Observable.from(m.getStories()))
                .doOnNext(i -> {
                    int index = random.nextInt(5);
                    String day = set.get(index);
                    if (listTreeMap.get(day) != null) {
                        List<NewsService.News.StoriesBean> list = listTreeMap.get(day);
                        list.add(i);
                    } else {
                        List<NewsService.News.StoriesBean> list = new ArrayList<NewsService.News.StoriesBean>();
                        list.add(i);
                        listTreeMap.put(day, list);
                    }

                })
                .toList()
                .subscribe((l) -> {
                    dayNewsListAdapter.setDateDataMap(listTreeMap);
                    dayNewsListAdapter.notifyDataSetChanged();
                    calendarItemAdapter.notifyDataSetChanged();
                })
        ;
    }*/






    private void startTask(String taskId, int taskDuration, boolean useParallelExecution) {
        ParserTask task = new ParserTask(taskId, taskDuration);

        if (useParallelExecution) {

            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        } else {
            // this is the same as calling task.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
            task.execute();
        }
    }

    private int getIntFromTextView(EditText v) {
        int result = 0;
        try {
            result = Integer.parseInt(v.getText().toString());
        } catch (NumberFormatException ignored) {}
        return result;
    }

    private void log(String msg) {
        Log.d("MainActivity", msg);
    }






    public class ParserTask extends AsyncTask<Void, Void, Boolean> {


        private String response = "";

        private final String mQuery;
        private final int mLimit;
        private final String json;

        ParserTask(String query, int limit) {
            mQuery = query;
            mLimit = limit;
            System.out.println("QUERY :" + query + ",LIMIT :" + limit);
            json = "{\n" +
                    "\"query\":\"" + query + "\",\n" +
                    "\"limit\":\"" + limit + "\"" +
                    "}\n";
            getRequest = new GetRequest("https://www.gouiran-beaute.com/link/api/v1/autocomplete/professional/place/?query=" + query + "&limit=100");
            String resp = null;
            try {
                resp = getRequest.execute().get();
                System.out.println("Rechercheeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
                System.out.println(resp);
                place_jsonparser(resp);
                //onPostExecute(results);

            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            response = resp;

        }

        String getResponse() {
            return response;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            int taskExecutionNumber = executedTasksCount.incrementAndGet();
            log("doInBackground: entered, taskExecutionNumber = " + taskExecutionNumber);
            SystemClock.sleep(mLimit); // emulates some job
            log("doInBackground: is about to finish, taskExecutionNumber = " + taskExecutionNumber);
            return null;


        }


    }







}

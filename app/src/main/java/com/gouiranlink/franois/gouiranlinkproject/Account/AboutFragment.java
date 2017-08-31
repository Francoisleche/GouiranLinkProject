package com.gouiranlink.franois.gouiranlinkproject.Account;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gouiranlink.franois.gouiranlinkproject.R;
import com.gouiranlink.franois.gouiranlinkproject.ToolsClasses.GetRequest;

import java.util.concurrent.ExecutionException;

/*
Fragment which contains the "A propos" in settings
 */

public class AboutFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    public AboutFragment() {
        // Required empty public constructor
    }

    public static AboutFragment newInstance() {
        AboutFragment fragment = new AboutFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_about, container, false);
        TextView tv = (TextView) view.findViewById(R.id.apropos_text);
        String s = apropos_api();

        /*final InputStream resource = DeckHelper.class.getResourceAsStream(file);
        final Scanner reader = new Scanner(resource, "ISO-8859-1");
        while (reader.hasNextLine()) {
            loadCard(reader.nextLine(), deck, ignoreCardCount);
        }*/





        /*String ss = "";
        try {
            ss = URLEncoder.encode(s, "ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }*/
        tv.setText(s);


        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } /*else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }



    public String apropos_api(){
        GetRequest getRequest = new GetRequest("http://gouiran.link.free.fr/Apropos.txt");
        String resp = null;
        try {
            resp = getRequest.execute().get();
            System.out.println("Apropos : ");
            System.out.println(resp.toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return resp;
    }




    /*public void json_parser_article(String str){

        if (str != null) {
            try {

                JSONObject jsonObj = new JSONObject(str);
                JSONArray nb_article = jsonObj.getJSONArray("data");
                articleIdList = new String[nb_article.length()];
                articleTextList = new String[nb_article.length()];
                articlePhotoList = new String[nb_article.length()];

                for (int i = 0; i < nb_article.length(); i++) {

                    JSONObject c = nb_article.getJSONObject(i);

                    articleIdList[i]=c.getString("id");
                    articleTextList[i]=c.getString("text");
                    articlePhotoList[i]=c.getString("photo");
                }

            } catch (final JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());
            }
        }


    }*/
}

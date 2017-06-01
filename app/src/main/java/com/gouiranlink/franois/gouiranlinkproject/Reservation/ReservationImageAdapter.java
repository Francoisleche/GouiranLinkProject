package com.gouiranlink.franois.gouiranlinkproject.Reservation;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gouiranlink.franois.gouiranlinkproject.Object.Customer;
import com.gouiranlink.franois.gouiranlinkproject.R;
import com.gouiranlink.franois.gouiranlinkproject.ToolsClasses.DeleteRequest;
import com.gouiranlink.franois.gouiranlinkproject.ToolsClasses.DownloadImageTask;
import com.gouiranlink.franois.gouiranlinkproject.ToolsClasses.GetRequest;
import com.gouiranlink.franois.gouiranlinkproject.ToolsClasses.PostRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class ReservationImageAdapter extends BaseAdapter {
    final private Context mContext;
    private Customer customer;
    private List<Boolean> boolean_commentaires_date_ok;
    private ArrayList<String> id_commentaires_deja_fait;

    private List<String> institutesNames = new ArrayList<String>();
    //private List<String> types = new ArrayList<String>();
    List<List<String>> types = new ArrayList<List<String>>();
    private List<String> pictures = new ArrayList<String>();
    private List<String> dates = new ArrayList<String>();
    private List<String> hours = new ArrayList<String>();
    private List<String> adress = new ArrayList<String>();
    private List<String> id = new ArrayList<String>();
    private String type_reservation = new String();
    private String products_json;




    ReservationImageAdapter(Context c) {
        mContext = c;
    }

    public Context getmContext() {
        return mContext;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<String> getId() {
        return id;
    }

    public void setId(List<String> id) {
        this.id = id;
    }

    public List<String> getAdress() {
        return adress;
    }

    public void setAdress(List<String> adress) {
        this.adress = adress;
    }

    public String getType_reservation() {
        return type_reservation;
    }

    public void setType_reservation(String type_reservation) {
        this.type_reservation = type_reservation;
    }

    public List<String> getInstitutesNames() {
        return institutesNames;
    }

    public void setInstitutesNames(List<String> institutesNames) {
        this.institutesNames = institutesNames;
    }

    public List<List<String>> getTypes() {
        return types;
    }

    public void setTypes(List<List<String>> types) {
        this.types = types;
    }

    public List<String> getPictures() {
        return pictures;
    }

    public void setPictures(List<String> pictures) {
        this.pictures = pictures;
    }

    public List<String> getDates() {
        return dates;
    }

    public void setDates(List<String> dates) {
        this.dates = dates;
    }

    public List<String> getHours() {
        return hours;
    }

    public void setHours(List<String> hours) {
        this.hours = hours;
    }

    public int getCount() {
        return institutesNames.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return position;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(final int position, View convertView, ViewGroup parent) {
        final int position2 = position;
        View grid;
        String type;
        LayoutInflater inflater = null;

        if (convertView == null) {
            inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            grid = inflater.inflate(R.layout.grid_single_reservation, null);
        } else {
            grid = convertView;
        }
        /*for(int i =0;i<dates.size();i++){
            System.out.println("Plein les cuuuuuuuuuuuuuuuuuuul  :"+institutesNames.get(i).toString()+"   "+dates.get(i).toString());
        }*/


            // if it's not recycled, initialize some attributes
            //grid = new View(mContext);

            TextView textView = (TextView)grid.findViewById(R.id.institute_name);
            ImageView imageView = (ImageView)grid.findViewById(R.id.picture);
            textView.setText(institutesNames.get(position));



        //Marche Impeccablement bien :)
        Picasso.with(grid.getContext()).load(pictures.get(position))
           .into(imageView);

            //new DownloadImageTask(imageView).execute(pictures.get(position));


            textView = (TextView)grid.findViewById(R.id.type);
            type = "";
            for (int i = 0; i < types.get(position).size(); i++) {
                type += types.get(position).get(i);
                if (i + 1 < types.get(position).size())
                    type += " ";
            }
            textView.setText(type);
            textView = (TextView)grid.findViewById(R.id.date);
            //System.out.println("Plein les cuuuuuuuuuuuuuuuuuuul : "+dates.get(position)+ "   " + position);
            textView.setText(dates.get(position));
            textView = (TextView)grid.findViewById(R.id.hour);
            textView.setText(hours.get(position));





        Button button = (Button) grid.findViewById(R.id.button);
        button.setVisibility(View.VISIBLE);
        button.setClickable(true);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // delete ton item ou appelle ta class qui va sauvegarder position.
                int position3 =position2;
                System.out.println("???????????????"+ position3);

                final Dialog dialog = new Dialog(getmContext());
                LayoutInflater layoutInflater = LayoutInflater.from(getmContext());
                View contentView = layoutInflater.inflate(R.layout.reservation_popup, null);
                final LinearLayout root = (LinearLayout) contentView.findViewById(R.id.proRootLayout2);
                System.out.println("Ooooooooooooooooooh filtres ?");

                TextView textview = (TextView) contentView.findViewById(R.id.monrdv);
                textview.setText("Mon rendez vous");
                //textview.setText("Mon RdV chez " + institutesNames.get(position) + "-" +id.get(position));

                TextView textview1 = (TextView) contentView.findViewById(R.id.texte_pro);
                textview1.setText("chez " + institutesNames.get(position));

                TextView textview2 = (TextView) contentView.findViewById(R.id.texte_date);
                textview2.setText("pour " + dates.get(position));

                TextView textview3 = (TextView) contentView.findViewById(R.id.texte_adresse);
                textview3.setText("à" + adress.get(position));

                dialog.setContentView(root);
                dialog.show();


                contentView.findViewById(R.id.ok_reservation).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });

                contentView.findViewById(R.id.faire_commentaire).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Dialog dialog_commentaire = new Dialog(getmContext());
                        LayoutInflater layoutInflater = LayoutInflater.from(getmContext());
                        final View contentView2 = layoutInflater.inflate(R.layout.comment_popup, null);
                        System.out.println("OOOooooooooooooh id :" + customer.getId());
                        final LinearLayout root = (LinearLayout) contentView2.findViewById(R.id.Layout_comment);
                        //recupcommentaire(String.valueOf(customer.getId()));

                        dialog_commentaire.setContentView(root);
                        dialog_commentaire.show();

                        contentView2.findViewById(R.id.comment_valid).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                EditText edit = (EditText)contentView2.findViewById(R.id.description_comment);
                                String s = edit.getText().toString();
                                faire_un_commentaire(String.valueOf(customer.getId()),s);

                                dialog_commentaire.cancel();
                            }
                        });

                    }
                });

                traitementcommentaire(recupcommentaire(String.valueOf(customer.getId())));
                boolean trouve = false;

                if(type_reservation.equals("Done")){
                    contentView.findViewById(R.id.supprimer_reservation).setVisibility(View.GONE);
                    System.out.println("Ooooooooooooooooooh filtres ?" + id_commentaires_deja_fait.size() +"   "+id.size());
                    if(boolean_commentaires_date_ok.get(position).equals(true)){
                        for(int i =0;i<id_commentaires_deja_fait.size();i++){

                            if(id_commentaires_deja_fait.get(i).equals(id.get(position))){
                                trouve = true;
                            }
                        }
                        if(trouve)
                            contentView.findViewById(R.id.faire_commentaire).setVisibility(View.GONE);
                        else
                            contentView.findViewById(R.id.faire_commentaire).setVisibility(View.VISIBLE);
                    }else{
                        contentView.findViewById(R.id.faire_commentaire).setVisibility(View.GONE);
                    }


                }else {
                    contentView.findViewById(R.id.faire_commentaire).setVisibility(View.GONE);
                    contentView.findViewById(R.id.supprimer_reservation).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Dialog d = new AlertDialog.Builder(getmContext())
                                    .setTitle("Confirmation")
                                    .setMessage("Voulez-vous supprimer le RdV ?")
                                    .setNegativeButton(android.R.string.cancel, null)
                                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog2, int which) {
                                            deleteReservationList(id.get(position));
                                            dialog.cancel();
                                        }
                                    })
                                    .create();
                            d.show();


                            //Si on enleve la boite de dialogue, remettre les 2 lignes ci dessous
                            //deleteReservationList(id.get(position));
                            //dialog.cancel();
                        }
                    });
                }






            }
        });




        return (grid);
    }

    private void deleteReservationList(String id) {
        String headerKey;
        String headerValue;
        String resp;

        headerKey = "Authorization";
        headerValue = "Token " + String.valueOf(customer.getToken());
        System.out.println("headerValue = Token " + String.valueOf(customer.getToken()));
        DeleteRequest getRequest = new DeleteRequest("https://www.gouiran-beaute.com/link/api/v1/booking/" + id + "/",String.valueOf(customer.getId()), headerKey, headerValue);
        try {
            resp = getRequest.execute().get();
            System.out.println("DELETE : " + resp);

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }




    private String recupcommentaire(String id) {
        String headerKey;
        String headerValue;
        String resp = "";

        headerKey = "Authorization";
        headerValue = "Token " + String.valueOf(customer.getToken());
        System.out.println("headerValue = Token " + String.valueOf(customer.getToken()));
        GetRequest getRequest = new GetRequest("https://www.gouiran-beaute.com/link/api/v1/comment/customer/"+id+ "/", headerKey, headerValue);
        try {
            resp = getRequest.execute().get();
            System.out.println("GET : " + resp);

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return resp;
    }


    public void traitementcommentaire(String jsonStr){
        try {
        if (!jsonStr.equals("[]")) {
            JSONObject jsonObj = null;
            jsonObj = new JSONObject(jsonStr);

            //JSONArray contacts = jsonObj.getJSONArray("data");
            JSONArray Customer_comment = jsonObj.getJSONArray("data");

            System.out.println("AHBON3 :" + jsonStr);


            //Shop_image = new ArrayList<>();
            //ProfessionnalGenericSchedule = new Professional_Schedule[Professional_Schedule.length()];
            id_commentaires_deja_fait = new ArrayList<>(Customer_comment.length());
            for (int j = 0; j < Customer_comment.length(); j++) {
                JSONObject p2 = Customer_comment.getJSONObject(j);
                String id = p2.getJSONObject("booking").getString("id");
                String begin_date = p2.getJSONObject("booking").getString("begin_date");
                String price = p2.getJSONObject("booking").getString("price");
                System.out.println("Oooooooooooooooooooooooooooooooooooh :" + id +"      "+ begin_date+"     " +price);
                id_commentaires_deja_fait.add(id);
            }
        }

        } catch (JSONException e) {
            e.printStackTrace();
        }



    }


    private void faire_un_commentaire(String id,String text) {
        String headerKey;
        String headerValue;
        String resp;
        String json,json2;

        String format = "dd/MM/yy H:mm:ss";

        java.text.SimpleDateFormat formater = new java.text.SimpleDateFormat( format );
        java.util.Date date = new java.util.Date();
        String jour1=formater.format(date).toString().substring(0,2);
        String mois1=formater.format(date).toString().substring(3,5);
        String annee1="20"+formater.format(date).toString().substring(6,8);
        String heure1=formater.format(date).toString().substring(9,11);

        String date2 = annee1+"/"+mois1+"/"+jour1+"T"+formater.format(date).toString().substring(9,17) + "Z";

        json2 = "{\n" +
                "\"id\":\"" + String.valueOf(customer.getId()) + "\"," +
                "\"name\":\"" + customer.getName() + "\"," +
                "\"surname\":\"" + customer.getSurname() + "\""+
                "}\n";


        json = "{\n" +
                "\"booking\":{\"" +
                "begin_date\":\"" + date2 + "\"," +
                "\"products\":\n" + products_json +
                "," +
                "\"grade\":" + 5 + "," +
                "\"text\":\"" + text + "\"," +
                "\"customer\":" + json2 +
                "}\n"+"}";


        System.out.println("JSOOOOOOOOOOOOOOn :"+json);
        headerKey = "Authorization";
        headerValue = "Token " + String.valueOf(customer.getToken());
        System.out.println("headerValue = Token " + String.valueOf(customer.getToken()));
        PostRequest postRequest = new PostRequest("https://www.gouiran-beaute.com/link/api/v1/comment/customer/"+id+ "/", json, headerKey, headerValue);
        try {
            resp = postRequest.execute().get();
            System.out.println("POST : " + resp);

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }





    public List<Boolean> getBoolean_Commentaire() {
        return boolean_commentaires_date_ok;
    }

    public void setBoolean_Commentaire(List<Boolean> boolean_commentaires) {
        this.boolean_commentaires_date_ok = boolean_commentaires;
    }

    public String getProducts_json() {
        return products_json;
    }

    public void setProducts_json(String products_json) {
        this.products_json = products_json;
    }



    protected Drawable ImageOperations(String url) {
        try {
            //URL urle = new URL(url);
            //Object content = urle.getContent();

            InputStream inputstream = new URL(url).openStream();
            Drawable  drawable = Drawable.createFromStream(inputstream, "src");
            return drawable;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    protected Drawable drawableFromUrl(URL url) throws IOException {
        Drawable ret = null;
        InputStream stream = null;
        try {
            stream = url.openStream();
            ret = Drawable.createFromStream(stream, "src");
        } catch (IOException ioe) {
            throw ioe;
        } catch (Exception e) {
            Log.e("drawableFromUrl","Failed to read image from URL !",e);
            throw new IOException("ImageFromUrlFailure",e);
        } finally {
            if (stream != null) try { stream.close(); } catch (Exception ex) { Log.wtf("drawableFromUrl","Failed to gracefully close the stream !",ex); }
        }
        return ret;
    }

}

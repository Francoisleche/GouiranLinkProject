package com.gouiranlink.franois.gouiranlinkproject.Reservation;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gouiranlink.franois.gouiranlinkproject.Object.Customer;
import com.gouiranlink.franois.gouiranlinkproject.R;
import com.gouiranlink.franois.gouiranlinkproject.ToolsClasses.DeleteRequest;
import com.gouiranlink.franois.gouiranlinkproject.ToolsClasses.DownloadImageTask;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ReservationImageAdapter extends BaseAdapter {
    final private Context mContext;
    private Customer customer;

    private List<String> institutesNames = new ArrayList<String>();
    //private List<String> types = new ArrayList<String>();
    List<List<String>> types = new ArrayList<List<String>>();
    private List<String> pictures = new ArrayList<String>();
    private List<String> dates = new ArrayList<String>();
    private List<String> hours = new ArrayList<String>();
    private List<String> adress = new ArrayList<String>();
    private List<String> id = new ArrayList<String>();
    private String type_reservation = new String();




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
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            grid = new View(mContext);
            grid = inflater.inflate(R.layout.grid_single_reservation, null);
            TextView textView = (TextView)grid.findViewById(R.id.institute_name);
            ImageView imageView = (ImageView)grid.findViewById(R.id.picture);
            textView.setText(institutesNames.get(position));
            new DownloadImageTask(imageView).execute(pictures.get(position));
            textView = (TextView)grid.findViewById(R.id.type);
            type = "";
            for (int i = 0; i < types.get(position).size(); i++) {
                type += types.get(position).get(i);
                if (i + 1 < types.get(position).size())
                    type += " ";
            }
            textView.setText(type);
            textView = (TextView)grid.findViewById(R.id.date);
            textView.setText(dates.get(position));
            textView = (TextView)grid.findViewById(R.id.hour);
            textView.setText(hours.get(position));

        } else {
            grid = convertView;
        }



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

                if(type_reservation.equals("Done")){
                    contentView.findViewById(R.id.supprimer_reservation).setVisibility(View.GONE);
                }else {
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



}

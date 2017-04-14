package com.gouiranlink.franois.gouiranlinkproject.Account;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.gouiranlink.franois.gouiranlinkproject.R;

import java.io.FileDescriptor;
import java.io.IOException;

/*
Fragment which is the place where people are going to report the problems they encountered
 */

public class TellUsFragment extends Fragment {


    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int PICK_IMAGE_REQUEST = 1;
    private static int RESULT_LOAD_IMAGE = 1;
    private OnFragmentInteractionListener mListener;

    public TellUsFragment() {
        // Required empty public constructor
    }

    public static TellUsFragment newInstance() {
        TellUsFragment fragment = new TellUsFragment();
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

        final View v = (inflater.inflate(R.layout.fragment_tellus, container, false));
        TextView modele_smartphone_tellus = (TextView) v.findViewById(R.id.modele_smartphone_tellus);
        TextView version_smartphone_tellus = (TextView) v.findViewById(R.id.version_smartphone_tellus);
        TextView nom_smartphone_tellus = (TextView) v.findViewById(R.id.nom_smartphone_tellus);
        TextView constructeur_smartphone_tellus = (TextView) v.findViewById(R.id.constructeur_smartphone_tellus);

        final EditText avis_tellus = (EditText) v.findViewById(R.id.avis_tellus);
        final EditText description_tellus = (EditText) v.findViewById(R.id.description_tellus);

        Button erreur_selfie_tellus = (Button) v.findViewById(R.id.erreur_selfie_tellus);
        erreur_selfie_tellus.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);
                System.out.println(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);


                Uri selectedImage = i.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();

                ImageView imageView = (ImageView) v.findViewById(R.id.imgView_tellus);

                Bitmap bmp = null;
                try {
                    bmp = getBitmapFromUri(selectedImage);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                imageView.setImageBitmap(bmp);
            }
        });







        //Information du smartphone
        System.out.println(Build.HARDWARE);
        System.out.println(Build.VERSION.CODENAME);
        System.out.println(Build.VERSION.BASE_OS);
        System.out.println(Build.VERSION.INCREMENTAL);
        System.out.println(Build.VERSION.SDK_INT);
        System.out.println(Build.VERSION.SDK);

        System.out.println(Build.HOST);
        System.out.println(Build.ID);
        System.out.println(Build.DEVICE);
        System.out.println(Build.DISPLAY);
        System.out.println(Build.MANUFACTURER);
        System.out.println(Build.MODEL);
        System.out.println(Build.PRODUCT);
        System.out.println(Build.SERIAL);

        //String s = Build.HARDWARE;
        String sdk = Build.VERSION.SDK;
        String model = Build.MODEL;
        String nom = Build.HOST;
        String manufacturer = Build.MANUFACTURER;

        version_smartphone_tellus.setText("Version "+ sdk);
        modele_smartphone_tellus.setText("Model : "+ model);
        nom_smartphone_tellus.setText("Nom du telephone : "+ nom);
        constructeur_smartphone_tellus.setText("Constructeur : "+ manufacturer);


        Button envoi_mail_erreur_tellus=(Button) v.findViewById(R.id.envoi_mail_erreur_tellus);
        //shop_email.setText(professional.getShop_email());
        envoi_mail_erreur_tellus.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent email = new Intent(Intent.ACTION_SEND);
                email.setType("text/plain");
                //email.putExtra(android.content.Intent.EXTRA_EMAIL,"meynardfrancois@gmail.com");
                email.putExtra(Intent.EXTRA_SUBJECT, "Avis");
                if(!avis_tellus.getText().equals("")){
                    String s = avis_tellus.getText().toString() + description_tellus.getText().toString() + "\n";
                    email.putExtra(Intent.EXTRA_TEXT, s);
                }else if(avis_tellus.getText().equals("")){
                    email.putExtra(Intent.EXTRA_TEXT, "Saisir votre demande ici...");
                }

                email.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(Intent.createChooser(email, "Choisir le logiciel"));

                /*GMailSender sender = new GMailSender(userid.getText().toString(), password.getText().toString());
                try {
                    sender.sendMail(subject, body, from, to);
                }
                catch (Exception e) {
                    Log.e("SendMail", e.getMessage(), e);
                }*/
            }
        });





        return v;
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


    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                getActivity().getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }
}
